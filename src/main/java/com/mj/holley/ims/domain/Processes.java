package com.mj.holley.ims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Processes.
 */
@Entity
@Table(name = "processes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Processes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_bop_id")
    private String subBopID;

    @Column(name = "process_id")
    private String processID;

    @Column(name = "general_sop_path")
    private String generalSopPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubBopID() {
        return subBopID;
    }

    public Processes subBopID(String subBopID) {
        this.subBopID = subBopID;
        return this;
    }

    public void setSubBopID(String subBopID) {
        this.subBopID = subBopID;
    }

    public String getProcessID() {
        return processID;
    }

    public Processes processID(String processID) {
        this.processID = processID;
        return this;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public String getGeneralSopPath() {
        return generalSopPath;
    }

    public Processes generalSopPath(String generalSopPath) {
        this.generalSopPath = generalSopPath;
        return this;
    }

    public void setGeneralSopPath(String generalSopPath) {
        this.generalSopPath = generalSopPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Processes processes = (Processes) o;
        if (processes.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, processes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Processes{" +
            "id=" + id +
            ", subBopID='" + subBopID + "'" +
            ", processID='" + processID + "'" +
            ", generalSopPath='" + generalSopPath + "'" +
            '}';
    }
}
