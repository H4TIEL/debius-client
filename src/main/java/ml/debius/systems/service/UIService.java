package ml.debius.systems.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ml.debius.systems.instance.DataHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UIService {

    private final ApplicationContext applicationContext;

    public UIService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void changeScene(Resource fxml, Stage stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(fxml.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();
            stage.getScene().setRoot(parent);
            stage.show();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(Resource fxml, Stage stage, DataHolder dataHolder){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(fxml.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();
            stage.setUserData(dataHolder);
            stage.getScene().setRoot(parent);
            stage.show();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }
}
