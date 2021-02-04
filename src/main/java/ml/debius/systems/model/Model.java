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

    private int maleDemographicError;

    private int femaleDemographicError;

    private int age0_2Error;

    private int age3_9Error;

    private int age10_19Error;

    private int age20_29Error;

    private int age30_39Error;

    private int age40_49Error;

    private int age50_59Error;

    private int age60_69Error;

    private int age70_Error;

    @OneToOne(mappedBy = "model")
    @JoinColumn(referencedColumnName = "id")
    private Dataset dataset;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMaleDemographicError() {
        return maleDemographicError;
    }

    public void setMaleDemographicError(int maleDemographicError) {
        this.maleDemographicError = maleDemographicError;
    }

    public int getFemaleDemographicError() {
        return femaleDemographicError;
    }

    public void setFemaleDemographicError(int femaleDemographicError) { this.femaleDemographicError = femaleDemographicError; }

    public int getAge0_2Error() {
        return age0_2Error;
    }

    public void setAge0_2Error(int age0_2Error) {
        this.age0_2Error = age0_2Error;
    }

    public int getAge3_9Error() {
        return age3_9Error;
    }

    public void setAge3_9Error(int age3_9Error) {
        this.age3_9Error = age3_9Error;
    }

    public int getAge10_19Error() {
        return age10_19Error;
    }

    public void setAge10_19Error(int age10_19Error) {
        this.age10_19Error = age10_19Error;
    }

    public int getAge20_29Error() {
        return age20_29Error;
    }

    public void setAge20_29Error(int age20_29Error) {
        this.age20_29Error = age20_29Error;
    }

    public int getAge30_39Error() {
        return age30_39Error;
    }

    public void setAge30_39Error(int age30_39Error) {
        this.age30_39Error = age30_39Error;
    }

    public int getAge40_49Error() {
        return age40_49Error;
    }

    public void setAge40_49Error(int age40_49Error) {
        this.age40_49Error = age40_49Error;
    }

    public int getAge50_59Error() {
        return age50_59Error;
    }

    public void setAge50_59Error(int age50_59Error) {
        this.age50_59Error = age50_59Error;
    }

    public int getAge60_69Error() {
        return age60_69Error;
    }

    public void setAge60_69Error(int age60_69Error) {
        this.age60_69Error = age60_69Error;
    }

    public int getAge70_Error() {
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