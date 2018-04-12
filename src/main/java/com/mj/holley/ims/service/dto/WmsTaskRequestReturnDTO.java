package com.mj.holley.ims.service.dto;

/**
 * 任务请求返回参数DTO
 * Created by YXQ on 2018/4/12.
 */
public class WmsTaskRequestReturnDTO {

    private Long serialID;

    private Long taskID;

    private String taskType;

    private String taskPrty;

    private String taskFlag;

    private String lPN;

    private String frmPos;

    private String frmPosType;

    private String toPos;

    private String toPosType;

    private String workPos;

    private String jobID;

    private String storeType;

    private String dec;

    public WmsTaskRequestReturnDTO() {
    }

    public WmsTaskRequestReturnDTO(Long serialID, Long taskID, String taskType, String taskPrty, String taskFlag, String lPN, String frmPos, String frmPosType, String toPos, String toPosType, String workPos, String jobID, String storeType, String dec) {
        this.serialID = serialID;
        this.taskID = taskID;
        this.taskType = taskType;
        this.taskPrty = taskPrty;
        this.taskFlag = taskFlag;
        this.lPN = lPN;
        this.frmPos = frmPos;
        this.frmPosType = frmPosType;
        this.toPos = toPos;
        this.toPosType = toPosType;
        this.workPos = workPos;
        this.jobID = jobID;
        this.storeType = storeType;
        this.dec = dec;
    }

    public Long getSerialID() {
        return serialID;
    }

    public void setSerialID(Long serialID) {
        this.serialID = serialID;
    }

    public Long getTaskID() {
        return taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskPrty() {
        return taskPrty;
    }

    public void setTaskPrty(String taskPrty) {
        this.taskPrty = taskPrty;
    }

    public String getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(String taskFlag) {
        this.taskFlag = taskFlag;
    }

    public String getlPN() {
        return lPN;
    }

    public void setlPN(String lPN) {
        this.lPN = lPN;
    }

    public String getFrmPos() {
        return frmPos;
    }

    public void setFrmPos(String frmPos) {
        this.frmPos = frmPos;
    }

    public String getFrmPosType() {
        return frmPosType;
    }

    public void setFrmPosType(String frmPosType) {
        this.frmPosType = frmPosType;
    }

    public String getToPos() {
        return toPos;
    }

    public void setToPos(String toPos) {
        this.toPos = toPos;
    }

    public String getToPosType() {
        return toPosType;
    }

    public void setToPosType(String toPosType) {
        this.toPosType = toPosType;
    }

    public String getWorkPos() {
        return workPos;
    }

    public void setWorkPos(String workPos) {
        this.workPos = workPos;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
