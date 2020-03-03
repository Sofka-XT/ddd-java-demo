package co.com.sofka.application.commands;

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
@RequestMapping("api/")
public class CommandRest {

    private CommandHandler<IssueCreateCommand> commandCreated;
    private CommandHandler<IssueUpdateCommand> commandUpdated;

    @Autowired
    public CommandRest(final CommandHandler<IssueCreateCommand> commandCreated,
                       final CommandHandler<IssueUpdateCommand> commandUpdated) {
        this.commandCreated = commandCreated;
        this.commandUpdated = commandUpdated;
    }

    @PostMapping(value = "command/issueCreate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void setCommand(@RequestBody final IssueCreateCommand command) {
        commandCreated.execute(command);
    }

    @PostMapping(value = "command/issueUpdate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void setCommand(@RequestBody final IssueUpdateCommand command) {
        commandUpdated.execute(command);

    }

}
