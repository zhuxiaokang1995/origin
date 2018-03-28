package com.mj.holley.ims.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mj.holley.ims.domain.util.ZonedDateTimeDeserializer;
import com.mj.holley.ims.domain.util.ZonedDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ProcessControl.
 */
@Entity
@Table(name = "process_control")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessControl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "hut_id")
    private String hutID;

    @Column(name = "order_id")
    private String orderID;

    @Column(name = "station_id")
    private String stationID;

    @Column(name = "result")
    private String result;

    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @Column(name = "mount_guard_time")
    private ZonedDateTime mountGuardTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ProcessControl serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getHutID() {
        return hutID;
    }

    public ProcessControl hutID(String hutID) {
        this.hutID = hutID;
        return this;
    }

    public void setHutID(String hutID) {
        this.hutID = hutID;
    }

    public String getOrderID() {
        return orderID;
    }

    public ProcessControl orderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getStationID() {
        return stationID;
    }

    public ProcessControl stationID(String stationID) {
        this.stationID = stationID;
        return this;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getResult() {
        return result;
    }

    public ProcessControl result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ZonedDateTime getMountGuardTime() {
        return mountGuardTime;
    }

    public ProcessControl mountGuardTime(ZonedDateTime mountGuardTime) {
        this.mountGuardTime = mountGuardTime;
        return this;
    }

    public void setMountGuardTime(ZonedDateTime mountGuardTime) {
        this.mountGuardTime = mountGuardTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProcessControl processControl = (ProcessControl) o;
        if (processControl.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, processControl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProcessControl{" +
            "id=" + id +
            ", serialNumber='" + serialNumber + "'" +
            ", hutID='" + hutID + "'" +
            ", orderID='" + orderID + "'" +
            ", stationID='" + stationID + "'" +
            ", result='" + result + "'" +
            ", mountGuardTime='" + mountGuardTime + "'" +
            '}';
    }
}
