package ml.debius.systems.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import ml.debius.systems.service.DatasetService;
import ml.debius.systems.service.UIService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.File;

import java.net.URL;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Controller
public class DatasetController implements Initializable {

    private final ApplicationContext applicationContext;

    private final DatasetService datasetService;

    @Value("classpath:fxml/generator.fxml")
    Resource fxml;

    public DatasetController(ApplicationContext applicationContext,
                             DatasetService datasetService) {
        this.applicationContext = applicationContext;
        this.datasetService = datasetService;
    }

    @FXML
    Label title;

    @FXML
    TextField datasetField;

    @FXML
    Text resultsText;

    @FXML
    Button directoryButton;

    @FXML
    Button nextButton;

    @FXML
    VBox datasetVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryButton.setVisible(false);
        resultsText.setVisible(false);
        nextButton.setVisible(false);
    }

    @FXML
    public void handleDatasetName() {
        datasetField.setEditable(false);
        directoryButton.setVisible(true);
    }

    @FXML
    public void handleDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Generated Images Store");
        try {
            File selectedDirectory = directoryChooser.showDialog(directoryButton.getScene().getWindow());
            String path = selectedDirectory.getAbsolutePath();
            resultsText.setVisible(true);
            resultsText.setText(path);
            nextButton.setVisible(true);
        } catch (NullPointerException e) {
            resultsText.setVisible(true);
            resultsText.setText("No directory was selected");
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> datasetService.save(datasetField.getText(), resultsText.getText()));
    }

    @FXML
    public void handleNext() {
        Stage stage = (Stage) title.getScene().getWindow();
        new UIService(applicationContext).changeScene(fxml, stage);
    }

}