package co.com.sofka.application;

import co.com.sofka.infrastructure.BdConnection;
import co.com.sofka.infrastructure.FirestoreRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfigure {
    @Bean
    public FirestoreRepository firestoreRepository() {
        return new FirestoreRepository(BdConnection.getDatabaseInstance());
    }
}
