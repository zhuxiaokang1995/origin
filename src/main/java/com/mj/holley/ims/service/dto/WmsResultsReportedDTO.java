package com.mj.holley.ims.service.dto;

import java.util.List;

/**
 * Created by YXQ on 2018/4/11.
 */
public class WmsResultsReportedDTO {

    private String funID;

    private String createDate;

    private String userID;

    private List<resultReportedDTO> resultReportedDTO;

    public WmsResultsReportedDTO() {
    }

    public WmsResultsReportedDTO(String funID, String createDate, String userID, List<resultReportedDTO> resultReportedDTO) {
        this.funID = funID;
        this.createDate = createDate;
        this.userID = userID;
        this.resultReportedDTO = resultReportedDTO;
    }

    public String getFunID() {
        return funID;
    }

    public void setFunID(String funID) {
        this.funID = funID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<resultReportedDTO> getResultReportedDTO() {
        return resultReportedDTO;
    }

    public void setResultReportedDTO(List<resultReportedDTO> resultReportedDTO) {
        this.resultReportedDTO = resultReportedDTO;
    }
}
