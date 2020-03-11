package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.domain.generic.DomainEvent;


public class IssueDeleted extends DomainEvent {

    private final IssueId issueId;

    public IssueDeleted(final IssueListId issueListId,  final IssueId issueId) {
        super("issue_deleted", issueListId);
        this.issueId = issueId;
    }

    public IssueId getIssueId() {
        return issueId;
    }

}
