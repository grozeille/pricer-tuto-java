package org.grozeille.pricer.utils;

import org.grozeille.pricer.DateService;
import org.joda.time.LocalDate;

public class FixedDateService implements DateService {

    private LocalDate now;
    
    public void setNow(LocalDate now){
        this.now = now;
    }
    
    public LocalDate getNow() {
        return this.now;
    }

}
