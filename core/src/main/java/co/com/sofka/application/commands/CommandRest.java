package co.com.sofka.application.commands;

import co.com.sofka.infraestructure.handle.CommandHandler;
import co.com.sofka.core.issue.commands.IssueCreateCommand;
import co.com.sofka.core.issue.commands.IssueUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/")
public class CommandRest {


    CommandHandler<IssueCreateCommand> commandCreated;
    CommandHandler<IssueUpdateCommand> commandUpdated;

    @Autowired
    public CommandRest(CommandHandler<IssueCreateCommand> commandCreated, CommandHandler<IssueUpdateCommand> commandUpdated) {
        this.commandCreated = commandCreated;
        this.commandUpdated = commandUpdated;
    }

    @PostMapping(value = "command/issueCreate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void setCommand(@RequestBody IssueCreateCommand command) {
        commandCreated.execute(command);
    }

    @PostMapping(value = "command/issueUpdate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void setCommand(@RequestBody IssueUpdateCommand command) {
        commandUpdated.execute(command);

    }

}
