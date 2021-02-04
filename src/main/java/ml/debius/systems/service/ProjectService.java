package ml.debius.systems.service;

import ml.debius.systems.instance.DataHolder;
import ml.debius.systems.model.Project;
import ml.debius.systems.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void saveProject(Project project) {
        projectRepository.save(project);
        DataHolder.getInstance().getData().setProject(project);
    }

}