package ml.debius.systems.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class SystemService {

    public String checkNvidiaGpu() {
        final String command = "nvidia-smi --list-gpus";
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            line = line.substring(0, line.indexOf('('));
            if (line.contains("GeForce")) {
                return line;
            } else {
                return "Nvidia GPU required";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "GPU Not Found";
    }

    public String checkDocker() {
        final String command = "docker version";
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line.contains("Docker Engine")) {
                return line;
            } else {
                return "Docker required. Install at https://docs.docker.com/get-docker";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Docker Not Found";
    }

}
