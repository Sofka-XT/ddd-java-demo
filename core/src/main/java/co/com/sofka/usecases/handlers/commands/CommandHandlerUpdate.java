package co.com.sofka.usecases.handlers.commands;

import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.handle.CommandHandler;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.core.issue.commands.IssueUpdateCommand;
import co.com.sofka.infraestructure.FirestoreRepository;
import co.com.sofka.infraestructure.SubscriberFirestore;
import co.com.sofka.usecases.IssueUpdateUseCase;

public class CommandHandlerUpdate implements CommandHandler<IssueUpdateCommand> {

    private FirestoreRepository firestoreRepository;
    private EventBus eventBus;

    public CommandHandlerUpdate(FirestoreRepository firestoreRepository, EventBus eventBus) {
        this.firestoreRepository = firestoreRepository;
        this.eventBus= eventBus;
    }

    @Override
    public void execute(IssueUpdateCommand command) {

        UseCaseHandler.getInstance()
                .asyncExecutor(new IssueUpdateUseCase(),
                        new IssueUpdateUseCase.Request(command.getUuid(),
                                command.getBasicInformation(), command.getStatus())
                ).subscribe(new SubscriberFirestore(command.getUuid(), firestoreRepository, eventBus));

    }
}
