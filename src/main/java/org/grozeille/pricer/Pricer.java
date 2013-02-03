package org.grozeille.pricer;

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

    public Option price(OptionType optionType, String underlying, LocalDate maturity, double strike, double spot, double volatility, double rate) {
        // TODO Auto-generated method stub
        return null;
    }

    public Option priceWithMarketData(OptionType optionType, String underlyingName, LocalDate maturity, Double strike) {
        // TODO Auto-generated method stub
        return null;
    }
}
