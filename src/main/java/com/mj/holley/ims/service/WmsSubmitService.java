package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.TransportTask;
import com.mj.holley.ims.domain.util.TimeZone;
import com.mj.holley.ims.repository.TransportTaskRepository;
import com.mj.holley.ims.service.dto.WmsReturnDTO;
import com.mj.holley.ims.service.dto.WmsTransportTaskDTO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.*;

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
        JSONObject jsonObject = JSONObject.fromObject(input);
        Map<String, Object> m = (Map<String, Object>) jsonObject;
        if (m.containsKey("MSG")) {
            Map<String, Object> map = (Map<String, Object>) m.get("MSG");
            JSONObject json = JSONObject.fromObject(m.get("MSG").toString());

            Map<String, Object> a = (Map<String, Object>) map.get("HEAD");
            String sa = a.get("FUN_ID").toString();
            String sb = a.get("USER_ID").toString();
            ZonedDateTime sc = (ZonedDateTime.now(TimeZone.ASIA_SHANGHAI.getId()));
            wmsTransportTaskDTO.setFunId(sa);
            wmsTransportTaskDTO.setUserId(sb);
            wmsTransportTaskDTO.setCreateDate(sc);

            if (map.containsKey("BODY")) {
                Object steps = map.get("BODY");
                if (steps instanceof JSONArray) {
                    ArrayList<TransportTask> transportTaskList = new ArrayList<>();
                    JSONArray family = json.getJSONArray("BODY");
                    for (int i = 0; i < family.size(); i++) {
                        Map<String, Object> o = (Map<String, Object>) family.get(i);
                        TransportTask tt = new TransportTask()
                            .funID(a.get("FUN_ID").toString())
                            .serialID(Integer.parseInt(o.get("SERIAL_ID").toString()))
                            .taskID(Integer.parseInt(o.get("TASK_ID").toString()))
                            .taskType(o.get("TASK_TYPE").toString())
                            .taskPrty(o.get("TASK_PRTY").toString())
                            .taskFlag(o.get("TASK_FLAG").toString())
                            .lPN(o.get("LPN").toString())
                            .frmPos(o.get("FRM_POS").toString())
                            .frmPosType(o.get("FRM_POS_TYPE").toString())
                            .toPos(o.get("TO_POS").toString())
                            .toPosType(o.get("TO_POS_TYPE").toString())
                            .opFlag(o.get("OP_FLAG").toString())
//                            .remark(o.get("REMARK").toString())
                            .issuedTaskTime(ZonedDateTime.now(TimeZone.ASIA_SHANGHAI.getId()))
                            .storeType(o.get("STORE_TYPE").toString());
                        transportTaskList.add(tt);
                    }
                    wmsTransportTaskDTO.setTransportTask(transportTaskList);
                }
            }
        }
        return wmsTransportTaskDTO;
    }

    public String saveWmsTransportTask(WmsTransportTaskDTO wmsTransportTaskDTO){
        ArrayList<WmsReturnDTO> list = new ArrayList<>();
        for (int i = 0; i < wmsTransportTaskDTO.getTransportTask().size(); i++){
            transportTaskRepository.save(wmsTransportTaskDTO.getTransportTask().get(i));
            WmsReturnDTO wmsReturnDTO = new WmsReturnDTO(wmsTransportTaskDTO.getTransportTask().get(i).getTaskID() , "1" , "SUCCESS");
            list.add(wmsReturnDTO);
        }
        JSONArray jsonObject = JSONArray.fromObject(list);
        Map map = new HashMap();
        map.put("BODY" , jsonObject);
        JSONObject js = JSONObject.fromObject(map);
        return js.toString();
    }

//    public MesReturnDto saveWmsTransportTask(WmsTransportTaskDTO wmsTransportTaskDTO) {
//
//        TransportTask id = transportTaskRepository.findByTaskID(wmsTransportTaskDTO.getTransportTask().getTaskID());
//        if (id != null) {
//            transportTaskRepository.updateTransportTask(wmsTransportTaskDTO.getTransportTask().getFunID(), wmsTransportTaskDTO.getTransportTask().getSerialID(),
//                wmsTransportTaskDTO.getTransportTask().getTaskType(), wmsTransportTaskDTO.getTransportTask().getTaskPrty(), wmsTransportTaskDTO.getTransportTask().getTaskFlag(),
//                wmsTransportTaskDTO.getTransportTask().getlPN(), wmsTransportTaskDTO.getTransportTask().getFrmPos(), wmsTransportTaskDTO.getTransportTask().getFrmPosType(),
//                wmsTransportTaskDTO.getTransportTask().getToPos(), wmsTransportTaskDTO.getTransportTask().getToPosType(), wmsTransportTaskDTO.getTransportTask().getOpFlag(),
//                wmsTransportTaskDTO.getTransportTask().getRemark(), wmsTransportTaskDTO.getTransportTask().getIssuedTaskTime(), wmsTransportTaskDTO.getTransportTask().getTaskID());
//        } else {
//            transportTaskRepository.save(wmsTransportTaskDTO.getTransportTask());
//        }
//        return new MesReturnDto(Boolean.TRUE, "Success", "");
//    }

}
