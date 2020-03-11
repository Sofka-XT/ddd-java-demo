package co.com.sofka.core.issue.commands;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.generic.values.StatusProperty;

public class IssueUpdateCommand extends Command {
    private final String issueListId;
    private final String issueId;
    private final StatusProperty status;

    public IssueUpdateCommand(final String type,
                              final String issueListId,
                              final String issueId,
                              final StatusProperty status) {
        super(type);
        this.issueListId = issueListId;
        this.issueId = issueId;
        this.status = status;
    }

    public String getIssueListId() {
        return issueListId;
    }

    public String getIssueId() {
        return issueId;
    }

    public StatusProperty getStatus() {
        return status;
    }
}
