package co.com.sofka.application.commands;

import co.com.sofka.infraestructure.FirestoreRepository;
import co.com.sofka.usecases.IssueCreateUseCase;
import co.com.sofka.usecases.IssueUpdateUseCase;
import co.com.sofka.usecases.handlers.CommandHandlerCreate;
import co.com.sofka.usecases.handlers.CommandHandlerUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfigure {

    @Bean
    public CommandBuilder configUseCases(
            CommandHandlerCreate issueCreateUseCase,
            CommandHandlerUpdate issueUpdateUseCase) {

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
    public CommandHandlerUpdate commandHandlerUpdate(FirestoreRepository firestoreRepository){
        return new CommandHandlerUpdate(firestoreRepository);
    }

    @Bean
    public CommandHandlerCreate commandHandlerCreate(FirestoreRepository firestoreRepository){
        return new CommandHandlerCreate(firestoreRepository);
    }
}
