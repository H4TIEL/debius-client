package ml.debius.systems.instance;

import ml.debius.systems.model.Dataset;
import ml.debius.systems.model.Auth;
import ml.debius.systems.model.Model;
import ml.debius.systems.model.Project;

public class Data {

    private Auth auth;

    private Project project;

    private Dataset dataset;

    private Model model;

    public Auth getKeys() {
        return auth;
    }

    public void setKeys(Auth auth) {
        this.auth = auth;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}