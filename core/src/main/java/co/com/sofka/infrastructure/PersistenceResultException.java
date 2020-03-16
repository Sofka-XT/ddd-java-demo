package co.com.sofka.infrastructure;

public class PersistenceResultException extends RuntimeException {

    public PersistenceResultException() {
    }

    public PersistenceResultException(final String message) {
        super(message);
    }
}
