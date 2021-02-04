package ml.debius.systems.service;

import ml.debius.systems.instance.DataHolder;
import ml.debius.systems.model.Dataset;
import ml.debius.systems.model.Project;
import ml.debius.systems.repository.DatasetRepository;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DatasetService {

    final DatasetRepository datasetRepository;

    final ProjectService projectService;

    final AuthService authService;

    public DatasetService(DatasetRepository datasetRepository,
                          ProjectService projectService,
                          AuthService authService) {
        this.datasetRepository = datasetRepository;
        this.projectService = projectService;
        this.authService = authService;
    }

    public void saveDataset(Dataset dataset) {
        datasetRepository.save(dataset);
        DataHolder.getInstance().getData().setDataset(dataset);
    }

    public void save(String datasetName, String path) {
        Project project = DataHolder.getInstance().getData().getProject();
        Dataset dataset = new Dataset();
        dataset.setName(datasetName);
        dataset.setDirectoryPath(path);
        setProjectDatasets(project, dataset);
        projectService.saveProject(project);
        dataset.setProject(project);
        saveDataset(dataset);
    }

    private void setProjectDatasets(Project project, Dataset dataset){
        if(project.getDatasets() == null){
            Set<Dataset> datasets = new HashSet<>();
            datasets.add(dataset);
            project.setDatasets(datasets);
        } else {
           project.getDatasets().add(dataset);
        }
    }


    public Dataset getDatasetByName(String name) {
        return datasetRepository.findDatasetByName(name);
    }

    public List<Dataset> getDatasets() {
        return datasetRepository.findAll();
    }

}