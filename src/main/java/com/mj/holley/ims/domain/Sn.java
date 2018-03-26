package com.mj.holley.ims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Sn.
 */
@Entity
@Table(name = "sn")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sn implements Serializable {

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

    @Column(name = "is_binding")
    private Boolean isBinding;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Sn serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getHutID() {
        return hutID;
    }

    public Sn hutID(String hutID) {
        this.hutID = hutID;
        return this;
    }

    public void setHutID(String hutID) {
        this.hutID = hutID;
    }

    public String getOrderID() {
        return orderID;
    }

    public Sn orderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Boolean isIsBinding() {
        return isBinding;
    }

    public Sn isBinding(Boolean isBinding) {
        this.isBinding = isBinding;
        return this;
    }

    public void setIsBinding(Boolean isBinding) {
        this.isBinding = isBinding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sn sn = (Sn) o;
        if (sn.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sn.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sn{" +
            "id=" + id +
            ", serialNumber='" + serialNumber + "'" +
            ", hutID='" + hutID + "'" +
            ", orderID='" + orderID + "'" +
            ", isBinding='" + isBinding + "'" +
            '}';
    }
}
