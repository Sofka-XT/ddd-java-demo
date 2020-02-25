package co.com.sofka.infraestructure;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class BdConnection {

    public static Firestore firebaseInstance() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("event-soucing-demo-firebase-adminsdk-iqrs0-ff0b4b6e2c.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        Firestore db = FirestoreClient.getFirestore();

        return db;
    }
}
