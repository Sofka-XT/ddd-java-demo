package co.com.sofka.application.commands;

import co.com.sofka.usecases.IssueCreateUseCase;
import co.com.sofka.usecases.IssueUpdateUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfigure {

    @Bean
    public CommandBuilder configUseCases(
            IssueCreateUseCase issueCreateUseCase,
            IssueUpdateUseCase issueUpdateUseCase) {

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

}
