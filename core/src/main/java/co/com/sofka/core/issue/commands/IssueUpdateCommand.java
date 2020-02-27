package co.com.sofka.core.issue.commands;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.StatusProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueUpdateCommand extends Command {
    private String uuid;
    private BasicInformationProperty basicInformation;
    private StatusProperty status;

    public IssueUpdateCommand(@JsonProperty("type") String type,
                              @JsonProperty("uuid")  String uuid,
                              @JsonProperty("basicInformation") BasicInformationProperty basicInformation,
                              @JsonProperty("status") StatusProperty status) {
        super(type);
        this.uuid = uuid;
        this.basicInformation = basicInformation;
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }

    public StatusProperty getStatus() {
        return status;
    }
}
