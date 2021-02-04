package ml.debius.systems.controller;

import javafx.fxml.FXML;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import ml.debius.systems.instance.DataHolder;
import ml.debius.systems.model.Auth;
import ml.debius.systems.model.Project;
import ml.debius.systems.service.ProjectService;
import ml.debius.systems.service.UIService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;


@Controller
public class ProjectController {

    private final ApplicationContext applicationContext;

    private final ProjectService projectService;

    @Value("classpath:fxml/dataset.fxml")
    Resource fxml;

    @FXML
    Label title;

    @FXML
    TextField nameField;

    public ProjectController(ApplicationContext applicationContext,
                             ProjectService projectService) {
        this.applicationContext = applicationContext;
        this.projectService = projectService;
    }

    @FXML
    public void handleNext(){
        Project project = new Project();
        project.setName(nameField.getText());
        project.setKey(getKey());
        project.setCreatedAt(System.currentTimeMillis());
        projectService.saveProject(project);
        nextScene();
    }

    public void nextScene(){
        Stage stage = (Stage) title.getScene().getWindow();
        new UIService(applicationContext).changeScene(fxml, stage);
    }

    private Auth getKey(){
        DataHolder dataHolder = DataHolder.getInstance();
        return dataHolder.getData().getKeys();
    }

}