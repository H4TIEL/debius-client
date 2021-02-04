package ml.debius.systems.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Component
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    private String name;

    private Long createdAt;

    @ManyToOne
    @JoinColumn(name = "auth")
    private Auth auth;

    @OneToMany(mappedBy = "project")
    private Set<Dataset> datasets;

    public Project() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Auth getKey() {
        return auth;
    }

    public void setKey(Auth key) {
        this.auth = key;
    }

    public void setKeys(Auth auth) {
        this.auth = auth;
    }

    public Set<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(Set<Dataset> datasets) {
        this.datasets = datasets;
    }


}