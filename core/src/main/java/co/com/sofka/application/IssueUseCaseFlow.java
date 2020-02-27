package co.com.sofka.application;

import co.com.sofka.business.UseCaseHandler;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.usecases.IssueCreateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Flow;

@Service
public class IssueUseCaseFlow {
    private IssueCreateUseCase createUseCase;

    @Autowired
    public IssueUseCaseFlow(IssueCreateUseCase createUseCase) {
        this.createUseCase = createUseCase;
    }


    public Flow.Publisher<DomainEvent> createIssue(String uuid, BasicInformationProperty property) {
        return UseCaseHandler
                .getInstance()
                .execute(createUseCase, new IssueCreateUseCase.Request(uuid, property));
    }


}
