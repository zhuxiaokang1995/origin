package com.mj.holley.ims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ScanningRegistration.
 */
@Entity
@Table(name = "scanning_registration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ScanningRegistration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pk")
    private Integer pk;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "station_id")
    private String stationID;

    @Column(name = "scan_type")
    private String scanType;

    @Column(name = "defect_code")
    private String defectCode;

    @Column(name = "result")
    private String result;

    @Column(name = "sub_sn")
    private String subSn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPk() {
        return pk;
    }

    public ScanningRegistration pk(Integer pk) {
        this.pk = pk;
        return this;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ScanningRegistration serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStationID() {
        return stationID;
    }

    public ScanningRegistration stationID(String stationID) {
        this.stationID = stationID;
        return this;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getScanType() {
        return scanType;
    }

    public ScanningRegistration scanType(String scanType) {
        this.scanType = scanType;
        return this;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getDefectCode() {
        return defectCode;
    }

    public ScanningRegistration defectCode(String defectCode) {
        this.defectCode = defectCode;
        return this;
    }

    public void setDefectCode(String defectCode) {
        this.defectCode = defectCode;
    }

    public String getResult() {
        return result;
    }

    public ScanningRegistration result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSubSn() {
        return subSn;
    }

    public ScanningRegistration subSn(String subSn) {
        this.subSn = subSn;
        return this;
    }

    public void setSubSn(String subSn) {
        this.subSn = subSn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScanningRegistration scanningRegistration = (ScanningRegistration) o;
        if (scanningRegistration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, scanningRegistration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ScanningRegistration{" +
            "id=" + id +
            ", pk='" + pk + "'" +
            ", serialNumber='" + serialNumber + "'" +
            ", stationID='" + stationID + "'" +
            ", scanType='" + scanType + "'" +
            ", defectCode='" + defectCode + "'" +
            ", result='" + result + "'" +
            ", subSn='" + subSn + "'" +
            '}';
    }
}
