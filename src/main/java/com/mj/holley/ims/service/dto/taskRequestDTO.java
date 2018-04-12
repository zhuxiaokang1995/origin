package com.mj.holley.ims.service.dto;

/**
 * Created by YXQ on 2018/4/12.
 */
public class taskRequestDTO {

    private Long serialID;

    private String lPN;

    private String curPos;

    private String curPosType;

    private String reqType;

    private String trayType;

    private String opFlag;

    private String dec;

    public taskRequestDTO() {
    }

    public taskRequestDTO(Long serialID, String lPN, String curPos, String curPosType, String reqType, String trayType, String opFlag, String dec) {
        this.serialID = serialID;
        this.lPN = lPN;
        this.curPos = curPos;
        this.curPosType = curPosType;
        this.reqType = reqType;
        this.trayType = trayType;
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

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getTrayType() {
        return trayType;
    }

    public void setTrayType(String trayType) {
        this.trayType = trayType;
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
