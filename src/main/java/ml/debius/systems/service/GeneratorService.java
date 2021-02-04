package ml.debius.systems.service;

import kong.unirest.Unirest;

import ml.debius.systems.instance.DataHolder;
import ml.debius.systems.publisher.GeneratorPublisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class GeneratorService {

    private static final String GENERATOR_URL = "http://localhost:5000";

    private static final String RESULTS_URL = GENERATOR_URL + "/results/00000-generate-images";

    private static final int SERVER_PORT = 5500;

    final ApplicationEventPublisher applicationEventPublisher;

    public GeneratorService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;

    }

    public String generateImages(String seeds, String truncation_psi) {
        generatorEventListenerServer();
        String response = Unirest.post(GENERATOR_URL)
                .field("seeds", seeds)
                .field("truncation_psi", truncation_psi)
                .asString()
                .getBody();
        return String.format("%s", response);
    }

    public void fetchGeneratedImages(int start, int count){
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for(int seed = start; seed <= start + count; seed++) {
           executor.submit(fetchImage(seed));
        }
    }

    private Runnable fetchImage(int seed){
        return  () -> {
            String image = String.format("seed%d.png",seed);
            String imageUrl = String.format("%s/%s", RESULTS_URL, image);
            String path = DataHolder.getInstance().getData().getDataset().getDirectoryPath();
            String outputPath = String.format("%s/%s", path, image);
            Unirest.get(imageUrl).asFile(outputPath).getBody();
        };
    }

    private void generatorEventListenerServer() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                ServerSocket listener = new ServerSocket(SERVER_PORT);
                Socket socket = listener.accept();
                publishGeneratorEvent("Generating images Complete");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void publishGeneratorEvent(final String response) {
        GeneratorPublisher generatorPublisher = new GeneratorPublisher(applicationEventPublisher);
        generatorPublisher.publishEvent(response);
    }

}
