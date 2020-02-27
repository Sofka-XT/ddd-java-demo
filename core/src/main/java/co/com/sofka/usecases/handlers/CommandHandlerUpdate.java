package co.com.sofka.usecases.handlers;

import co.com.sofka.application.commands.CommandHandler;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.core.issue.commands.IssueUpdateCommand;
import co.com.sofka.infraestructure.FirestoreRepository;
import co.com.sofka.infraestructure.SubscriberFirestore;
import co.com.sofka.usecases.IssueUpdateUseCase;

public class CommandHandlerUpdate implements CommandHandler<IssueUpdateCommand> {

    private FirestoreRepository firestoreRepository;


    public CommandHandlerUpdate(FirestoreRepository firestoreRepository) {
        this.firestoreRepository = firestoreRepository;
    }

    @Override
    public void execute(IssueUpdateCommand command) {

        UseCaseHandler.getInstance()
                .asyncExecutor(new IssueUpdateUseCase(),
                        new IssueUpdateUseCase.Request(command.getUuid(),
                                command.getBasicInformation(), command.getStatus())
                ).subscribe(new SubscriberFirestore(command.getUuid(), firestoreRepository));

    }
}
