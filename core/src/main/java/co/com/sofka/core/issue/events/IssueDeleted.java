package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.domain.DomainEvent;


public class IssueDeleted extends DomainEvent {

    private final IssueId issueId;

    public IssueDeleted(IssueId issueId) {
        super("issue_deleted");
        this.issueId = issueId;
    }

    public IssueId getIssueId() {
        return issueId;
    }

}
