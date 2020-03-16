package co.com.sofka.core.issue.commands;

import co.com.sofka.domain.generic.Command;

public class IssueDeleteCommand extends Command  {

    private final String issueListId;
    private final String issueId;

    public IssueDeleteCommand(String type, String issueListId, String issueId) {
        super(type);
        this.issueListId = issueListId;
        this.issueId = issueId;
    }

    public String getIssueListId() {
        return issueListId;
    }

    public String getIssueId() {
        return issueId;
    }
}
