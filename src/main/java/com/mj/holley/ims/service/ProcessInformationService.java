package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.domain.Processes;
import com.mj.holley.ims.domain.Steps;
import com.mj.holley.ims.repository.OrderInfoRepository;
import com.mj.holley.ims.repository.ProcessesRepository;
import com.mj.holley.ims.repository.StepsRepository;
import com.mj.holley.ims.service.dto.ProcessesDTO;
import com.mj.holley.ims.service.dto.StepsDTO;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by YXQ on 2018/1/29.
 */
public class ProcessInformationService {

    @Inject
    private OrderInfoRepository orderInfoRepository;

    @Inject
    private ProcessesRepository processesRepository;

    @Inject
    private StepsRepository stepsRepository;

//    public void ProcessInformation(Optional<OrderInfo> orderInfo , List<StepsDTO> stepsDTO , List<ProcessesDTO> processesDTO){
//
//        OrderInfo OI = new OrderInfo()
//            .orderID(orderInfo.get().getOrderID())
//            .defID(orderInfo.get().getDefID())
//            .defDescript(orderInfo.get().getDefDescript())
//            .lineID(orderInfo.get().getLineID())
//            .bOPID(orderInfo.get().getbOPID())
//            .pPRName(orderInfo.get().getpPRName());
//        orderInfoRepository.saveAndFlush(OI);
//
//        stepsDTO.stream().forEach(s -> {
//            Steps steps = new Steps()
//                .stepID(s.getStepID())
//                .stepName(s.getStepName())
//                .sequence(s.getSequence())
//                .stepAttrID(s.getStepAttrID())
//                .stationID(s.getStationID());
//            stepsRepository.saveAndFlush(steps);
//        });
//
//        processesDTO.stream().forEach(p -> {
//            Processes processes = new Processes()
//                .subBopID(p.getSubBopID())
//                .processID(p.getProcessID())
//                .generalSopPath(p.getGeneralSopPath());
//            processesRepository.saveAndFlush(processes);
//        });

//    }
}
