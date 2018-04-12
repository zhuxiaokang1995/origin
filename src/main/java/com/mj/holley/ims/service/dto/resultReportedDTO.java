package com.mj.holley.ims.service.dto;

/**
 * Created by YXQ on 2018/4/12.
 */
public class resultReportedDTO {

    private Long serialID;

    private Long taskID;

    private String taskFlag;

    private String lPN;

    private String curPos;

    private String curPosType;

    private String errCode;

    private String dec;

    public resultReportedDTO() {
    }

    public resultReportedDTO(Long serialID, Long taskID, String taskFlag, String lPN, String curPos, String curPosType, String errCode, String dec) {
        this.serialID = serialID;
        this.taskID = taskID;
        this.taskFlag = taskFlag;
        this.lPN = lPN;
        this.curPos = curPos;
        this.curPosType = curPosType;
        this.errCode = errCode;
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

    public String getCurPos() {
        return curPos;
    }

    public void setCurPos(String curPos) {
        this.curPos = curPos;
    }

    public String getCurPosType() {
        return curPosType;
    }

    public void setCurPosType(String curPosType) {
        this.curPosType = curPosType;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
