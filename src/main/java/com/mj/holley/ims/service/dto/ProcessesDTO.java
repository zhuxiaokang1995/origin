package com.mj.holley.ims.service.dto;

/**
 * Created by YXQ on 2018/1/29.
 */
public class ProcessesDTO {

    private String subBopID;

    private String processID;

    private String generalSopPath;

    public ProcessesDTO() {

    }

    public ProcessesDTO(String subBopID, String processID, String generalSopPath) {
        this.subBopID = subBopID;
        this.processID = processID;
        this.generalSopPath = generalSopPath;
    }

    public String getSubBopID() {
        return subBopID;
    }

    public void setSubBopID(String subBopID) {
        this.subBopID = subBopID;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public String getGeneralSopPath() {
        return generalSopPath;
    }

    public void setGeneralSopPath(String generalSopPath) {
        this.generalSopPath = generalSopPath;
    }
}
