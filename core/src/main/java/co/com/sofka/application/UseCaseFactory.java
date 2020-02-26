package co.com.sofka.application;

import co.com.sofka.business.UseCase;
import co.com.sofka.usecases.*;
import com.google.cloud.Tuple;
import org.springframework.stereotype.Component;

@Component
public class UseCaseFactory {

    public Tuple<UseCase, UseCase.RequestValues> getUseCase(IssueDTO body) {
        switch (body.type) {
            case "IssueCreateUseCase":
                return Tuple.of( new IssueCreateUseCase(),
                        new IssueCreateUseCase.Request(body.uuid, body.basicInformation));
            case "IssueCreateAndUpdateUseCase":
                return Tuple.of( new IssueCreateAndUpdateUseCase(),
                        new IssueCreateAndUpdateUseCase.Request(body.uuid,
                                body.basicInformation,
                                body.period,
                                body.person,
                                body.status));
            case "IssueDeleteUseCase":
                return Tuple.of( new IssueDeleteUseCase(),
                        new IssueDeleteUseCase.Request(body.uuid, body.basicInformation));
            case "IssueUpdateUseCase":
                return Tuple.of( new IssueUpdateUseCase(),
                        new IssueUpdateUseCase.Request(body.uuid, body.basicInformation, body.status));
            case "LabelAddToIssueUseCase":
                return Tuple.of( new LabelAddToIssueUseCase(),
                        new LabelAddToIssueUseCase.Request(body.uuid,
                                body.basicInformation,
                                body.labelListId,
                                body.color,
                                body.title));
            case "LabelDeleteUseCase":
                return Tuple.of( new LabelDeleteUseCase(),
                        new LabelDeleteUseCase.Request(body.uuid,
                                body.basicInformation,
                                body.labelListId,
                                body.color,
                                body.title));
            default:
                throw new RuntimeException();
        }
    }

}
