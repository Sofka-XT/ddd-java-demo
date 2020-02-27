package co.com.sofka.application.commands;

import co.com.sofka.business.UseCase;
import co.com.sofka.usecases.*;
import com.google.cloud.Tuple;
import org.springframework.stereotype.Component;

@Component
public class UseCaseFactory {

    public Tuple<UseCase, UseCase.RequestValues> getUseCase(IssueDTO body) {
        switch (body.type) {
            case "IssueCreateUseCase":
                return getInstanceOfIssueCreateUseCase(body);
            case "IssueCreateAndUpdateUseCase":
                return getInstanceOfIssueCreateAndUpdateUseCase(body);
            case "IssueDeleteUseCase":
                return getInstanceOfIssueDeleteUseCase(body);
            case "IssueUpdateUseCase":
                return getInstanceOfIssueUpdateUseCase(body);
            case "LabelAddToIssueUseCase":
                return getInstanceOfLabelAddToIssueUseCase(body);
            case "LabelDeleteUseCase":
                return getInstanceOfLabelDeleteUseCase(body);
            default:
                throw new UseCaseFactoryException("The command type is incorrect");
        }
    }

    private Tuple<UseCase, UseCase.RequestValues> getInstanceOfLabelDeleteUseCase(IssueDTO body) {
        return Tuple.of( new LabelDeleteUseCase(),
                new LabelDeleteUseCase.Request(body.uuid,
                        body.basicInformation,
                        body.labelListId,
                        body.color,
                        body.title));
    }

    private Tuple<UseCase, UseCase.RequestValues> getInstanceOfLabelAddToIssueUseCase(IssueDTO body) {
        return Tuple.of( new LabelAddToIssueUseCase(),
                new LabelAddToIssueUseCase.Request(body.uuid,
                        body.basicInformation,
                        body.labelListId,
                        body.color,
                        body.title));
    }

    private Tuple<UseCase, UseCase.RequestValues> getInstanceOfIssueUpdateUseCase(IssueDTO body) {
        return Tuple.of( new IssueUpdateUseCase(),
                new IssueUpdateUseCase.Request(body.uuid, body.basicInformation, body.status));
    }

    private Tuple<UseCase, UseCase.RequestValues> getInstanceOfIssueDeleteUseCase(IssueDTO body) {
        return Tuple.of( new IssueDeleteUseCase(),
                new IssueDeleteUseCase.Request(body.uuid, body.basicInformation));
    }

    private Tuple<UseCase, UseCase.RequestValues> getInstanceOfIssueCreateAndUpdateUseCase(IssueDTO body) {
        return Tuple.of( new IssueCreateAndUpdateUseCase(),
                new IssueCreateAndUpdateUseCase.Request(body.uuid,
                        body.basicInformation,
                        body.period,
                        body.person,
                        body.status));
    }

    private Tuple<UseCase, UseCase.RequestValues> getInstanceOfIssueCreateUseCase(IssueDTO body) {
        return Tuple.of( new IssueCreateUseCase(),
                new IssueCreateUseCase.Request(body.uuid, body.basicInformation));
    }

}
