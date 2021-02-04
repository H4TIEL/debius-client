package ml.debius.systems.service;

import javafx.scene.chart.XYChart;
import kong.unirest.Unirest;
import ml.debius.systems.instance.DataHolder;
import ml.debius.systems.model.Dataset;
import ml.debius.systems.model.Image;
import ml.debius.systems.publisher.ClassifierPublisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AnalyseService {

    final DatasetService datasetService;

    final ImageService imageService;

    final ApplicationEventPublisher applicationEventPublisher;

    private static final String INPUT_FILE = "mount/classifier-input.csv";

    private static final String MOUNT_FILE = "/mount/classifier-input.csv";

    private static final String CLASSIFIER_URL = "http://localhost:5000";

    private static final String RESULTS_URL = CLASSIFIER_URL + "/results/output.csv";

    private static final String OUTPUT_FILE = "mount/classifier-output.csv";

    private static final int SERVER_PORT = 6000;


    public AnalyseService(DatasetService datasetService,
                          ImageService imageService,
                          ApplicationEventPublisher applicationEventPublisher) {
        this.datasetService = datasetService;
        this.imageService = imageService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public List<Dataset> getDatasets() {
        return datasetService.getDatasets();
    }

    private XYChart.Data<String, Number> getGender(String gender, Dataset dataset) {
        Integer count = imageService.getGenderCount(gender, dataset);
        return new XYChart.Data<>(gender, count);
    }

    private XYChart.Data<String, Number> getAge(String age, Dataset dataset) {
        Integer count = imageService.getAgeCount(age, dataset);
        return new XYChart.Data<>(age, count);
    }


    public XYChart.Series<String, Number> getGenderDataSeries(Dataset dataset) {
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(getGender("Male", dataset));
        dataSeries.getData().add(getGender("Female", dataset));
        return dataSeries;
    }

    public XYChart.Series<String, Number> getAgeDataSeries(Dataset dataset) {
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        String[] ages = {"0-2", "3-9", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70+"};
        for (String age : ages) {
            dataSeries.getData().add(getAge(age, dataset));
        }
        return dataSeries;
    }


    private void classifierEventListenerServer() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                ServerSocket listener = new ServerSocket(SERVER_PORT);
                Socket socket = listener.accept();
                publishClassifierEvent("Classifier Complete");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fetchResultsCSV();
                parseCSV(DataHolder.getInstance().getData().getDataset());
            }
        });
    }

    public void publishClassifierEvent(final String response) {
        ClassifierPublisher classifierPublisher = new ClassifierPublisher(applicationEventPublisher);
        classifierPublisher.publishEvent(response);
    }

    private boolean checkExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            String extension = fileName.substring(index + 1);
            return extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg");
        }
        return false;
    }

    private void writeFileNamesCSV(File[] files) {
        try {
            File imagesFile = new File(INPUT_FILE);
            // Clear file contents
            new FileWriter(imagesFile, false).close();
            FileWriter csvWriter = new FileWriter(imagesFile);
            csvWriter.append("img_path");
            csvWriter.append("\n");
            for (File file : files) {
                // Only images
                if (checkExtension(file.toString())) {
                    String fileName = String.format("/mount/images/%s", file.getName());
                    csvWriter.append(String.join(",", fileName));
                    csvWriter.append("\n");
                }
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fetchResultsCSV() {
        if (clearResultsCSV())
            Unirest.get(RESULTS_URL).asFile(OUTPUT_FILE).getBody();
    }

    public boolean clearResultsCSV() {
        File results = new File(OUTPUT_FILE);
        // Create temporary file if does not exist
        try {
            FileWriter fileWriter = new FileWriter(results);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results.delete();
    }


    private void parseCSV(Dataset dataset) {

        Set<Image> images = new HashSet<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(OUTPUT_FILE));
            String row;
            int iteration = 0;
            while ((row = csvReader.readLine()) != null) {
                // Skip first line
                if (iteration > 0) {
                    String[] columns = row.split(",");
                    Image image = new Image();
                    image.setId(columns[0]);
                    image.setRace(columns[1]);
                    image.setGender(columns[3]);
                    image.setAge(columns[4]);
                    image.setDataset(dataset);
                    images.add(image);
                }
                iteration++;
            }
            dataset.setGeneratedCount(iteration - 1);
            datasetService.saveDataset(dataset);
            saveImages(images);
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            publishClassifierEvent("Analysis complete");
        }
    }

    private void saveImages(Set<Image> images) {
        imageService.saveImages(images);
    }


    public String writeCSV(Dataset dataset) {
        File path = new File(dataset.getDirectoryPath());
        File[] files = path.listFiles();
        writeFileNamesCSV(Objects.requireNonNull(files));
        return "Write Complete, Mount";
    }

    public String classify() {
        classifierEventListenerServer();
        return Unirest.post(CLASSIFIER_URL)
                .field("csv", MOUNT_FILE)
                .asString()
                .getBody();
    }

}
