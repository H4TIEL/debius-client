package ml.debius.systems.publisher;

import ml.debius.systems.event.ClassifierEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ClassifierPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ClassifierPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(final String response) {
        ClassifierEvent classifierEvent = new ClassifierEvent(this, response);
        applicationEventPublisher.publishEvent(classifierEvent);
    }
}
