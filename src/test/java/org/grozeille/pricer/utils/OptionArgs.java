package org.grozeille.pricer.utils;

import org.grozeille.pricer.OptionType;
import org.joda.time.LocalDate;

public class OptionArgs {
    
    private OptionType optionType;

    private String underlying;

    private double spot;

    private double strike;

    private LocalDate maturity;

    private double rate;

    private double volatility;

    public OptionType getOptionType() {
        return optionType;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    public double getSpot() {
        return spot;
    }

    public void setSpot(double spot) {
        this.spot = spot;
    }

    public double getStrike() {
        return strike;
    }

    public void setStrike(double strike) {
        this.strike = strike;
    }

    public LocalDate getMaturity() {
        return maturity;
    }

    public void setMaturity(LocalDate maturity) {
        this.maturity = maturity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }
}
