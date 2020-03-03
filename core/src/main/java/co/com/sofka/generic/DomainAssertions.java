package co.com.sofka.generic;

import co.com.sofka.core.label.Label;
import co.com.sofka.generic.exeption.BasicInformationException;
import co.com.sofka.generic.exeption.IssueException;
import co.com.sofka.generic.exeption.LabelException;
import co.com.sofka.generic.exeption.PeriodException;
import co.com.sofka.generic.exeption.PersonException;


import java.util.Collection;


public final class DomainAssertions {
    private DomainAssertions() {
    }

    public static void checkNonNullForBasicInformationProperties(final Object object, final String message) {
        if (object == null) {
            throw new BasicInformationException(message);
        }
    }

    public static void checkNonNullForPeriodProperties(final Object object, final String message) {
        if (object == null) {
            throw new PeriodException(message);
        }
    }

    public static void checkNonNullForIssue(final Object obj, final String message) {
        if (obj == null) {
            throw new IssueException(message);
        }
    }

    public static void checkNonNullForPersonProperties(final Object object, final String message) {
        if (object == null) {
            throw new PersonException(message);
        }
    }

    public static void checkNonNullForLabel(final Object obj, final String message) {
        if ((obj == null) || (obj.toString().equals(""))) {
            throw new LabelException(message);
        }
    }

    public static void checkIfLabelCollectionIsEmpty(final Collection<Label> list, final String errorMessage) {
        if (list.isEmpty()) {
            throw new LabelException(errorMessage);
        }
    }


}
