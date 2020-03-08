package co.com.sofka.infraestructure;

public class PersistenceResultException extends RuntimeException {

    public PersistenceResultException() {
    }

    public PersistenceResultException(final String message) {
        super(message);
    }
}
