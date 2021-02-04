package ml.debius.systems.event;

import org.springframework.context.ApplicationEvent;

public class GeneratorEvent extends ApplicationEvent {

    private final String response;

    public GeneratorEvent(Object source, String response) {
        super(source);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
