package ml.debius.systems;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ml.debius.systems.event.StageReadyEvent;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFX extends Application {

    ConfigurableApplicationContext applicationContext;

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Debius.class).run();
    }

    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }

}
