package ml.debius.systems.repository;

import ml.debius.systems.model.Dataset;
import ml.debius.systems.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    Integer countByGenderAndDataset(String gender, Dataset dataset);

    Integer countByAgeAndDataset(String age, Dataset dataset);

    Optional<Image> findImageByIdContains(String image);

}