package org.grozeille.pricer.stories;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.grozeille.pricer.DateOffset;
import org.grozeille.pricer.DateOffsetType;
import org.grozeille.pricer.MarketDataService;
import org.grozeille.pricer.Option;
import org.grozeille.pricer.OptionType;
import org.grozeille.pricer.Pricer;
import org.grozeille.pricer.utils.DateOffsetConverter;
import org.grozeille.pricer.utils.FixedDateService;
import org.grozeille.pricer.utils.OptionArgs;
import org.grozeille.pricer.utils.StepsDefinition;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.joda.time.Days;
import org.joda.time.LocalDate;


@StepsDefinition
public class BlackSholesSteps {
    
    private DateOffsetConverter dateOffsetConverter;

    private FixedDateService dateService;

    private Pricer pricer;

    private OptionArgs input;

    private Option result;

    private MarketDataService marketDataService;

    @BeforeScenario
    public void inializeScenario() {
        dateService = new FixedDateService();
        dateService.setNow(new LocalDate(2012, 1, 1));
        pricer = new Pricer();
        pricer.setDateService(dateService);
        marketDataService = mock(MarketDataService.class);
        pricer.setMarketDataService(marketDataService);
    }

    @AfterScenario
    public void disposeScenario() {
    }

    @Given("the following option: $table")
    public void givenTheFollowingOption(ExamplesTable table) {
        this.input = new OptionArgs();

        this.input.setOptionType(Enum.valueOf(OptionType.class, table.getRow(0).get("type")));
        this.input.setSpot(Double.parseDouble(table.getRow(0).get("spot")));
        this.input.setStrike(Double.parseDouble(table.getRow(0).get("strike")));
        this.input.setRate(Double.parseDouble(table.getRow(0).get("rate")));
        this.input.setVolatility(Double.parseDouble(table.getRow(0).get("volatility")));

        String maturityString = table.getRow(0).get("maturity");

        this.input.setMaturity(parseMaturity(maturityString));
    }

    @Given("the market rate: $rate")
    public void GivenTheMarketRate(double rate)
    {
        when(this.marketDataService.getInterestRate()).thenReturn(rate);
    }

    @Given("the market spot for underlying \"$underlying\": $spot")
    public void GivenTheMarketSpotForUnderlying(String underlying, double spot)
    {
        when(this.marketDataService.getSpot(underlying)).thenReturn(spot);
    }

    @Given("the market volatility for underlying \"$underlying\" since $since: $volatility")
    public void GivenTheMarketVolatilityForUnderlying(String underlying, DateOffset since, double volatility)
    {
        int days = 0;
        if (since.getType() == DateOffsetType.MONTH)
        {
            days = Days.daysBetween(this.dateService.getNow(), this.dateService.getNow().plusMonths(since.getOffset())).getDays();
        }
        else if (since.getType() == DateOffsetType.YEAR)
        {
            days = Days.daysBetween(this.dateService.getNow(), this.dateService.getNow().plusYears(since.getOffset())).getDays();
        }

        when(this.marketDataService.getHistoricalVolatility(underlying, this.dateService.getNow(), days)).thenReturn(volatility);
    }

    @When("I compute the price")
    public void WhenIComputeThePrice()
    {
        this.result = pricer.price(
                this.input.getOptionType(),
                this.input.getUnderlying(),
                this.input.getMaturity(),
                this.input.getStrike(),
                this.input.getSpot(),
                this.input.getVolatility(),
                this.input.getRate());
    }

    @When("I compute the price with market data the option: $table")
    public void WhenIComputeThePriceWithMarketData(ExamplesTable table)
    {
        OptionType optionType = Enum.valueOf(OptionType.class, table.getRow(0).get("type"));
        String maturityString = table.getRow(0).get("maturity");
        Double strike = Double.valueOf(table.getRow(0).get("strike"));
        String underlyingName = table.getRow(0).get("underlying");

        LocalDate maturity = this.parseMaturity(maturityString);

        this.result = pricer.priceWithMarketData(optionType, underlyingName, maturity, strike);
    }

    @Then("the result should be $expected")
    public void ThenTheResultShouldBe(double expected)
    {
        assertThat(this.result.getPrice()).isEqualTo(expected).as("Wrong price");
    }

    public LocalDate parseMaturity(String maturityString)
    {
        DateOffset offset = dateOffsetConverter.parseOffset(maturityString);

        LocalDate now = this.dateService.getNow();

        if (offset.getType() == DateOffsetType.MONTH)
        {
            return now.plusMonths(offset.getOffset());
        }
        else
        {
            return now.plusYears(offset.getOffset());
        }
    }

}
