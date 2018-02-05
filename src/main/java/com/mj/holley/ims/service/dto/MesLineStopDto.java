package com.mj.holley.ims.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mj.holley.ims.domain.util.ZonedDateTimeDeserializer;
import com.mj.holley.ims.domain.util.ZonedDateTimeSerializer;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by Wanghui on 2018/1/19.
 */
public class MesLineStopDto {

    private int PK;

    private int ErrorType;

//    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
//    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private Date OperationTime;

    private String StationID;

    public int getPK() {
        return PK;
    }

    public void setPK(int PK) {
        this.PK = PK;
    }

    public int getErrorType() {
        return ErrorType;
    }

    public void setErrorType(int errorType) {
        ErrorType = errorType;
    }

    public Date getOperationTime() {
        return OperationTime;
    }

    public void setOperationTime(Date operationTime) {
        OperationTime = operationTime;
    }

    public String getStationID() {
        return StationID;
    }

    public void setStationID(String stationID) {
        StationID = stationID;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return "{" +
            "PK:" + PK +
            ", ErrorType:" + ErrorType +
            ", OperationTime:'" + sdf3.format(OperationTime) +'\'' +
            ", StationID:'" + StationID + '\'' +
            '}';
    }

}
