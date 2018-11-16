package com.mj.holley.ims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ArriveStationInfo.
 */
@Entity
@Table(name = "arrive_station_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArriveStationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "zp_02")
    private Integer zp02;

    @Column(name = "zp_03")
    private Integer zp03;

    @Column(name = "zp_04")
    private Integer zp04;

    @Column(name = "zp_05")
    private Integer zp05;

    @Column(name = "zp_06")
    private Integer zp06;

    @Column(name = "zp_07")
    private Integer zp07;

    @Column(name = "zp_08")
    private Integer zp08;

    @Column(name = "zp_09")
    private Integer zp09;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ArriveStationInfo serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getZp02() {
        return zp02;
    }

    public ArriveStationInfo zp02(Integer zp02) {
        this.zp02 = zp02;
        return this;
    }

    public void setZp02(Integer zp02) {
        this.zp02 = zp02;
    }

    public Integer getZp03() {
        return zp03;
    }

    public ArriveStationInfo zp03(Integer zp03) {
        this.zp03 = zp03;
        return this;
    }

    public void setZp03(Integer zp03) {
        this.zp03 = zp03;
    }

    public Integer getZp04() {
        return zp04;
    }

    public ArriveStationInfo zp04(Integer zp04) {
        this.zp04 = zp04;
        return this;
    }

    public void setZp04(Integer zp04) {
        this.zp04 = zp04;
    }

    public Integer getZp05() {
        return zp05;
    }

    public ArriveStationInfo zp05(Integer zp05) {
        this.zp05 = zp05;
        return this;
    }

    public void setZp05(Integer zp05) {
        this.zp05 = zp05;
    }

    public Integer getZp06() {
        return zp06;
    }

    public ArriveStationInfo zp06(Integer zp06) {
        this.zp06 = zp06;
        return this;
    }

    public void setZp06(Integer zp06) {
        this.zp06 = zp06;
    }

    public Integer getZp07() {
        return zp07;
    }

    public ArriveStationInfo zp07(Integer zp07) {
        this.zp07 = zp07;
        return this;
    }

    public void setZp07(Integer zp07) {
        this.zp07 = zp07;
    }

    public Integer getZp08() {
        return zp08;
    }

    public ArriveStationInfo zp08(Integer zp08) {
        this.zp08 = zp08;
        return this;
    }

    public void setZp08(Integer zp08) {
        this.zp08 = zp08;
    }

    public Integer getZp09() {
        return zp09;
    }

    public ArriveStationInfo zp09(Integer zp09) {
        this.zp09 = zp09;
        return this;
    }

    public void setZp09(Integer zp09) {
        this.zp09 = zp09;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArriveStationInfo arriveStationInfo = (ArriveStationInfo) o;
        if (arriveStationInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), arriveStationInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArriveStationInfo{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", zp02=" + getZp02() +
            ", zp03=" + getZp03() +
            ", zp04=" + getZp04() +
            ", zp05=" + getZp05() +
            ", zp06=" + getZp06() +
            ", zp07=" + getZp07() +
            ", zp08=" + getZp08() +
            ", zp09=" + getZp09() +
            "}";
    }
}
