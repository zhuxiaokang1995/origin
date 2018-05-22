package com.mj.holley.ims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RepeatProcess.
 */
@Entity
@Table(name = "repeat_process")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RepeatProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "process_num")
    private String processNum;

    @Column(name = "process_name")
    private String processName;

    @Column(name = "descriple")
    private String descriple;

    @ManyToOne
    private OrderInfo orderInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessNum() {
        return processNum;
    }

    public RepeatProcess processNum(String processNum) {
        this.processNum = processNum;
        return this;
    }

    public void setProcessNum(String processNum) {
        this.processNum = processNum;
    }

    public String getProcessName() {
        return processName;
    }

    public RepeatProcess processName(String processName) {
        this.processName = processName;
        return this;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getDescriple() {
        return descriple;
    }

    public RepeatProcess descriple(String descriple) {
        this.descriple = descriple;
        return this;
    }

    public void setDescriple(String descriple) {
        this.descriple = descriple;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public RepeatProcess orderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        return this;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepeatProcess repeatProcess = (RepeatProcess) o;
        if (repeatProcess.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, repeatProcess.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RepeatProcess{" +
            "id=" + id +
            ", processNum='" + processNum + "'" +
            ", processName='" + processName + "'" +
            ", descriple='" + descriple + "'" +
            '}';
    }
}
