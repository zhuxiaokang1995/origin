package com.mj.holley.ims.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TransportTask.
 */
@Entity
@Table(name = "transport_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransportTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fun_id")
    private String funID;

    @Column(name = "task_type")
    private String taskType;

    @Column(name = "task_prty")
    private String taskPrty;

    @Column(name = "task_flag")
    private String taskFlag;

    @Column(name = "l_pn")
    private String lPN;

    @Column(name = "frm_pos")
    private String frmPos;

    @Column(name = "frm_pos_type")
    private String frmPosType;

    @Column(name = "to_pos")
    private String toPos;

    @Column(name = "to_pos_type")
    private String toPosType;

    @Column(name = "op_flag")
    private String opFlag;

    @Column(name = "remark")
    private String remark;

    @Column(name = "issued_task_time")
    private ZonedDateTime issuedTaskTime;

    @Column(name = "completion_time")
    private ZonedDateTime completionTime;

    @Column(name = "store_type")
    private String storeType;

    @Column(name = "serial_id")
    private Long serialID;

    @Column(name = "task_id")
    private Long taskID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunID() {
        return funID;
    }

    public TransportTask funID(String funID) {
        this.funID = funID;
        return this;
    }

    public void setFunID(String funID) {
        this.funID = funID;
    }

    public String getTaskType() {
        return taskType;
    }

    public TransportTask taskType(String taskType) {
        this.taskType = taskType;
        return this;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskPrty() {
        return taskPrty;
    }

    public TransportTask taskPrty(String taskPrty) {
        this.taskPrty = taskPrty;
        return this;
    }

    public void setTaskPrty(String taskPrty) {
        this.taskPrty = taskPrty;
    }

    public String getTaskFlag() {
        return taskFlag;
    }

    public TransportTask taskFlag(String taskFlag) {
        this.taskFlag = taskFlag;
        return this;
    }

    public void setTaskFlag(String taskFlag) {
        this.taskFlag = taskFlag;
    }

    public String getlPN() {
        return lPN;
    }

    public TransportTask lPN(String lPN) {
        this.lPN = lPN;
        return this;
    }

    public void setlPN(String lPN) {
        this.lPN = lPN;
    }

    public String getFrmPos() {
        return frmPos;
    }

    public TransportTask frmPos(String frmPos) {
        this.frmPos = frmPos;
        return this;
    }

    public void setFrmPos(String frmPos) {
        this.frmPos = frmPos;
    }

    public String getFrmPosType() {
        return frmPosType;
    }

    public TransportTask frmPosType(String frmPosType) {
        this.frmPosType = frmPosType;
        return this;
    }

    public void setFrmPosType(String frmPosType) {
        this.frmPosType = frmPosType;
    }

    public String getToPos() {
        return toPos;
    }

    public TransportTask toPos(String toPos) {
        this.toPos = toPos;
        return this;
    }

    public void setToPos(String toPos) {
        this.toPos = toPos;
    }

    public String getToPosType() {
        return toPosType;
    }

    public TransportTask toPosType(String toPosType) {
        this.toPosType = toPosType;
        return this;
    }

    public void setToPosType(String toPosType) {
        this.toPosType = toPosType;
    }

    public String getOpFlag() {
        return opFlag;
    }

    public TransportTask opFlag(String opFlag) {
        this.opFlag = opFlag;
        return this;
    }

    public void setOpFlag(String opFlag) {
        this.opFlag = opFlag;
    }

    public String getRemark() {
        return remark;
    }

    public TransportTask remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ZonedDateTime getIssuedTaskTime() {
        return issuedTaskTime;
    }

    public TransportTask issuedTaskTime(ZonedDateTime issuedTaskTime) {
        this.issuedTaskTime = issuedTaskTime;
        return this;
    }

    public void setIssuedTaskTime(ZonedDateTime issuedTaskTime) {
        this.issuedTaskTime = issuedTaskTime;
    }

    public ZonedDateTime getCompletionTime() {
        return completionTime;
    }

    public TransportTask completionTime(ZonedDateTime completionTime) {
        this.completionTime = completionTime;
        return this;
    }

    public void setCompletionTime(ZonedDateTime completionTime) {
        this.completionTime = completionTime;
    }

    public String getStoreType() {
        return storeType;
    }

    public TransportTask storeType(String storeType) {
        this.storeType = storeType;
        return this;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public Long getSerialID() {
        return serialID;
    }

    public TransportTask serialID(Long serialID) {
        this.serialID = serialID;
        return this;
    }

    public void setSerialID(Long serialID) {
        this.serialID = serialID;
    }

    public Long getTaskID() {
        return taskID;
    }

    public TransportTask taskID(Long taskID) {
        this.taskID = taskID;
        return this;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransportTask transportTask = (TransportTask) o;
        if (transportTask.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transportTask.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransportTask{" +
            "id=" + id +
            ", funID='" + funID + "'" +
            ", taskType='" + taskType + "'" +
            ", taskPrty='" + taskPrty + "'" +
            ", taskFlag='" + taskFlag + "'" +
            ", lPN='" + lPN + "'" +
            ", frmPos='" + frmPos + "'" +
            ", frmPosType='" + frmPosType + "'" +
            ", toPos='" + toPos + "'" +
            ", toPosType='" + toPosType + "'" +
            ", opFlag='" + opFlag + "'" +
            ", remark='" + remark + "'" +
            ", issuedTaskTime='" + issuedTaskTime + "'" +
            ", completionTime='" + completionTime + "'" +
            ", storeType='" + storeType + "'" +
            ", serialID='" + serialID + "'" +
            ", taskID='" + taskID + "'" +
            '}';
    }
}
