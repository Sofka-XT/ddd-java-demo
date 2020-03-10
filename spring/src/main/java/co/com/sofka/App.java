package co.com.sofka;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
public class App 
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
