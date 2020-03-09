package co.com.sofka.core.issue.commands;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.generic.values.BasicInformationProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueCreateCommand extends Command {
    private final String aggregateRootId;
    private final BasicInformationProperty basicInformation;

    public IssueCreateCommand(@JsonProperty("type") final String type,
                              @JsonProperty("aggregateRootId") final String aggregateRootId,
                              @JsonProperty("basicInformation") final BasicInformationProperty basicInformation) {
        super(type);
        this.aggregateRootId = aggregateRootId;
        this.basicInformation = basicInformation;
    }

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }


}
