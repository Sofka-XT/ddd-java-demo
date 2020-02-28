package co.com.sofka.usecases.handlers.commands;

import co.com.sofka.infraestructure.handle.CommandHandler;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.core.issue.commands.IssueCreateCommand;
import co.com.sofka.infraestructure.FirestoreRepository;
import co.com.sofka.infraestructure.SubscriberFirestore;
import co.com.sofka.usecases.IssueCreateUseCase;

public class CommandHandlerCreate implements CommandHandler<IssueCreateCommand> {

    private FirestoreRepository firestoreRepository;

    public CommandHandlerCreate(FirestoreRepository firestoreRepository) {
        this.firestoreRepository = firestoreRepository;
    }


    @Override
    public void execute(IssueCreateCommand command) {

        UseCaseHandler.getInstance()
                .asyncExecutor(new IssueCreateUseCase(),
                        new IssueCreateUseCase.Request(command.getUuid(),
                                command.getBasicInformation())
                ).subscribe(new SubscriberFirestore(command.getUuid(), firestoreRepository));

    }


}
