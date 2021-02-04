package ml.debius.systems.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import ml.debius.systems.event.GeneratorEvent;
import ml.debius.systems.instance.DataHolder;
import ml.debius.systems.service.DatasetService;
import ml.debius.systems.service.GeneratorService;

import ml.debius.systems.service.UIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class GeneratorController implements Initializable , ApplicationListener<GeneratorEvent> {

    private static final int COUNT = 3;
    final GeneratorService generatorService;

    final DatasetService datasetService;

    final ApplicationContext applicationContext;

    @Value("classpath:fxml/analyse.fxml")
    Resource fxml;

    @FXML
    Label title;

    @FXML
    ProgressBar generateProgress;

    @FXML
    Text resultsText;

    @FXML
    Button generateButton;

    @FXML
    Button nextButton;

    public GeneratorController(GeneratorService generatorService,
                               DatasetService datasetService,
                               ApplicationContext applicationContext) {
        this.generatorService = generatorService;
        this.datasetService = datasetService;
        this.applicationContext = applicationContext;
    }

    @FXML
    public void handleNext() {
        Stage stage = (Stage) title.getScene().getWindow();
        new UIService(applicationContext).changeScene(fxml, stage);
    }

    @FXML
    public void handleGenerate() {
        int start = (int)(Math.random() * (7000 - 5000 + 1) + 5000);
        DataHolder.getInstance().getData().getDataset().setGeneratedCount(start);
        String seeds = String.format("%s-%s", start, start + COUNT);
        String response = generatorService.generateImages(seeds, "0.7");
        resultsText.setVisible(true);
        resultsText.setText(response);
        generateProgress.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultsText.setVisible(false);
        generateProgress.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        generateProgress.setVisible(false);
    }

    @Override
    public void onApplicationEvent(GeneratorEvent event) {
        Integer count = DataHolder.getInstance().getData().getDataset().getGeneratedCount();
        generatorService.fetchGeneratedImages(count, COUNT);
        resultsText.setText(event.getResponse());
        generateProgress.setVisible(false);
        generateButton.setVisible(false);
        nextButton.setVisible(true);
    }
}
