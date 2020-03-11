package co.com.sofka.application.commands;

import co.com.sofka.core.issue.commands.IssueDeleteCommand;
import co.com.sofka.infraestructure.handle.CommandHandler;
import co.com.sofka.core.issue.commands.IssueCreateCommand;
import co.com.sofka.core.issue.commands.IssueUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "api/command/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class CommandRest {

    private CommandHandler<IssueCreateCommand> commandCreated;
    private CommandHandler<IssueUpdateCommand> commandUpdated;
    private CommandHandler<IssueDeleteCommand> commandUDelete;

    @Autowired
    public CommandRest(final CommandHandler<IssueCreateCommand> commandCreated,
                       final CommandHandler<IssueUpdateCommand> commandUpdated,
                       CommandHandler<IssueDeleteCommand> commandUDelete) {
        this.commandCreated = commandCreated;
        this.commandUpdated = commandUpdated;
        this.commandUDelete = commandUDelete;
    }

    @PostMapping("issueCreate")
    @ResponseStatus(HttpStatus.OK)
    public void setCommandIssueCreated(@RequestBody final IssueCreateCommand command) {
        commandCreated.execute(command);
    }

    @PostMapping("issueUpdate")
    @ResponseStatus(HttpStatus.OK)
    public void setCommandIssueUpdate(@RequestBody final IssueUpdateCommand command) {
        commandUpdated.execute(command);
    }

    @PostMapping("issueDelete")
    @ResponseStatus(HttpStatus.OK)
    public void setCommandIssueDelete(@RequestBody final IssueDeleteCommand command) {
        commandUDelete.execute(command);
    }

}
