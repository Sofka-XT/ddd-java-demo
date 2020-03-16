package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.domain.generic.DomainEvent;


public class IssuePersonUpdated extends DomainEvent {

    private final IssueId issueId;
    private final PersonProperty person;

    public IssuePersonUpdated(final IssueListId issueListId, final IssueId issueId, final PersonProperty person) {
        super("issue_person_updated", issueListId);
        this.issueId = issueId;
        this.person = person;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public PersonProperty getPerson() {
        return person;
    }


}
