package ml.debius.systems.repository;

import ml.debius.systems.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRepository extends JpaRepository<Dataset, Integer> {

    Dataset findDatasetByName(String name);

}