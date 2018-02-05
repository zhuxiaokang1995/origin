package com.mj.holley.ims.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mj.holley.ims.domain.util.ZonedDateTimeDeserializer;
import com.mj.holley.ims.domain.util.ZonedDateTimeSerializer;

import java.time.ZonedDateTime;

/**
 * Created by Wanghui on 2018/1/19.
 */
public class MesLineStopDto {

    private int PK;

//    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
//    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private String OperationTime;

    private String SectionID;

    public int getPK() {
        return PK;
    }

    public void setPK(int PK) {
        this.PK = PK;
    }

    public String getOperationTime() {
        return OperationTime;
    }

    public void setOperationTime(String operationTime) {
        OperationTime = operationTime;
    }

    public String getSectionID() {
        return SectionID;
    }

    public void setSectionID(String sectionID) {
        SectionID = sectionID;
    }

    public MesLineStopDto(int PK, String operationTime, String sectionID) {
        this.PK = PK;
        OperationTime = operationTime;
        SectionID = sectionID;
    }

    @Override
    public String toString() {
        return "MesLineStopDto{" +
            "PK=" + PK +
            ", OperationTime=" + OperationTime +
            ", SectionID='" + SectionID + '\'' +
            '}';
    }

}
