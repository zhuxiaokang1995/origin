package com.mj.holley.ims.service.dto;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by YXQ on 2018/4/12.
 */
public class WmsTaskRequestDTO {

    private String funID;

    private String userID;

    private ZonedDateTime createDate = ZonedDateTime.now();

    private List<taskRequestDTO> taskRequestDTO;

    public WmsTaskRequestDTO() {
    }

    public WmsTaskRequestDTO(String funID, String userID, ZonedDateTime createDate, List<taskRequestDTO> taskRequestDTO) {
        this.funID = funID;
        this.userID = userID;
        this.createDate = createDate;
        this.taskRequestDTO = taskRequestDTO;
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

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public List<taskRequestDTO> getTaskRequestDTO() {
        return taskRequestDTO;
    }

    public void setTaskRequestDTO(List<taskRequestDTO> taskRequestDTO) {
        this.taskRequestDTO = taskRequestDTO;
    }
}
