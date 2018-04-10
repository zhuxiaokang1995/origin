package com.mj.holley.ims.service.dto;

/**
 * Created by vtstar on 2018/4/9.
 */
public class SerialNumbers {

    //箱号
    private String hutID;

    //序列号（铭牌）
    private String namePlate;

    public SerialNumbers() {
    }

    public SerialNumbers(String hutID, String namePlate) {
        this.hutID = hutID;
        this.namePlate = namePlate;
    }

    public String getHutID() {
        return hutID;
    }

    public void setHutID(String hutID) {
        this.hutID = hutID;
    }

    public String getNamePlate() {
        return namePlate;
    }

    public void setNamePlate(String namePlate) {
        this.namePlate = namePlate;
    }
}
