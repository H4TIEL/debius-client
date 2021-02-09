package ml.debius.systems.service;

import ml.debius.systems.model.Image;
import ml.debius.systems.model.Model;
import ml.debius.systems.repository.ModelRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


@Service
public class ModelService {

    final ModelRepository modelRepository;

    final ImageService imageService;

    public ModelService(ModelRepository modelRepository,
                        ImageService imageService) {
        this.modelRepository = modelRepository;
        this.imageService = imageService;
    }

    private Model initializeModel() {
        Model model = new Model();
        model.setMaleDemographicError(0);
        model.setFemaleDemographicError(0);
        model.setAge0_2Error(0);
        model.setAge3_9Error(0);
        model.setAge10_19Error(0);
        model.setAge20_29Error(0);
        model.setAge30_39Error(0);
        model.setAge40_49Error(0);
        model.setAge50_59Error(0);
        model.setAge60_69Error(0);
        model.setAge70_Error(0);
        return model;
    }


    public Model analyseModel(String inputFile) {
        Model model = initializeModel();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(inputFile));
            String row;
            int line = 0;
            while ((row = csvReader.readLine()) != null) {
                // Skip first line
                if (line > 0) {
                    String[] columns = row.split(",");
                    // image exists
                    String imageId = getImageId(columns[0]);
                    Image image = imageService.getImageById(imageId);
                    if (image != null) {
                        model = calculateAgeError(model, image, columns[1]);
                        model = calculateGenderError(model, image, columns[2]);
                    }
                }
                line++;
            }

            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    private String getImageId(String image) {
        int index = image.indexOf('.');
        return image.substring(0, index);
    }

    public boolean checkCSVExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            String extension = fileName.substring(index + 1);
            return extension.equals("csv");
        }
        return false;
    }

    public boolean checkCSVFormat(String fileName) {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
            String row = csvReader.readLine();
            // Read first line
            String[] columns = row.split(",");
            if (columns[0].contains("image") && columns[1].contains("age") && columns[2].contains("gender")) {
                return true;
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Model calculateGenderError(Model model, Image image, String gender) {
        // male but marked as female
        if (image.getGender().equalsIgnoreCase("Male") && gender.equalsIgnoreCase("female"))
            model.setMaleDemographicError(model.getMaleDemographicError() - 1);
            // female but marked as male
        else if (image.getGender().equalsIgnoreCase("Female") && gender.equalsIgnoreCase("male"))
            model.setFemaleDemographicError(model.getFemaleDemographicError() - 1);
        else
            // no error
            return model;

        return model;
    }

    public Model calculateAgeError(Model model, Image image, String column) {
        try {
            int age = Integer.parseInt(column);
            if (age >= 0 && age <= 2) {
                if (!image.getAge().equals("0-2"))
                    model.setAge0_2Error(model.getAge0_2Error() - 1);
            } else if (age >= 3 && age <= 9) {
                if (!image.getAge().equals("3-9"))
                    model.setAge3_9Error(model.getAge3_9Error() - 1);
            } else if (age >= 10 && age <= 19) {
                if (!image.getAge().equals("10-19"))
                    model.setAge10_19Error(model.getAge10_19Error() - 1);
            } else if (age >= 20 && age <= 29) {
                if (!image.getAge().equals("20-29"))
                    model.setAge20_29Error(model.getAge20_29Error() - 1);
            } else if (age >= 30 && age <= 39) {
                if (!image.getAge().equals("30-39"))
                    model.setAge30_39Error(model.getAge30_39Error() - 1);
            } else if (age >= 40 && age <= 49) {
                if (!image.getAge().equals("40-49"))
                    model.setAge40_49Error(model.getAge40_49Error() - 1);
            } else if (age >= 50 && age <= 59) {
                if (!image.getAge().equals("50-59"))
                    model.setAge50_59Error(model.getAge50_59Error() - 1);
            } else if (age >= 60 && age <= 69) {
                if (!image.getAge().equals("60-69"))
                    model.setAge60_69Error(model.getAge60_69Error() - 1);
            } else {
                if (!image.getAge().equals("70+"))
                    model.setAge70_Error(model.getAge70_Error() - 1);
            }
        } catch (NumberFormatException e) {
            return model;
        }
        return model;
    }

    public void saveModel(Model model) {
        modelRepository.save(model);
    }
}
