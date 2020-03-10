package co.com.sofka.core.issue.commands;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.StatusProperty;

public class IssueUpdateCommand extends Command {
    private final String aggregateRootId;
    private final BasicInformationProperty basicInformation;
    private final StatusProperty status;

    public IssueUpdateCommand(final String type,
                              final String aggregateRootId,
                              final BasicInformationProperty basicInformation,
                              final StatusProperty status) {
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
