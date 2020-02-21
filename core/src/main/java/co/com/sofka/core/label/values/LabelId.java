package co.com.sofka.core.label.values;

import static co.com.sofka.generic.DomainAssertions.checkNonNullForLabel;

public class LabelId {

    private final String uuid;

    public LabelId(String uuid) {
        checkNonNullForLabel(uuid, "Label List Id can't be null");
        this.uuid = uuid;
    }
}
