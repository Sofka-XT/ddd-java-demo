package co.com.sofka.core.issue.commands;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.generic.values.BasicInformationProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueCreateCommand extends Command {
    private String uuid;
    private BasicInformationProperty basicInformation;

    public IssueCreateCommand(@JsonProperty("type") String type,
                              @JsonProperty("uuid") String uuid,
                              @JsonProperty("basicInformation") BasicInformationProperty basicInformation) {
        super(type);
        this.uuid = uuid;
        this.basicInformation = basicInformation;
    }

    public String getUuid() {
        return uuid;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }
}
