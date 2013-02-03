package org.grozeille.pricer;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class Pricer {
    private DateService dateService;
    
    private MarketDataService marketDataService;
    
    public MarketDataService getMarketDataService() {
        return marketDataService;
    }
    
    public DateService getDateService() {
        return dateService;
    }
    
    public void setMarketDataService(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }
    
    public void setDateService(DateService dateService) {
        this.dateService = dateService;
    }

    public Option price(OptionType optionType, String underlyingName, LocalDate maturity, double strike, double spot, double volatility, double interestRate) {
        
        Option option = new Option();
        option.setOptionType(optionType);
        option.setUnderlyingName(underlyingName);
        option.setSpot(spot);
        option.setMaturity(maturity);
        option.setStrike(strike);

        if (optionType ==  OptionType.Call)
        {
            option.setPayOff(Math.max(spot - strike, 0));
        }
        else //if (optionType == PricerTuto.OptionType.Put)
        {
            option.setPayOff(Math.max(strike - spot, 0));
        }


        option.setVolatility(volatility);
        option.setInterestRate(interestRate);
        double timeToExpirationYears = Days.daysBetween(this.dateService.getNow(), maturity).getDays() / 260.0;
        option.setPrice(BlackSholes.Price(
            optionType,
            option.getSpot(),
            option.getStrike(),
            timeToExpirationYears,
            option.getInterestRate(),
            option.getVolatility()));
        option.setDelta(BlackSholes.Delta(
            optionType,
            option.getSpot(),
            option.getStrike(),
            timeToExpirationYears,
            option.getInterestRate(),
            option.getVolatility()));
        option.setGamma(BlackSholes.Gamma(
            optionType,
            option.getSpot(),
            option.getStrike(),
            timeToExpirationYears,
            option.getInterestRate(),
            option.getVolatility()));

        return option;
    }

    public Option priceWithMarketData(OptionType optionType, String underlyingName, LocalDate maturity, Double strike) {
        double spot = this.marketDataService.getSpot(underlyingName);
        
        int maturityDays = Days.daysBetween(this.dateService.getNow(), maturity).getDays();
        
        double volatility = this.marketDataService.getHistoricalVolatility(underlyingName, this.dateService.getNow(), maturityDays);
        double interestRate = this.marketDataService.getInterestRate();

        return this.price(optionType, underlyingName, maturity, strike, spot, volatility, interestRate);
    }
}
