package ml.debius.systems.listener;

import javafx.scene.image.Image;
import ml.debius.systems.event.StageReadyEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

    @Component
public class StageInitializerListener implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:fxml/model.fxml")
    private Resource fxml;

    @Value("${spring.application.ui.title}")
    private String applicationTitle;

    private final ApplicationContext applicationContext;

    public StageInitializerListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(fxml.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setTitle(applicationTitle);
            stage.getIcons().add(new Image("assets/icons/logo.png"));
            stage.setScene(new Scene(parent, 1800, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
