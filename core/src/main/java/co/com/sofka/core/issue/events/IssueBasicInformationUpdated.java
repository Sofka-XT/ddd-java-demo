package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;


public class IssueBasicInformationUpdated extends DomainEvent {

    private final IssueId issueId;
    private final BasicInformationProperty basicInformation;

    public IssueBasicInformationUpdated(final IssueListId issueListId,  final IssueId issueId,
                                        final BasicInformationProperty basicInformation) {
        super("issue_basic_information_updated", issueListId);
        this.issueId = issueId;
        this.basicInformation = basicInformation;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }

}
