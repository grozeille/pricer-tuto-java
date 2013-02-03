package org.grozeille.pricer;

import org.joda.time.LocalDate;

public class Option {

    private String underlyingName;

    private OptionType optionType;

    private Double spot;

    private LocalDate maturity;

    private Double strike;

    private Double volatility;

    private Double interestRate;

    private Double price;

    private Double delta;

    private Double gamma;

    private Double payOff;

    public String getUnderlyingName() {
        return underlyingName;
    }

    public void setUnderlyingName(String underlyingName) {
        this.underlyingName = underlyingName;
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }

    public Double getSpot() {
        return spot;
    }

    public void setSpot(Double spot) {
        this.spot = spot;
    }

    public LocalDate getMaturity() {
        return maturity;
    }

    public void setMaturity(LocalDate maturity) {
        this.maturity = maturity;
    }

    public Double getStrike() {
        return strike;
    }

    public void setStrike(Double strike) {
        this.strike = strike;
    }

    public Double getVolatility() {
        return volatility;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public Double getGamma() {
        return gamma;
    }

    public void setGamma(Double gamma) {
        this.gamma = gamma;
    }

    public Double getPayOff() {
        return payOff;
    }

    public void setPayOff(Double payOff) {
        this.payOff = payOff;
    }

}
