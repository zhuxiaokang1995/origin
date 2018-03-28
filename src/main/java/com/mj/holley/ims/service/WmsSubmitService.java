package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.TransportTask;
import com.mj.holley.ims.repository.TransportTaskRepository;
import com.mj.holley.ims.service.dto.MesReturnDto;
import com.mj.holley.ims.service.dto.WmsTransportTaskDTO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
        if (map.containsKey("TransportTask")) {
            Map<String, Object> transportTask = (Map<String, Object>) map.get("TransportTask");
            TransportTask tt = new TransportTask()
                .funID(transportTask.get("FUN_ID").toString())
                .serialID(Integer.parseInt(transportTask.get("SERIAL_ID").toString()))
                .taskID(Integer.parseInt(transportTask.get("TASK_ID").toString()))
                .taskType(transportTask.get("TASK_TYPE").toString())
                .taskPrty(transportTask.get("TASK_PRTY").toString())
                .taskFlag(transportTask.get("TASK_FLAG").toString())
                .lPN(transportTask.get("LPN").toString())
                .frmPos(transportTask.get("FRM_POS").toString())
                .frmPosType(transportTask.get("FRM_POS_TYPE").toString())
                .toPos(transportTask.get("TO_POS").toString())
                .toPosType(transportTask.get("TO_POS_TYPE").toString())
                .opFlag(transportTask.get("OP_FLAG").toString())
                .remark(transportTask.get("REMARK").toString());
            wmsTransportTaskDTO.setTransportTask(tt);
        }
        return wmsTransportTaskDTO;
    }

    public MesReturnDto saveWmsTransportTask(WmsTransportTaskDTO wmsTransportTaskDTO) {
        TransportTask result = transportTaskRepository.save(wmsTransportTaskDTO.getTransportTask());
        return new MesReturnDto(Boolean.TRUE, "Success", "");
    }

}
