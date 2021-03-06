package com.mj.holley.ims.service.dto;

import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.domain.Processes;
import com.mj.holley.ims.domain.Sn;
import com.mj.holley.ims.domain.Steps;

import javax.validation.constraints.AssertTrue;
import java.util.List;

/**
 * Created by Wanghui on 2018/2/5.
 */
public class MesOrderInfoDto {

    private OrderInfo OrderInfo;

    private List<Steps> Steps;

    private List<Processes> Processes;

    private List<String> SnDetails;

    private List<SerialNumbers> SerialNumbers;

    public List<SerialNumbers> getSerialNumbers() {
        return SerialNumbers;
    }

    public void setSerialNumbers(List<SerialNumbers> serialNumbers) {
        SerialNumbers = serialNumbers;
    }

    public List<String> getSnDetails() {
        return SnDetails;
    }

    public void setSnDetails(List<String> snDetails) {
        SnDetails = snDetails;
    }

    public OrderInfo getOrderInfo() {
        return OrderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.OrderInfo = orderInfo;
    }

    public List<Steps> getSteps() {
        return Steps;
    }

    public void setSteps(List<Steps> steps) {
        Steps = steps;
    }

    public List<Processes> getProcesses() {
        return Processes;
    }

    public void setProcesses(List<Processes> processes) {
        Processes = processes;
    }
}
