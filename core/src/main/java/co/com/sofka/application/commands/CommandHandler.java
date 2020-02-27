package co.com.sofka.application.commands;

import co.com.sofka.core.issue.commands.IssueCreateCommand;
import co.com.sofka.core.issue.commands.IssueUpdateCommand;

public interface CommandHandler {

    void execute(IssueCreateCommand issueCreateCommand);

    void execute(IssueUpdateCommand issueUpdateCommand);

}



