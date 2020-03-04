package co.com.sofka.infraestructure;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static co.com.sofka.generic.helpers.Constants.TOPIC_EXCHANGE_NAME;
import static co.com.sofka.generic.helpers.Serialize.serializeEvent;


public class RabbitDomainEventBus implements EventBus {
    @Value("${rabbitmq.routingkey}")
    private String routingkey;


    private final RabbitAdmin rabbitAdmin;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
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
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, routingkey, eventSerialization);
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
