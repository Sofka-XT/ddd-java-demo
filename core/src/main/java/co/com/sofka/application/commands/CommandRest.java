package co.com.sofka.application.commands;

import co.com.sofka.core.issue.commands.IssueCreateCommand;
import co.com.sofka.core.issue.commands.IssueUpdateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/")
public class CommandRest {


    CommandHandler commandHandler;

    @Autowired
    public CommandRest(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping(value = "command/issueCreate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void setCommand(@RequestBody IssueCreateCommand body) {
        commandHandler.execute(body);
    }

    @PostMapping(value = "command/issueUpdate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void setCommand(@RequestBody IssueUpdateCommand body) {
        commandHandler.execute(body);
    }

}
