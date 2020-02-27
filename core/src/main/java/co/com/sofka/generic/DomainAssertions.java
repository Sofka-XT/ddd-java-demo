package co.com.sofka.generic;

import co.com.sofka.core.label.Label;
import co.com.sofka.generic.exeption.*;

import java.util.Collection;


public class DomainAssertions {
    private DomainAssertions() {
    }

    public static void checkNonNullForBasicInformationProperties(Object object, String message) {
        if (object == null) {
            throw new BasicInformationException(message);
        }
    }

    public static void checkNonNullForPeriodProperties(Object object, String message) {
        if (object == null) {
            throw new PeriodException(message);
        }
    }

    public static void checkNonNullForIssue(Object obj, String message) {
        if (obj == null) {
            throw new IssueException(message);
        }
    }

    public static void checkNonNullForPersonProperties(Object object, String message) {
        if (object == null) {
            throw new PersonException(message);
        }
    }

    public static void checkNonNullForLabel(Object obj, String message) {
        if ((obj == null) || (obj.toString().equals(""))) {
            throw new LabelException(message);
        }
    }

    public static void checkIfLabelCollectionIsEmpty(Collection<Label> list, String errorMessage) {
        if (list.isEmpty()) {
            throw new LabelException(errorMessage);
        }
    }


}
