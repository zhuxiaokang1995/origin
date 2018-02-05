package com.mj.holley.ims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Steps.
 */
@Entity
@Table(name = "steps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Steps implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "step_id")
    private String stepID;

    @Column(name = "step_name")
    private String stepName;

    @Column(name = "sequence")
    private String sequence;

    @Column(name = "step_attr_id")
    private String stepAttrID;

    @Column(name = "station_id")
    private String stationID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStepID() {
        return stepID;
    }

    public Steps stepID(String stepID) {
        this.stepID = stepID;
        return this;
    }

    public void setStepID(String stepID) {
        this.stepID = stepID;
    }

    public String getStepName() {
        return stepName;
    }

    public Steps stepName(String stepName) {
        this.stepName = stepName;
        return this;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getSequence() {
        return sequence;
    }

    public Steps sequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getStepAttrID() {
        return stepAttrID;
    }

    public Steps stepAttrID(String stepAttrID) {
        this.stepAttrID = stepAttrID;
        return this;
    }

    public void setStepAttrID(String stepAttrID) {
        this.stepAttrID = stepAttrID;
    }

    public String getStationID() {
        return stationID;
    }

    public Steps stationID(String stationID) {
        this.stationID = stationID;
        return this;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Steps steps = (Steps) o;
        if (steps.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, steps.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Steps{" +
            "id=" + id +
            ", stepID='" + stepID + "'" +
            ", stepName='" + stepName + "'" +
            ", sequence='" + sequence + "'" +
            ", stepAttrID='" + stepAttrID + "'" +
            ", stationID='" + stationID + "'" +
            '}';
    }
}
