package ml.debius.systems.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

@Controller
public class DependenciesController {


    @FXML
    Label title;

    @FXML
    Text statusText;


    @FXML
    public void handleNext(){
        nextScene();
    }


    public void nextScene(){
    }
}
