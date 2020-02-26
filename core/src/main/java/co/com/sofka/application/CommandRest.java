package co.com.sofka.application;

import co.com.sofka.business.UseCase;
import co.com.sofka.business.UseCaseHandler;
import co.com.sofka.infraestructure.SubscriberFirestoreRepository;

import com.google.cloud.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("api/")
public class CommandRest {

    UseCaseFactory useCaseFactory;

    @Autowired
    public CommandRest(UseCaseFactory useCaseFactory) {
        this.useCaseFactory = useCaseFactory;
    }

    @PostMapping(value="command", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> setCommand(@RequestBody IssueDTO body) {
        Map<String, Object> respose = new HashMap<>();
        Tuple<UseCase, UseCase.RequestValues> useCase = useCaseFactory.getUseCase(body);

        UseCaseHandler.SimplePublisher pub = UseCaseHandler.getInstance()
                .execute(useCase.x(), useCase.y());

        pub.subscribe(new SubscriberFirestoreRepository(body.uuid));

        return new ResponseEntity<Map<String, Object>>(respose, HttpStatus.OK);
    }

}
