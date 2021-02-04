package ml.debius.systems.controller;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import ml.debius.systems.service.SystemService;
import ml.debius.systems.service.UIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class SystemController implements Initializable {

    private final ApplicationContext applicationContext;

    private final SystemService systemService;

    @Value("classpath:fxml/project.fxml")
    Resource fxml;

    public SystemController(ApplicationContext applicationContext, SystemService systemService) {
        this.applicationContext = applicationContext;
        this.systemService = systemService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scanProgress.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        resultsVBox.setVisible(false);
        nextButton.setVisible(false);
        nextButton.setDisable(true);
    }

    @FXML
    Label title;

    @FXML
    Button scanButton;

    @FXML
    VBox resultsVBox;

    @FXML
    Text gpuText;

    @FXML
    Text dockerText;

    @FXML
    ProgressBar scanProgress;

    @FXML
    Button nextButton;

    @FXML
    public void handleNext() {
        Stage stage = (Stage) title.getScene().getWindow();
        new UIService(applicationContext).changeScene(fxml, stage);
    }

    @FXML
    public void handleScan() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            scanProgress.setVisible(true);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gpuText.setText(systemService.checkNvidiaGpu());
            dockerText.setText(systemService.checkDocker());
            scanComplete();
        });
    }


    private void scanComplete(){
        scanProgress.setVisible(false);
        scanButton.setVisible(false);
        scanButton.setDisable(true);
        resultsVBox.setVisible(true);
        nextButton.setVisible(true);
        nextButton.setDisable(false);
    }


}