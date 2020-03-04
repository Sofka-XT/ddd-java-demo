package co.com.sofka.infraestructure;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static co.com.sofka.infraestructure.RabbitConfiguration.TOPIC_EXCHANGE_NAME;

public class RabbitDomainEventBus implements EventBus {
    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Autowired
    RabbitAdmin rabbitAdmin;

    private final RabbitTemplate rabbitTemplate;

    public RabbitDomainEventBus(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(final DomainEvent event) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        final String eventSerialization = serializeEvent(mapper, event);
        generateQueue(event);
        generateBinding(event);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, routingkey, eventSerialization);
    }

    private void generateBinding(DomainEvent event) {
        Binding binding = new Binding(event.type, Binding.DestinationType.QUEUE, TOPIC_EXCHANGE_NAME, event.type, null);
        rabbitAdmin.declareBinding(binding);
    }

    private void generateQueue(DomainEvent event) {
        Queue queue = new Queue(event.type, false, false, false);
        rabbitAdmin.declareQueue(queue);
    }

    private String serializeEvent(final ObjectMapper mapper, final DomainEvent domainEvent) {
        final String eventSerialization;
        try {
            eventSerialization = mapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new EventMapperException();
        }
        return eventSerialization;
    }
}
