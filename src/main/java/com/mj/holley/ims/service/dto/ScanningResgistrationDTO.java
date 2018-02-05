package com.mj.holley.ims.service.dto;

/**
 * Created by vtstar on 2018/1/29.
 */
public class ScanningResgistrationDTO {

    private Integer pk;

    private String serialNumber;

    private String stationID;

    private String scanType;

    private String defectCode;

    private String result;

    private String subSns;

    public ScanningResgistrationDTO() {
    }

    public ScanningResgistrationDTO(Integer pk, String serialNumber, String stationID, String scanType, String defectCode, String result, String subSns) {
        this.pk = pk;
        this.serialNumber = serialNumber;
        this.stationID = stationID;
        this.scanType = scanType;
        this.defectCode = defectCode;
        this.result = result;
        this.subSns = subSns;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getDefectCode() {
        return defectCode;
    }

    public void setDefectCode(String defectCode) {
        this.defectCode = defectCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSubSns() {
        return subSns;
    }

    public void setSubSn(String subSns) {
        this.subSns = subSns;
    }

    @Override
    public String toString() {
        return "{" +
            "pk:" + pk +
            ", serialNumber:'" + serialNumber + '\'' +
            ", stationID:'" + stationID + '\'' +
            ", scanType:'" + scanType + '\'' +
            ", defectCode:'" + defectCode + '\'' +
            ", result:'" + result + '\'' +
            ", subSns:'" + subSns + '\'' +
            '}';
    }
}
