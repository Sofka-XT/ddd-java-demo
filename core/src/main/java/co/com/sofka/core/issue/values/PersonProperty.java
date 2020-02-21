package co.com.sofka.core.issue.values;


import static co.com.sofka.generic.DomainAssertions.checkNonNullForPersonProperties;

public class PersonProperty {

    private final String name;

    public PersonProperty(String name) {
        checkNonNullForPersonProperties(name,"Name can't be null");
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
