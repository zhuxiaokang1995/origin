package com.mj.holley.ims.service.dto;

/**
 * Created by vtstar on 2018/3/27.
 */
public class BingdingDto {

    //操作类型
    private int opeType;

    //序列号
    private String serialNumber;

    //工单号
    private String orderID;

    //工装板号
    private String hutID;

    //工位
    private String stationID;

    public int getOpeType() {
        return opeType;
    }

    public void setOpeType(int opeType) {
        this.opeType = opeType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getHutID() {
        return hutID;
    }

    public void setHutID(String hutID) {
        this.hutID = hutID;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }
}
