package co.com.sofka.application.commands;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.core.issue.commands.IssueCreateCommand;
import co.com.sofka.core.issue.commands.IssueUpdateCommand;
import co.com.sofka.infraestructure.FirestoreRepository;
import co.com.sofka.infraestructure.SubscriberFirestore;
import co.com.sofka.usecases.IssueCreateUseCase;
import co.com.sofka.usecases.IssueUpdateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandHandlerService implements CommandHandler {

    private FirestoreRepository firestoreRepository;

    @Autowired
    public CommandHandlerService(FirestoreRepository firestoreRepository) {
        this.firestoreRepository = firestoreRepository;
    }

    @Override
    public void execute(IssueCreateCommand issueCreateCommand) {
        UseCaseHandler.getInstance()
                .asyncExecutor(new IssueCreateUseCase(),
                        new IssueCreateUseCase.Request(issueCreateCommand.getUuid(), issueCreateCommand.getBasicInformation())
                ).subscribe(new SubscriberFirestore(issueCreateCommand.getUuid(), firestoreRepository));
    }

    @Override
    public void execute(IssueUpdateCommand issueUpdateCommand) {
        UseCaseHandler.getInstance()
                .asyncExecutor(new IssueUpdateUseCase(),
                        new IssueUpdateUseCase.Request(issueUpdateCommand.getUuid(),
                                issueUpdateCommand.getBasicInformation(), issueUpdateCommand.getStatus())
                ).subscribe(new SubscriberFirestore(issueUpdateCommand.getUuid(), firestoreRepository));
    }
}
