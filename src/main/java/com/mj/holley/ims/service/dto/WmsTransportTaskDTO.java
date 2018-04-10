package com.mj.holley.ims.service.dto;

import com.mj.holley.ims.domain.TransportTask;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by YXQ on 2018/3/24.
 */
public class WmsTransportTaskDTO {

    private String funId;

    private String userId;

    private ZonedDateTime createDate = ZonedDateTime.now();

    private List<TransportTask> transportTask;

    public WmsTransportTaskDTO() {

    }

    public WmsTransportTaskDTO(String funId, String userId, ZonedDateTime createDate, List<TransportTask> transportTask) {
        this.funId = funId;
        this.userId = userId;
        this.createDate = createDate;
        this.transportTask = transportTask;
    }

    public String getFunId() {
        return funId;
    }

    public void setFunId(String funId) {
        this.funId = funId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public List<TransportTask> getTransportTask() {
        return transportTask;
    }

    public void setTransportTask(List<TransportTask> transportTask) {
        this.transportTask = transportTask;
    }
}
