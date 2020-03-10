package co.com.sofka.core.issue.commands;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.generic.values.StatusProperty;

public class IssueUpdateCommand extends Command {
    private final String aggregateRootId;
    private final String issueId;
    private final StatusProperty status;

    public IssueUpdateCommand(final String type,
                              final String aggregateRootId,
                              final String issueId,
                              final StatusProperty status) {
        super(type);
        this.aggregateRootId = aggregateRootId;
        this.issueId = issueId;
        this.status = status;
    }

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public String getIssueId() {
        return issueId;
    }

    public StatusProperty getStatus() {
        return status;
    }
}
