package com.mj.holley.ims.service.dto;

/**
 * Created by YXQ on 2018/1/29.
 */
public class StepsDTO {

    private String stepID;

    private String stepName;

    private String sequence;

    private String stepAttrID;

    private String stationID;

    public StepsDTO() {

    }

    public StepsDTO(String stepID, String stepName, String sequence, String stepAttrID, String stationID) {
        this.stepID = stepID;
        this.stepName = stepName;
        this.sequence = sequence;
        this.stepAttrID = stepAttrID;
        this.stationID = stationID;
    }

    public String getStepID() {
        return stepID;
    }

    public void setStepID(String stepID) {
        this.stepID = stepID;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getStepAttrID() {
        return stepAttrID;
    }

    public void setStepAttrID(String stepAttrID) {
        this.stepAttrID = stepAttrID;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }
}
