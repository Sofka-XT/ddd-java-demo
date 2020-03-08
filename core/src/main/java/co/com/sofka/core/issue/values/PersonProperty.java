package co.com.sofka.core.issue.values;


import com.fasterxml.jackson.annotation.JsonProperty;

import static co.com.sofka.generic.DomainAssertions.checkNonNullForPersonProperties;

public class PersonProperty {

    private final String name;

    public PersonProperty(@JsonProperty("name") final String name) {
        checkNonNullForPersonProperties(name, "Name can't be null");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
