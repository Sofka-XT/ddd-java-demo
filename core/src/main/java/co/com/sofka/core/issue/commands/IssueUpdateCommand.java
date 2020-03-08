package co.com.sofka.core.issue.commands;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.StatusProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueUpdateCommand extends Command {
    private String aggregateRootId;
    private BasicInformationProperty basicInformation;
    private StatusProperty status;

    public IssueUpdateCommand(@JsonProperty("type") final String type,
                              @JsonProperty("aggregateRootId") final String aggregateRootId,
                              @JsonProperty("basicInformation") final BasicInformationProperty basicInformation,
                              @JsonProperty("status") final StatusProperty status) {
        super(type);
        this.aggregateRootId = aggregateRootId;
        this.basicInformation = basicInformation;
        this.status = status;
    }

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }

    public StatusProperty getStatus() {
        return status;
    }
}
