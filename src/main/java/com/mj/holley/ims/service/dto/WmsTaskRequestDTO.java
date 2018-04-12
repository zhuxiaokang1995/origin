package com.mj.holley.ims.service.dto;

/**
 * Created by YXQ on 2018/4/12.
 */
public class WmsTaskRequestDTO {

    private Long serialID;

    private String lPN;

    private String curPos;

    private String curPosType;

    private String funID;

    private String userID;

    private String reqType;

    private String opFlag;

    private String dec;

    public WmsTaskRequestDTO() {
    }

    public WmsTaskRequestDTO(Long serialID, String lPN, String curPos, String curPosType, String funID, String userID, String reqType, String opFlag, String dec) {
        this.serialID = serialID;
        this.lPN = lPN;
        this.curPos = curPos;
        this.curPosType = curPosType;
        this.funID = funID;
        this.userID = userID;
        this.reqType = reqType;
        this.opFlag = opFlag;
        this.dec = dec;
    }

    public Long getSerialID() {
        return serialID;
    }

    public void setSerialID(Long serialID) {
        this.serialID = serialID;
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

    public String getFunID() {
        return funID;
    }

    public void setFunID(String funID) {
        this.funID = funID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getOpFlag() {
        return opFlag;
    }

    public void setOpFlag(String opFlag) {
        this.opFlag = opFlag;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
