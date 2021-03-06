package co.com.sofka.application.commands;

import co.com.sofka.infraestructure.repository.EventStoreRepository;

import co.com.sofka.infrastructure.FirestoreRepository;
import co.com.sofka.infrastructure.RabbitDomainEventBus;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.usecases.IssueCreateUseCase;
import co.com.sofka.usecases.IssueDeleteUseCase;
import co.com.sofka.usecases.IssueUpdateUseCase;
import co.com.sofka.usecases.handlers.commands.CommandHandlerCreate;
import co.com.sofka.usecases.handlers.commands.CommandHandlerDelete;
import co.com.sofka.usecases.handlers.commands.CommandHandlerUpdate;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfigure {

    @Bean
    public IssueCreateUseCase issueCreateUseCase() {
        return new IssueCreateUseCase();
    }

    @Bean
    public IssueUpdateUseCase issueUpdateUseCase(EventStoreRepository repository) {
        return new IssueUpdateUseCase(repository);
    }

    @Bean
    public IssueDeleteUseCase IssueDeleteUseCase(EventStoreRepository repository) {
        return new IssueDeleteUseCase(repository);
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
    public CommandHandlerDelete CommandHandlerDelete(final FirestoreRepository firestoreRepository,
                                                     final EventBus eventBus) {
        return new CommandHandlerDelete(firestoreRepository, eventBus);
    }

    @Bean
    public EventBus rabbitDomainEventBus(final RabbitTemplate rabbitTemplate, RabbitAdmin rabbitAdmin) {
        return new RabbitDomainEventBus(rabbitTemplate, rabbitAdmin);
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
