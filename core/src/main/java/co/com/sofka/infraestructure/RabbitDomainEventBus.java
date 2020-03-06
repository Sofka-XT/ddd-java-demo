package co.com.sofka.infraestructure;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static co.com.sofka.generic.helpers.Constants.TOPIC_EXCHANGE_NAME;
import static co.com.sofka.generic.helpers.SerializeHelper.serializeEvent;


public class RabbitDomainEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitAdmin rabbitAdmin;

    public RabbitDomainEventBus(final RabbitTemplate rabbitTemplate, final RabbitAdmin rabbitAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitAdmin = rabbitAdmin;
    }

    @Override
    public void publish(final DomainEvent event) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        final String eventSerialization = serializeEvent(mapper, event);
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
