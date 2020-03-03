package co.com.sofka.generic.values;


import com.fasterxml.jackson.annotation.JsonProperty;

import static co.com.sofka.generic.DomainAssertions.checkNonNullForBasicInformationProperties;

public class BasicInformationProperty {

    private final String title;
    private final String description;

    public BasicInformationProperty(@JsonProperty("title") final String title,
                                    @JsonProperty("description") final String description) {
        checkNonNullForBasicInformationProperties(title, "title can't be null");
        checkNonNullForBasicInformationProperties(description, "Description can't be null");
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


}
