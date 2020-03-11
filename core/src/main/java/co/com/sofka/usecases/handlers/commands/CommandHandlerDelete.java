package co.com.sofka.usecases.handlers.commands;

import co.com.sofka.business.asyn.SubscriberEvent;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.core.issue.commands.IssueDeleteCommand;
import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.handle.CommandHandler;
import co.com.sofka.infrastructure.FirestoreRepository;
import co.com.sofka.infrastructure.SubscriberFirestore;
import co.com.sofka.usecases.IssueDeleteUseCase;

public class CommandHandlerDelete implements CommandHandler<IssueDeleteCommand> {

    private FirestoreRepository firestoreRepository;
    private EventBus eventBus;

    public CommandHandlerDelete(final FirestoreRepository firestoreRepository,
                                final EventBus eventBus) {

        this.firestoreRepository = firestoreRepository;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(IssueDeleteCommand issueDeleteCommand) {

        UseCaseHandler.getInstance()
                .asyncExecutor(new IssueDeleteUseCase(firestoreRepository),
                        new IssueDeleteUseCase.Request(new IssueListId(issueDeleteCommand.getIssueListId()),
                                new IssueId(issueDeleteCommand.getIssueId()))
                )
                .subscribe(new SubscriberEvent<>(firestoreRepository, eventBus));

    }
}
