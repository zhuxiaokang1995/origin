package com.mj.holley.ims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AbnormalInformation.
 */
@Entity
@Table(name = "abnormal_information")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AbnormalInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_station_id")
    private String lineStationId;

    @Column(name = "abnormal_cause")
    private String abnormalCause;

    @Column(name = "abnormal_time")
    private ZonedDateTime abnormalTime;

    @Column(name = "remark")
    private String remark;

    public AbnormalInformation() {
    }

    public AbnormalInformation(String stationId, String s, ZonedDateTime now, String s1) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLineStationId() {
        return lineStationId;
    }

    public AbnormalInformation lineStationId(String lineStationId) {
        this.lineStationId = lineStationId;
        return this;
    }

    public void setLineStationId(String lineStationId) {
        this.lineStationId = lineStationId;
    }

    public String getAbnormalCause() {
        return abnormalCause;
    }

    public AbnormalInformation abnormalCause(String abnormalCause) {
        this.abnormalCause = abnormalCause;
        return this;
    }

    public void setAbnormalCause(String abnormalCause) {
        this.abnormalCause = abnormalCause;
    }

    public ZonedDateTime getAbnormalTime() {
        return abnormalTime;
    }

    public AbnormalInformation abnormalTime(ZonedDateTime abnormalTime) {
        this.abnormalTime = abnormalTime;
        return this;
    }

    public void setAbnormalTime(ZonedDateTime abnormalTime) {
        this.abnormalTime = abnormalTime;
    }

    public String getRemark() {
        return remark;
    }

    public AbnormalInformation remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbnormalInformation abnormalInformation = (AbnormalInformation) o;
        if (abnormalInformation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, abnormalInformation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AbnormalInformation{" +
            "id=" + id +
            ", lineStationId='" + lineStationId + "'" +
            ", abnormalCause='" + abnormalCause + "'" +
            ", abnormalTime='" + abnormalTime + "'" +
            ", remark='" + remark + "'" +
            '}';
    }
}
