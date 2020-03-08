package co.com.sofka.core.label.values;

import com.fasterxml.jackson.annotation.JsonProperty;

import static co.com.sofka.generic.DomainAssertions.checkNonNullForLabel;

public class LabelListId {


    private final String uuid;

    public LabelListId(@JsonProperty("uuid") final String uuid) {
        checkNonNullForLabel(uuid, "Label Id can't be null");
        this.uuid = uuid;
    }

}
