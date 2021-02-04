package ml.debius.systems.publisher;

import ml.debius.systems.event.GeneratorEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class GeneratorPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public GeneratorPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(final String response) {
        GeneratorEvent generatorEvent = new GeneratorEvent(this, response);
        applicationEventPublisher.publishEvent(generatorEvent);
    }
}
