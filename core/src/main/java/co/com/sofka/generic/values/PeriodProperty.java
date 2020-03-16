package co.com.sofka.generic.values;

import java.util.Date;

import static co.com.sofka.generic.DomainAssertions.checkNonNullForPeriodProperties;


public class PeriodProperty {

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private final Date startDate;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private final Date endDate;

    public PeriodProperty(final Date startDate,
                          final Date endDate) {
        checkNonNullForPeriodProperties(startDate, "StartDate can't be null");
        checkNonNullForPeriodProperties(endDate, "EndDate can't be null");
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
