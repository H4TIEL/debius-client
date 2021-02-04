package ml.debius.systems.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.chart.BarChart;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import ml.debius.systems.instance.DataHolder;
import ml.debius.systems.model.Dataset;

import ml.debius.systems.model.Model;
import ml.debius.systems.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;


import java.io.File;

import java.net.URL;

import java.util.ResourceBundle;

@Controller
public class ModelController implements Initializable {

    private final ApplicationContext applicationContext;

    final ModelService modelService;

    final DatasetService datasetService;

    final AnalyseService analyseService;

    final ImageService imageService;

    @Value("classpath:fxml/dataset.fxml")
    Resource fxml;

    @FXML
    Label title;

    @FXML
    Button importButton;

    @FXML
    ProgressBar analyseProgress;

    @FXML
    Text resultsText;

    @FXML
    Button analyseButton;

    @FXML
    BarChart<String, Number> genderBarChart;

    @FXML
    BarChart<String, Number> ageBarChart;

    @FXML
    Button nextButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        analyseProgress.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        analyseProgress.setVisible(false);
        analyseButton.setVisible(false);
        resultsText.setVisible(false);
        nextButton.setVisible(false);
    }

    public ModelController(ApplicationContext applicationContext,
                           ModelService modelService,
                           AnalyseService analyseService,
                           ImageService imageService,
                           DatasetService datasetService) {
        this.applicationContext = applicationContext;
        this.modelService = modelService;
        this.analyseService = analyseService;
        this.imageService = imageService;
        this.datasetService = datasetService;
    }

    @FXML
    public void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open input file");
        try {
            File file = fileChooser.showOpenDialog(importButton.getScene().getWindow());
            if (modelService.checkCSVExtension(file.toString())) {
                if (modelService.checkCSVFormat(file.getAbsolutePath())) {
                    analyseButton.setVisible(true);
                    resultsText.setVisible(true);
                    resultsText.setText(file.toString());
                }
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            resultsText.setVisible(true);
            resultsText.setText("Invalid file was selected");
        }
    }

    @FXML
    public void handleAnalyse() {
        Dataset dataset = datasetService.getDatasetByName("Test");
        //Dataset dataset = datasetService.getDatasetByName(DataHolder.getInstance().getData().getDataset().getName());
        Model model = modelService.analyseModel(resultsText.getText());
        model.setDataset(dataset);
        //DataHolder.getInstance().getData().setModel(model);
        modelService.saveModel(model);
        clearCharts();
        updateCharts(model);
    }

    @FXML
    public void handleNext() {
        Stage stage = (Stage) title.getScene().getWindow();
        new UIService(applicationContext).changeScene(fxml, stage);
    }

    private void updateCharts(Model model) {
        XYChart.Series<String, Number> genderSeries = analyseService.getGenderDataSeries(model.getDataset());
        genderSeries.setName("True Values");
        XYChart.Series<String, Number> genderErrorSeries = new XYChart.Series<>();
        genderErrorSeries.setName("Error Rate");
        genderErrorSeries.getData().add(new XYChart.Data<>("Male", model.getMaleDemographicError()));
        genderErrorSeries.getData().add(new XYChart.Data<>("Female", model.getFemaleDemographicError()));
        genderBarChart.getData().add(genderSeries);
        genderBarChart.getData().add(genderErrorSeries);

        XYChart.Series<String, Number> ageSeries = analyseService.getAgeDataSeries(model.getDataset());
        ageSeries.setName("True Values");
        XYChart.Series<String, Number> ageErrorSeries = new XYChart.Series<>();
        ageErrorSeries.setName("Error Rate");
        ageErrorSeries.getData().add(new XYChart.Data<>("0-2", model.getAge0_2Error()));
        ageErrorSeries.getData().add(new XYChart.Data<>("3-9", model.getAge3_9Error()));
        ageErrorSeries.getData().add(new XYChart.Data<>("10-19", model.getAge10_19Error()));
        ageErrorSeries.getData().add(new XYChart.Data<>("20-29", model.getAge20_29Error()));
        ageErrorSeries.getData().add(new XYChart.Data<>("30-39", model.getAge30_39Error()));
        ageErrorSeries.getData().add(new XYChart.Data<>("40-49", model.getAge40_49Error()));
        ageErrorSeries.getData().add(new XYChart.Data<>("50-59", model.getAge50_59Error()));
        ageErrorSeries.getData().add(new XYChart.Data<>("60-69", model.getAge60_69Error()));
        ageErrorSeries.getData().add(new XYChart.Data<>("70+", model.getAge70_Error()));
        ageBarChart.getData().add(ageSeries);
        ageBarChart.getData().add(ageErrorSeries);

        nextButton.setVisible(true);
    }

    private void clearCharts() {
        genderBarChart.getData().clear();
        ageBarChart.getData().clear();
    }

}
