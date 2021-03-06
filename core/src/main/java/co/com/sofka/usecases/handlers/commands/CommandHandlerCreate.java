package co.com.sofka.usecases.handlers.commands;

import co.com.sofka.business.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.handle.CommandHandler;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.core.issue.commands.IssueCreateCommand;
import co.com.sofka.infrastructure.FirestoreRepository;
import co.com.sofka.infrastructure.SubscriberFirestore;
import co.com.sofka.usecases.IssueCreateUseCase;

public class CommandHandlerCreate implements CommandHandler<IssueCreateCommand> {

    private FirestoreRepository firestoreRepository;
    private EventBus eventBus;

    public CommandHandlerCreate(final FirestoreRepository firestoreRepository,
                                final EventBus eventBus) {
        this.firestoreRepository = firestoreRepository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(final IssueCreateCommand command) {
        UseCaseHandler.getInstance()
            .asyncExecutor(new IssueCreateUseCase(),
                    new IssueCreateUseCase.Request(command.getIssueListId(),
                            command.getBasicInformation())
            )
                .subscribe(new SubscriberEvent<>(firestoreRepository, eventBus));
    }

}
