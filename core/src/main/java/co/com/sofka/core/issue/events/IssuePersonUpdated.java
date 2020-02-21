package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.domain.DomainEvent;


public class IssuePersonUpdated extends DomainEvent {

    private final IssueId issueId;
    private final PersonProperty person;

    public IssuePersonUpdated(IssueId issueId, PersonProperty person) {
        super("issue_person_updated");
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
