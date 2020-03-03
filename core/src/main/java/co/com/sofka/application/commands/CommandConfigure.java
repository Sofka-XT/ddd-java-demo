package co.com.sofka.application.commands;

import co.com.sofka.infraestructure.FirestoreRepository;
import co.com.sofka.infraestructure.RabbitDomainEventBus;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.usecases.IssueCreateUseCase;
import co.com.sofka.usecases.IssueUpdateUseCase;
import co.com.sofka.usecases.handlers.commands.CommandHandlerCreate;
import co.com.sofka.usecases.handlers.commands.CommandHandlerUpdate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfigure {

    @Bean
    public CommandBuilder configUseCases(
            final CommandHandlerCreate issueCreateUseCase,
            final CommandHandlerUpdate issueUpdateUseCase) {

        return new CommandBuilder.Builder()
                .registerUseCase("issue_create", issueCreateUseCase)
                .registerUseCase("issue_update", issueUpdateUseCase)
                .build();
    }

    @Bean
    public IssueCreateUseCase issueCreateUseCase() {
        return new IssueCreateUseCase();
    }

    @Bean
    public IssueUpdateUseCase issueUpdateUseCase() {
        return new IssueUpdateUseCase();
    }

    @Bean
    public CommandHandlerUpdate commandHandlerUpdate(final FirestoreRepository firestoreRepository,
                                                     final EventBus eventBus) {
        return new CommandHandlerUpdate(firestoreRepository, eventBus);
    }

    @Bean
    public CommandHandlerCreate commandHandlerCreate(final FirestoreRepository firestoreRepository,
                                                     final EventBus eventBus) {
        return new CommandHandlerCreate(firestoreRepository, eventBus);
    }

    @Bean
    public EventBus rabbitDomainEventBus(final RabbitTemplate rabbitTemplate) {
        return new RabbitDomainEventBus(rabbitTemplate);
    }
}
