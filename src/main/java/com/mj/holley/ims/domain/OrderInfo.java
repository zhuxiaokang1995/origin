package com.mj.holley.ims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A OrderInfo.
 */
@Entity
@Table(name = "order_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private String orderID;

    @Column(name = "def_id")
    private String defID;

    @Column(name = "def_descript")
    private String defDescript;

    @Column(name = "line_id")
    private String lineID;

    @Column(name = "b_opid")
    private String bOPID;

    @Column(name = "p_pr_name")
    private String pPRName;

    @Column(name = "depart_id")
    private String departID;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "pcba_quantity")
    private Integer pcbaQuantity;

    @Column(name = "order_sequence")
    private String orderSequence;

    @Column(name = "plan_start_date")
    private ZonedDateTime planStartDate;

    @Column(name = "plan_end_date")
    private ZonedDateTime planEndDate;

    @Column(name = "p_cb_program_id")
    private String pCBProgramID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderID;
    }

    public OrderInfo orderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDefID() {
        return defID;
    }

    public OrderInfo defID(String defID) {
        this.defID = defID;
        return this;
    }

    public void setDefID(String defID) {
        this.defID = defID;
    }

    public String getDefDescript() {
        return defDescript;
    }

    public OrderInfo defDescript(String defDescript) {
        this.defDescript = defDescript;
        return this;
    }

    public void setDefDescript(String defDescript) {
        this.defDescript = defDescript;
    }

    public String getLineID() {
        return lineID;
    }

    public OrderInfo lineID(String lineID) {
        this.lineID = lineID;
        return this;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public String getbOPID() {
        return bOPID;
    }

    public OrderInfo bOPID(String bOPID) {
        this.bOPID = bOPID;
        return this;
    }

    public void setbOPID(String bOPID) {
        this.bOPID = bOPID;
    }

    public String getpPRName() {
        return pPRName;
    }

    public OrderInfo pPRName(String pPRName) {
        this.pPRName = pPRName;
        return this;
    }

    public void setpPRName(String pPRName) {
        this.pPRName = pPRName;
    }

    public String getDepartID() {
        return departID;
    }

    public OrderInfo departID(String departID) {
        this.departID = departID;
        return this;
    }

    public void setDepartID(String departID) {
        this.departID = departID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderInfo quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public OrderInfo orderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getPcbaQuantity() {
        return pcbaQuantity;
    }

    public OrderInfo pcbaQuantity(Integer pcbaQuantity) {
        this.pcbaQuantity = pcbaQuantity;
        return this;
    }

    public void setPcbaQuantity(Integer pcbaQuantity) {
        this.pcbaQuantity = pcbaQuantity;
    }

    public String getOrderSequence() {
        return orderSequence;
    }

    public OrderInfo orderSequence(String orderSequence) {
        this.orderSequence = orderSequence;
        return this;
    }

    public void setOrderSequence(String orderSequence) {
        this.orderSequence = orderSequence;
    }

    public ZonedDateTime getPlanStartDate() {
        return planStartDate;
    }

    public OrderInfo planStartDate(ZonedDateTime planStartDate) {
        this.planStartDate = planStartDate;
        return this;
    }

    public void setPlanStartDate(ZonedDateTime planStartDate) {
        this.planStartDate = planStartDate;
    }

    public ZonedDateTime getPlanEndDate() {
        return planEndDate;
    }

    public OrderInfo planEndDate(ZonedDateTime planEndDate) {
        this.planEndDate = planEndDate;
        return this;
    }

    public void setPlanEndDate(ZonedDateTime planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getpCBProgramID() {
        return pCBProgramID;
    }

    public OrderInfo pCBProgramID(String pCBProgramID) {
        this.pCBProgramID = pCBProgramID;
        return this;
    }

    public void setpCBProgramID(String pCBProgramID) {
        this.pCBProgramID = pCBProgramID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderInfo orderInfo = (OrderInfo) o;
        if (orderInfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
            "id=" + id +
            ", orderID='" + orderID + "'" +
            ", defID='" + defID + "'" +
            ", defDescript='" + defDescript + "'" +
            ", lineID='" + lineID + "'" +
            ", bOPID='" + bOPID + "'" +
            ", pPRName='" + pPRName + "'" +
            ", departID='" + departID + "'" +
            ", quantity='" + quantity + "'" +
            ", orderType='" + orderType + "'" +
            ", pcbaQuantity='" + pcbaQuantity + "'" +
            ", orderSequence='" + orderSequence + "'" +
            ", planStartDate='" + planStartDate + "'" +
            ", planEndDate='" + planEndDate + "'" +
            ", pCBProgramID='" + pCBProgramID + "'" +
            '}';
    }
}
