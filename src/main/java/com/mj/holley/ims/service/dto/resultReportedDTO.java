package com.mj.holley.ims.service.dto;

/**
 * Created by YXQ on 2018/4/12.
 */
public class resultReportedDTO {

    private Long SERIAL_ID;

    private Long TASK_ID;

    private String TASK_FLAG;

    private String LPN;

    private String CUR_POS;

    private String CUR_POS_TYPE;

    private String ERR_CODE;

    private String DEC;

    public resultReportedDTO() {
    }

    public resultReportedDTO(Long SERIAL_ID, Long TASK_ID, String TASK_FLAG, String LPN, String CUR_POS, String CUR_POS_TYPE, String ERR_CODE, String DEC) {
        this.SERIAL_ID = SERIAL_ID;
        this.TASK_ID = TASK_ID;
        this.TASK_FLAG = TASK_FLAG;
        this.LPN = LPN;
        this.CUR_POS = CUR_POS;
        this.CUR_POS_TYPE = CUR_POS_TYPE;
        this.ERR_CODE = ERR_CODE;
        this.DEC = DEC;
    }

    public Long getSERIAL_ID() {
        return SERIAL_ID;
    }

    public void setSERIAL_ID(Long SERIAL_ID) {
        this.SERIAL_ID = SERIAL_ID;
    }

    public Long getTASK_ID() {
        return TASK_ID;
    }

    public void setTASK_ID(Long TASK_ID) {
        this.TASK_ID = TASK_ID;
    }

    public String getTASK_FLAG() {
        return TASK_FLAG;
    }

    public void setTASK_FLAG(String TASK_FLAG) {
        this.TASK_FLAG = TASK_FLAG;
    }

    public String getLPN() {
        return LPN;
    }

    public void setLPN(String LPN) {
        this.LPN = LPN;
    }

    public String getCUR_POS() {
        return CUR_POS;
    }

    public void setCUR_POS(String curPos) {
        this.CUR_POS = curPos;
    }

    public String getCUR_POS_TYPE() {
        return CUR_POS_TYPE;
    }

    public void setCUR_POS_TYPE(String CUR_POS_TYPE) {
        this.CUR_POS_TYPE = CUR_POS_TYPE;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }

    public void setERR_CODE(String ERR_CODE) {
        this.ERR_CODE = ERR_CODE;
    }

    public String getDEC() {
        return DEC;
    }

    public void setDEC(String DEC) {
        this.DEC = DEC;
    }
}
