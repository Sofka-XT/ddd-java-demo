package co.com.sofka.application.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/")
public class CommandRest {

    CommandService commandService;

    @Autowired
    public CommandRest(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping(value = "command")
    @ResponseStatus(HttpStatus.OK)
    public void setCommand(@RequestBody Map<String, Object> body) {
        IssueCommand commandWrapper = new IssueCommand(
                body.get("type").toString(),
                body.get("aggregateId").toString()
        );
        commandService.executor(commandWrapper);
    }

}
