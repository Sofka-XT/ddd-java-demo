package co.com.sofka.infraestructure;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

import static co.com.sofka.generic.helpers.Constants.CREDENTIAL_FIREBASE;

public final class BdConnection {

    private static Firestore database;

    private BdConnection() {
    }


    public static Firestore getDatabaseInstance() {
        if (database == null) {
            database = openDatabase();
        }

        return database;
    }

    private static Firestore openDatabase() {


        try (FileInputStream serviceAccount = new FileInputStream(CREDENTIAL_FIREBASE)) {

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            return FirestoreClient.getFirestore();

        } catch (IOException e) {
            throw new PersistenceResultException("Error to open the database");
        }

    }

    public static void closeDatabase() {
        try {
            database.close();
            database = null;
        } catch (Exception e) {
            throw new PersistenceResultException("Error to close the database");
        }
    }
}
