package com.mj.holley.ims.service.dto;

import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.domain.Processes;
import com.mj.holley.ims.domain.Steps;

import javax.validation.constraints.AssertTrue;
import java.util.List;

/**
 * Created by Wanghui on 2018/2/5.
 */
public class MesOrderInfoDto {

    private OrderInfo orderInfo;

    private List<Steps> Steps;

    private List<Processes> Processes;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
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
