package co.com.sofka.infrastructure;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import com.google.gson.Gson;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import static co.com.sofka.generic.helpers.Constants.TOPIC_EXCHANGE_NAME;

public class RabbitDomainEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitAdmin rabbitAdmin;

    public RabbitDomainEventBus(final RabbitTemplate rabbitTemplate, final RabbitAdmin rabbitAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitAdmin = rabbitAdmin;
    }

    @Override
    public void publish(final DomainEvent event) {
        Gson gson = new Gson();
        final String eventSerialization = gson.toJson(event);
        generateQueue(event);
        generateBinding(event);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, event.type, eventSerialization);
    }

    private void generateBinding(final DomainEvent event) {
        Binding binding = new Binding(event.type, Binding.DestinationType.QUEUE, TOPIC_EXCHANGE_NAME, event.type, null);
        rabbitAdmin.declareBinding(binding);
    }

    private void generateQueue(final DomainEvent event) {
        Queue queue = new Queue(event.type, false, false, false);
        rabbitAdmin.declareQueue(queue);
    }

}
