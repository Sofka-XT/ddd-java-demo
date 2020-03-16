package co.com.sofka.usecases.handlers.commands;

import co.com.sofka.business.asyn.SubscriberEvent;
import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.handle.CommandHandler;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.core.issue.commands.IssueUpdateCommand;
import co.com.sofka.infrastructure.FirestoreRepository;
import co.com.sofka.infrastructure.SubscriberFirestore;
import co.com.sofka.usecases.IssueUpdateUseCase;

public class CommandHandlerUpdate implements CommandHandler<IssueUpdateCommand> {

    private FirestoreRepository firestoreRepository;
    private EventBus eventBus;

    public CommandHandlerUpdate(final FirestoreRepository firestoreRepository,
                                final EventBus eventBus) {
        this.firestoreRepository = firestoreRepository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(final IssueUpdateCommand command) {

        UseCaseHandler.getInstance()
                .asyncExecutor(new IssueUpdateUseCase(firestoreRepository),
                        new IssueUpdateUseCase.Request(new IssueListId(command.getIssueListId()),
                        new IssueId(command.getIssueId()), command.getStatus())
                )
                .subscribe(new SubscriberEvent<>(firestoreRepository, eventBus));

    }
}
