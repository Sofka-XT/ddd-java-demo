package co.com.sofka.application.querys;

import co.com.sofka.infrastructure.IssueEntityRepository;
import co.com.sofka.handlers.quieries.QueryHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfigure {

    @Bean
    public QueryHandlerImpl queryHandlerGetById(final IssueEntityRepository repository) {
        return new QueryHandlerImpl(repository);
    }
}
