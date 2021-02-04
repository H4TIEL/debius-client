package ml.debius.systems.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.util.StringConverter;

import ml.debius.systems.event.ClassifierEvent;
import ml.debius.systems.instance.DataHolder;
import ml.debius.systems.model.Dataset;

import ml.debius.systems.service.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.net.URL;

import java.util.ResourceBundle;

@Controller
public class AnalyseController implements Initializable, ApplicationListener<ClassifierEvent> {

    private final ApplicationContext applicationContext;

    @Value("classpath:fxml/model.fxml")
    Resource fxml;

    @FXML
    Label title;

    @FXML
    ProgressBar analyseProgress;

    @FXML
    Button analyseButton;

    @FXML
    Text resultsText;

    @FXML
    Button plotButton;

    @FXML
    BarChart<String, Number> genderBarChart;

    @FXML
    BarChart<String, Number> ageBarChart;

    @FXML
    ChoiceBox<Dataset> datasetChoiceBox;

    @FXML
    Button nextButton;

    final AnalyseService analyseService;

    final DatasetService datasetService;

    ObservableList<Dataset> datasets = FXCollections.observableArrayList();

    public AnalyseController(ApplicationContext applicationContext, AnalyseService analyseService,
                             DatasetService datasetService) {
        this.applicationContext = applicationContext;
        this.datasetService = datasetService;
        this.analyseService = analyseService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        analyseProgress.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        analyseProgress.setVisible(false);
        resultsText.setVisible(false);
        plotButton.setVisible(false);
        nextButton.setVisible(false);
        datasets.addAll(analyseService.getDatasets());
        datasetChoiceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Dataset dataset) {
                return dataset.getName();
            }

            @Override
            public Dataset fromString(String name) {
                return datasetService.getDatasetByName(name);
            }
        });

        datasetChoiceBox.setItems(datasets);
        datasetChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    clearCharts();
                    checkNext();
                    DataHolder.getInstance().getData().setDataset(newValue);
                    String response = analyseService.writeCSV(newValue);
                    resultsText.setVisible(true);
                    resultsText.setText(response);
                });
    }

    @Override
    public void onApplicationEvent(ClassifierEvent event) {
        analyseProgress.setVisible(false);
        analyseButton.setVisible(false);
        resultsText.setText(event.getResponse());
        plotButton.setVisible(true);
    }

    @FXML
    public void handleAnalyse() {
        analyseProgress.setVisible(true);
        datasetChoiceBox.setDisable(true);
        resultsText.setVisible(true);
        String response = analyseService.classify();
        resultsText.setText(response);
    }

    @FXML
    public void handlePlot() {
        updateCharts(DataHolder.getInstance().getData().getDataset());
        nextButton.setVisible(true);
    }

    @FXML
    public void handleNext() {
        Stage stage = (Stage) title.getScene().getWindow();
        new UIService(applicationContext).changeScene(fxml, stage);
    }

    private void clearCharts() {
        genderBarChart.getData().clear();
        ageBarChart.getData().clear();
    }

    private void updateCharts(Dataset dataset) {
        genderBarChart.getData().add(analyseService.getGenderDataSeries(dataset));
        ageBarChart.getData().add(analyseService.getAgeDataSeries(dataset));
    }

    private void checkNext() {
        if (!nextButton.isVisible())
            nextButton.setVisible(true);
    }

}