package co.com.sofka.infraestructure;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;

public class RabbitDomainEventBus implements EventBus {

    @Override
    public void publish(DomainEvent event) {
        System.out.println("LLega al RabbitDomainEventBus ");
    }
}
