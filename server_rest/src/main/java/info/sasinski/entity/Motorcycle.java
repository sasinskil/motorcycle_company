package info.sasinski.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import info.sasinski.entity.enumeration.Classification;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Motorcycle {
    @Id
    @SequenceGenerator(name = "Motorcycle_generator", sequenceName = "Motorcycle_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Motorcycle_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Classification classification;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    @Column(name = "Engine_type")
    @NotNull
    private String engineType;
    @NotNull
    private BigDecimal capacity;
    @NotNull
    private int power;
    @NotNull
    private BigDecimal weight;

    @OneToMany(mappedBy = "motorcycle", fetch = FetchType.EAGER)
    @Column(name = "Motorcycle_details")
    @JsonIgnore
    private List<MotorcycleDetails> motorcycleDetails;


    public Motorcycle() {
    }


    public Motorcycle(Classification classification, String brand, String model, String engineType, BigDecimal capacity,
                      int power, BigDecimal weight) {
        this.classification = classification;
        this.brand = brand;
        this.model = model;
        this.engineType = engineType;
        this.capacity = capacity;
        this.power = power;
        this.weight = weight;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public List<MotorcycleDetails> getMotorcycleDetails() {
        return motorcycleDetails;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((brand == null) ? 0 : brand.hashCode());
        result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
        result = prime * result + ((classification == null) ? 0 : classification.hashCode());
        result = prime * result + ((engineType == null) ? 0 : engineType.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((motorcycleDetails == null) ? 0 : motorcycleDetails.hashCode());
        result = prime * result + power;
        result = prime * result + ((weight == null) ? 0 : weight.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Motorcycle other = (Motorcycle) obj;
        if (brand == null) {
            if (other.brand != null)
                return false;
        } else if (!brand.equals(other.brand))
            return false;
        if (capacity == null) {
            if (other.capacity != null)
                return false;
        } else if (!capacity.equals(other.capacity))
            return false;
        if (classification != other.classification)
            return false;
        if (engineType == null) {
            if (other.engineType != null)
                return false;
        } else if (!engineType.equals(other.engineType))
            return false;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (motorcycleDetails == null) {
            if (other.motorcycleDetails != null)
                return false;
        } else if (!motorcycleDetails.equals(other.motorcycleDetails))
            return false;
        if (power != other.power)
            return false;
        if (weight == null) {
            if (other.weight != null)
                return false;
        } else if (!weight.equals(other.weight))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Motorcycle [id=" + id + ", classification=" + classification + ", brand=" + brand + ", model=" + model
                + ", engineType=" + engineType + ", capacity=" + capacity + ", power=" + power + ", weight=" + weight
                + "]";
    }

    public static Motorcycle getMotorcycle(Set<Motorcycle> motorcycleSet, int number) {

        List<Motorcycle> mList = new ArrayList<>();

        for (Motorcycle m : motorcycleSet) {
            mList.add(m);

        }
        return mList.get(number);
    }


}
