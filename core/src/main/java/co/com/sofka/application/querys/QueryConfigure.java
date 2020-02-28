package co.com.sofka.application.querys;

import co.com.sofka.usecases.handlers.quieries.QueryHandlerGetById;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfigure {


    @Bean
    public QueryHandlerGetById queryHandlerGetById() {
        return new QueryHandlerGetById();
    }
}
