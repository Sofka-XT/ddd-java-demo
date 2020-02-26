package co.com.sofka.application;

import co.com.sofka.business.UseCase;
import co.com.sofka.business.UseCaseHandler;
import co.com.sofka.infraestructure.SubscriberFirestoreRepository;

import com.google.cloud.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/")
public class CommandRest {

    UseCaseFactory useCaseFactory;

    @Autowired
    public CommandRest(UseCaseFactory useCaseFactory) {
        this.useCaseFactory = useCaseFactory;
    }

    @PostMapping("command")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> setCommand(@RequestBody IssueDTO body) {
        Tuple<UseCase, UseCase.RequestValues> useCase = useCaseFactory.getUseCase(body);

        UseCaseHandler.SimplePublisher pub = UseCaseHandler.getInstance()
                .execute(useCase.x(), useCase.y());

        pub.subscribe(new SubscriberFirestoreRepository(body.uuid));

        return new ResponseEntity<Map<String, Object>>((Map<String, Object>) null, HttpStatus.OK);
    }

}
