package co.com.sofka.infraestructure;

public class PersistenceResultException extends RuntimeException {

    public PersistenceResultException() {
    }

    public PersistenceResultException(String message) {
        super(message);
    }
}
