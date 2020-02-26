package co.com.sofka;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class App 
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
