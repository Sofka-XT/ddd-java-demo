package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueWithBasicInformationCreated extends DomainEvent {

    private final BasicInformationProperty basicInformation;
    private final IssueId issueId;

    public IssueWithBasicInformationCreated(@JsonProperty("issueId") IssueId issueId, @JsonProperty("basicInformation") BasicInformationProperty basicInformation) {
        super("issue_with_basic_information_created");
        this.issueId = issueId;
        this.basicInformation = basicInformation;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }

    public IssueId getIssueId() {
        return issueId;
    }

}
