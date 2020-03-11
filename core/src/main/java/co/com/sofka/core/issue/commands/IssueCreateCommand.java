package co.com.sofka.core.issue.commands;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.generic.values.BasicInformationProperty;

public class IssueCreateCommand extends Command {
    private final String issueListId;
    private final BasicInformationProperty basicInformation;

    public IssueCreateCommand(final String type,
                              final String issueListId,
                              final BasicInformationProperty basicInformation) {
        super(type);
        this.issueListId = issueListId;
        this.basicInformation = basicInformation;
    }

    public String getIssueListId() {
        return issueListId;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }


}
