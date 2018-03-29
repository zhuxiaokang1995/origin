package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.TransportTask;
import com.mj.holley.ims.domain.util.TimeZone;
import com.mj.holley.ims.repository.TransportTaskRepository;
import com.mj.holley.ims.service.dto.MesReturnDto;
import com.mj.holley.ims.service.dto.WmsTransportTaskDTO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Created by YXQ on 2018/3/24.
 */
@Slf4j
@Service
@Transactional
public class WmsSubmitService {

    @Inject
    private TransportTaskRepository transportTaskRepository;

    public static WmsTransportTaskDTO transportTaskToDTO(String input) {
        WmsTransportTaskDTO wmsTransportTaskDTO = new WmsTransportTaskDTO();
        TransportTask tasks = new TransportTask();
        JSONObject jsonObject = JSONObject.fromObject(input);
        Map<String, Object> map = (Map<String, Object>) jsonObject;
            TransportTask tt = new TransportTask()
                .funID(map.get("FUN_ID").toString())
                .serialID(Integer.parseInt(map.get("SERIAL_ID").toString()))
                .taskID(Integer.parseInt(map.get("TASK_ID").toString()))
                .taskType(map.get("TASK_TYPE").toString())
                .taskPrty(map.get("TASK_PRTY").toString())
                .taskFlag(map.get("TASK_FLAG").toString())
                .lPN(map.get("LPN").toString())
                .frmPos(map.get("FRM_POS").toString())
                .frmPosType(map.get("FRM_POS_TYPE").toString())
                .toPos(map.get("TO_POS").toString())
                .toPosType(map.get("TO_POS_TYPE").toString())
                .opFlag(map.get("OP_FLAG").toString())
                .remark(map.get("REMARK").toString())
                .issuedTaskTime(ZonedDateTime.now(TimeZone.ASIA_SHANGHAI.getId()));
            wmsTransportTaskDTO.setTransportTask(tt);
        return wmsTransportTaskDTO;
    }

    public MesReturnDto saveWmsTransportTask(WmsTransportTaskDTO wmsTransportTaskDTO) {

        TransportTask id = transportTaskRepository.findByTaskID(wmsTransportTaskDTO.getTransportTask().getTaskID());
        //String result = "";
        if (id != null) {
            transportTaskRepository.updateTransportTask(wmsTransportTaskDTO.getTransportTask().getFunID(), wmsTransportTaskDTO.getTransportTask().getSerialID(),
                wmsTransportTaskDTO.getTransportTask().getTaskType(), wmsTransportTaskDTO.getTransportTask().getTaskPrty(), wmsTransportTaskDTO.getTransportTask().getTaskFlag(),
                wmsTransportTaskDTO.getTransportTask().getlPN(), wmsTransportTaskDTO.getTransportTask().getFrmPos(), wmsTransportTaskDTO.getTransportTask().getFrmPosType(),
                wmsTransportTaskDTO.getTransportTask().getToPos(), wmsTransportTaskDTO.getTransportTask().getToPosType(), wmsTransportTaskDTO.getTransportTask().getOpFlag(),
                wmsTransportTaskDTO.getTransportTask().getRemark(), wmsTransportTaskDTO.getTransportTask().getIssuedTaskTime(),
                wmsTransportTaskDTO.getTransportTask().getTaskID());
        } else {
            TransportTask result = transportTaskRepository.save(wmsTransportTaskDTO.getTransportTask());
        }
        return new MesReturnDto(Boolean.TRUE, "Success", "");
    }
}
