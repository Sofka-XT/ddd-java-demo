package co.com.sofka.infrastructure;

public class RabbitDomainException extends RuntimeException {

    public RabbitDomainException(String message) {
        super(message);
    }
}
