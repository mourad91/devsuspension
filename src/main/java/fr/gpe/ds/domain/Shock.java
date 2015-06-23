package fr.gpe.ds.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Shock.
 */
@Entity
@Table(name = "SHOCK")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="shock")
public class Shock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "brand", nullable = false)
    private String brand;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "hsc")
    private Integer hsc;

    @Column(name = "lsc")
    private Integer lsc;

    @Column(name = "rebound")
    private Integer rebound;

    @Column(name = "bottom_out")
    private Integer bottomOut;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "type", nullable = false)
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getHsc() {
        return hsc;
    }

    public void setHsc(Integer hsc) {
        this.hsc = hsc;
    }

    public Integer getLsc() {
        return lsc;
    }

    public void setLsc(Integer lsc) {
        this.lsc = lsc;
    }

    public Integer getRebound() {
        return rebound;
    }

    public void setRebound(Integer rebound) {
        this.rebound = rebound;
    }

    public Integer getBottomOut() {
        return bottomOut;
    }

    public void setBottomOut(Integer bottomOut) {
        this.bottomOut = bottomOut;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Shock shock = (Shock) o;

        if ( ! Objects.equals(id, shock.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Shock{" +
                "id=" + id +
                ", brand='" + brand + "'" +
                ", model='" + model + "'" +
                ", hsc='" + hsc + "'" +
                ", lsc='" + lsc + "'" +
                ", rebound='" + rebound + "'" +
                ", bottomOut='" + bottomOut + "'" +
                ", type='" + type + "'" +
                '}';
    }
}
