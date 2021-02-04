package ml.debius.systems.controller;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ml.debius.systems.instance.Data;
import ml.debius.systems.instance.DataHolder;
import ml.debius.systems.model.Auth;
import ml.debius.systems.service.AuthService;
import ml.debius.systems.service.UIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


@Controller
public class AuthController implements Initializable {

    private final AuthService authService;

    private final ApplicationContext applicationContext;

    private Auth auth;

    @Value("classpath:fxml/system.fxml")
    Resource fxml;

    @FXML
    Label title;

    @FXML
    TextField keyField;

    public AuthController(AuthService authService, ApplicationContext applicationContext) {
        this.authService = authService;
        this.applicationContext = applicationContext;
    }


    @FXML
    public void handleLogin() {
        if(keyField.isVisible()) {
            String appKey = keyField.getText();
            if(authService.verifyKey(appKey)){
                auth = authService.retrieveKey(appKey);
                nextScene();
            } else {
                invalidKey();
            }
        }else{
            keyField.setVisible(true);
            try {
                new ProcessBuilder("x-www-browser", "https://debius-ml.web.app/#/home").start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void invalidKey() {
        keyField.clear();
        keyField.setPromptText("Invalid app key");
    }

    private void nextScene() {
        Stage stage = (Stage) title.getScene().getWindow();
        DataHolder holder = DataHolder.getInstance();
        Data data = new Data();
        data.setKeys(auth);
        holder.setData(data);
        new UIService(applicationContext).changeScene(fxml, stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        keyField.setVisible(false);
    }
}