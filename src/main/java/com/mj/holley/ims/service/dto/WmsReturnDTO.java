package com.mj.holley.ims.service.dto;

/**
 * Created by YXQ on 2018/4/10.
 */
public class WmsReturnDTO {

    private Integer taskId;

    private String flag;

    private String dec;

    public WmsReturnDTO() {

    }

    public WmsReturnDTO(Integer taskId, String flag, String dec) {
        this.taskId = taskId;
        this.flag = flag;
        this.dec = dec;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
