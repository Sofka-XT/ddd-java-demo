package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.domain.generic.DomainEvent;


public class IssueLabelsDeleted extends DomainEvent {

    private final IssueId issueId;

    public IssueLabelsDeleted(final IssueListId issueListId, final IssueId issueId) {
        super("issue_label_deleted", issueListId);
        this.issueId = issueId;
    }

    public IssueId getIssueId() {
        return issueId;
    }


}
