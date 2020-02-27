package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.values.StatusProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueStatusUpdated extends DomainEvent {
    private final IssueId issueId;
    private final StatusProperty statusProperty;

    public IssueStatusUpdated(@JsonProperty("issueId") IssueId issueId, @JsonProperty("statusProperty") StatusProperty statusProperty) {
        super("issue_status_updated");
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
