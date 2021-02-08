package ml.debius.systems.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name = "model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    private Integer maleDemographicError;

    private Integer femaleDemographicError;

    private Integer age0_2Error;

    private Integer age3_9Error;

    private Integer age10_19Error;

    private Integer age20_29Error;

    private Integer age30_39Error;

    private Integer age40_49Error;

    private Integer age50_59Error;

    private Integer age60_69Error;

    private Integer age70_Error;

    @OneToOne(mappedBy = "model")
    @JoinColumn(referencedColumnName = "id")
    private Dataset dataset;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaleDemographicError() {
        return maleDemographicError;
    }

    public void setMaleDemographicError(Integer maleDemographicError) {
        this.maleDemographicError = maleDemographicError;
    }

    public Integer getFemaleDemographicError() {
        return femaleDemographicError;
    }

    public void setFemaleDemographicError(Integer femaleDemographicError) { this.femaleDemographicError = femaleDemographicError; }

    public Integer getAge0_2Error() {
        return age0_2Error;
    }

    public void setAge0_2Error(Integer age0_2Error) {
        this.age0_2Error = age0_2Error;
    }

    public Integer getAge3_9Error() {
        return age3_9Error;
    }

    public void setAge3_9Error(Integer age3_9Error) {
        this.age3_9Error = age3_9Error;
    }

    public Integer getAge10_19Error() {
        return age10_19Error;
    }

    public void setAge10_19Error(Integer age10_19Error) {
        this.age10_19Error = age10_19Error;
    }

    public Integer getAge20_29Error() {
        return age20_29Error;
    }

    public void setAge20_29Error(Integer age20_29Error) {
        this.age20_29Error = age20_29Error;
    }

    public Integer getAge30_39Error() {
        return age30_39Error;
    }

    public void setAge30_39Error(Integer age30_39Error) {
        this.age30_39Error = age30_39Error;
    }

    public Integer getAge40_49Error() {
        return age40_49Error;
    }

    public void setAge40_49Error(Integer age40_49Error) {
        this.age40_49Error = age40_49Error;
    }

    public Integer getAge50_59Error() {
        return age50_59Error;
    }

    public void setAge50_59Error(Integer age50_59Error) {
        this.age50_59Error = age50_59Error;
    }

    public Integer getAge60_69Error() {
        return age60_69Error;
    }

    public void setAge60_69Error(Integer age60_69Error) {
        this.age60_69Error = age60_69Error;
    }

    public Integer getAge70_Error() {
        return age70_Error;
    }

    public void setAge70_Error(int age70_Error) {
        this.age70_Error = age70_Error;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

}