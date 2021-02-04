package ml.debius.systems.service;

import ml.debius.systems.model.Dataset;
import ml.debius.systems.model.Image;
import ml.debius.systems.repository.ImageRepository;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ImageService {

    final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    public Integer getGenderCount(String gender, Dataset dataset) {
        return imageRepository.countByGenderAndDataset(gender, dataset);
    }

    public Integer getAgeCount(String age, Dataset dataset) {
        return imageRepository.countByAgeAndDataset(age, dataset);
    }

    public void saveImages(Set<Image> images) {
        imageRepository.saveAll(images);
    }

    public Image getImageById(String image) {
         return imageRepository.findImageByIdContains(image).orElse(null);
    }

}