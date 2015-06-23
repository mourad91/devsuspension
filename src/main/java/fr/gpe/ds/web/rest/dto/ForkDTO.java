package fr.gpe.ds.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Fork entity.
 */
public class ForkDTO implements Serializable {

    private Long id;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    private Integer hsc;

    private Integer lsc;

    private Integer rebound;

    private Integer bottomOut;

    @NotNull
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

        ForkDTO forkDTO = (ForkDTO) o;

        if ( ! Objects.equals(id, forkDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ForkDTO{" +
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
