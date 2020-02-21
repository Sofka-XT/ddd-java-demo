package co.com.sofka.generic.values;

import java.util.Date;

import static co.com.sofka.generic.DomainAssertions.*;


public class PeriodProperty {

    private final Date startDate;
    private final Date endDate;

    public PeriodProperty(Date startDate, Date endDate) {
        checkNonNullForPeriodProperties(startDate,"StartDate can't be null");
        checkNonNullForPeriodProperties(endDate,"EndDate can't be null");
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
