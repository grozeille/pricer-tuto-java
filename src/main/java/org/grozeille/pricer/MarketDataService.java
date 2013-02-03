package org.grozeille.pricer;

import org.joda.time.LocalDate;

public interface MarketDataService {
    Double getInterestRate();

    Double getSpot(String underlying);

    Double getHistoricalVolatility(String underlying, LocalDate now, int days);
}
