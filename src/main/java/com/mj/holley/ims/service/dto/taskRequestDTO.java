package com.mj.holley.ims.service.dto;

/**
 * Created by YXQ on 2018/4/12.
 */
public class taskRequestDTO {

    private Long SERIAL_ID;

    private String LPN;

    private String CUR_POS;

    private String CUR_POS_TYPE;

    private String REQ_TYPE;

    private String TRAY_TYPE;

    private String OP_FLAG;

    private String DEC;

    public Long getSERIAL_ID() {
        return SERIAL_ID;
    }

    public void setSERIAL_ID(Long SERIAL_ID) {
        this.SERIAL_ID = SERIAL_ID;
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

    public void setCUR_POS(String CUR_POS) {
        this.CUR_POS = CUR_POS;
    }

    public String getCUR_POS_TYPE() {
        return CUR_POS_TYPE;
    }

    public void setCUR_POS_TYPE(String CUR_POS_TYPE) {
        this.CUR_POS_TYPE = CUR_POS_TYPE;
    }

    public String getREQ_TYPE() {
        return REQ_TYPE;
    }

    public void setREQ_TYPE(String REQ_TYPE) {
        this.REQ_TYPE = REQ_TYPE;
    }

    public String getTRAY_TYPE() {
        return TRAY_TYPE;
    }

    public void setTRAY_TYPE(String TRAY_TYPE) {
        this.TRAY_TYPE = TRAY_TYPE;
    }

    public String getOP_FLAG() {
        return OP_FLAG;
    }

    public void setOP_FLAG(String OP_FLAG) {
        this.OP_FLAG = OP_FLAG;
    }

    public String getDEC() {
        return DEC;
    }

    public void setDEC(String DEC) {
        this.DEC = DEC;
    }

    public taskRequestDTO(Long SERIAL_ID, String LPN, String CUR_POS, String CUR_POS_TYPE, String REQ_TYPE, String TRAY_TYPE, String OP_FLAG, String DEC) {
        this.SERIAL_ID = SERIAL_ID;
        this.LPN = LPN;
        this.CUR_POS = CUR_POS;
        this.CUR_POS_TYPE = CUR_POS_TYPE;
        this.REQ_TYPE = REQ_TYPE;
        this.TRAY_TYPE = TRAY_TYPE;
        this.OP_FLAG = OP_FLAG;
        this.DEC = DEC;
    }

    public taskRequestDTO() {
    }


}
