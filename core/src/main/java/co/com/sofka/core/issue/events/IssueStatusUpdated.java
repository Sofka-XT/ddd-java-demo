package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.values.StatusProperty;

public class IssueStatusUpdated extends DomainEvent {
    private final IssueId issueId;
    private final StatusProperty statusProperty;

    public IssueStatusUpdated(final IssueListId issueListId, final IssueId issueId,
                              final StatusProperty statusProperty) {
        super("issue_status_updated", issueListId);
        this.issueId = issueId;
        this.statusProperty = statusProperty;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public StatusProperty getStatusProperty() {
        return statusProperty;
    }

}
