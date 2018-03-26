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

//    public MesReturnDto transportTaskToDTO(String input) {
//        //WmsTransportTaskDTO wmsTransportTaskDTO = new WmsTransportTaskDTO();
//        TransportTask tasks = new TransportTask();
//        JSONObject jsonObject = JSONObject.fromObject(input);
//        Map<String, Object> map = (Map<String, Object>) jsonObject;
//        if (map.containsKey("TransportTask")) {
//            Map<String, Object> transportTask = (Map<String, Object>) map.get("TransportTask");
//            //TransportTask tt = new TransportTask()
//            tasks.setFunID(transportTask.get("FunID").toString());
//            tasks.setSerialID(Integer.parseInt(transportTask.get("SerialID").toString()));
//            tasks.setTaskID(Integer.parseInt(transportTask.get("TaskID").toString()));
//            tasks.setTaskType(transportTask.get("TaskType").toString());
//            tasks.setTaskPrty(transportTask.get("TaskPrty").toString());
//            tasks.setTaskFlag(transportTask.get("TaskFlag").toString());
//            tasks.setlPN(transportTask.get("LPN").toString());
//            tasks.setFrmPos(transportTask.get("FrmPos").toString());
//            tasks.setFrmPosType(transportTask.get("FrmPosType").toString());
//            tasks.setToPos(transportTask.get("ToPos").toString());
//            tasks.setToPosType(transportTask.get("ToPosType").toString());
//            tasks.setOpFlag(transportTask.get("OpFlag").toString());
//            tasks.setRemark(transportTask.get("Remark").toString());
//        }
//        //return tasks;
//        TransportTask result = transportTaskRepository.save(tasks);
//        return new MesReturnDto(Boolean.TRUE,"Success","");
//    }
    public static WmsTransportTaskDTO transportTaskToDTO(String input) {
        WmsTransportTaskDTO wmsTransportTaskDTO = new WmsTransportTaskDTO();
        TransportTask tasks = new TransportTask();
        JSONObject jsonObject = JSONObject.fromObject(input);
        Map<String, Object> map = (Map<String, Object>) jsonObject;
        if (map.containsKey("TransportTask")) {
            Map<String, Object> transportTask = (Map<String, Object>) map.get("TransportTask");
            TransportTask tt = new TransportTask()
                .funID(transportTask.get("FunID").toString())
                .serialID(Integer.parseInt(transportTask.get("SerialID").toString()))
                .taskID(Integer.parseInt(transportTask.get("TaskID").toString()))
                .taskType(transportTask.get("TaskType").toString())
                .taskPrty(transportTask.get("TaskPrty").toString())
                .taskFlag(transportTask.get("TaskFlag").toString())
                .lPN(transportTask.get("LPN").toString())
                .frmPos(transportTask.get("FrmPos").toString())
                .frmPosType(transportTask.get("FrmPosType").toString())
                .toPos(transportTask.get("ToPos").toString())
                .toPosType(transportTask.get("ToPosType").toString())
                .opFlag(transportTask.get("OpFlag").toString())
                .remark(transportTask.get("Remark").toString());
            wmsTransportTaskDTO.setTransportTask(tt);
        }
        return wmsTransportTaskDTO;
    }

    public MesReturnDto saveWmsTransportTask(WmsTransportTaskDTO wmsTransportTaskDTO) {
        TransportTask result = transportTaskRepository.save(wmsTransportTaskDTO.getTransportTask());
        return new MesReturnDto(Boolean.TRUE, "Success", "");
    }

}