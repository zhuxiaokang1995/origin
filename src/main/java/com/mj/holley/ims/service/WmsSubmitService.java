package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.TransportTask;
import com.mj.holley.ims.domain.util.TimeZone;
import com.mj.holley.ims.repository.TransportTaskRepository;
import com.mj.holley.ims.service.dto.WmsResultsReportedDTO;
import com.mj.holley.ims.service.dto.WmsReturnDTO;
import com.mj.holley.ims.service.dto.WmsTransportTaskDTO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
                            .serialID(Long.parseLong(o.get("SERIAL_ID").toString()))
                            .taskID(Long.parseLong(o.get("TASK_ID").toString()))
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


    public HashMap<Object,Object> requestSoapServices(String body, String contentType) throws IOException {
        HashMap<Object,Object> resultMap = new HashMap<Object,Object>();
        String urlPath = new String("http://139.196.253.114/HZHL_WCSWebservice/Service.asmx");
        String param = body;
        //建立连接
        URL url = new URL(urlPath);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        //设置参数
        httpConn.setDoOutput(true); //需要输出
        httpConn.setDoInput(true); //需要输入
        httpConn.setUseCaches(false); //不允许缓存
        httpConn.setRequestMethod("POST"); //设置POST方式连接
        //设置请求属性
        httpConn.setRequestProperty("Content-Type", contentType); //"text/xml"
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        //建立输入流，向指向的URL传入参数
        DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
        dos.writeBytes(param);
        dos.flush();
        dos.close();
        //获得响应状态
        int resultCode = httpConn.getResponseCode();
        resultMap.put("resultCode",resultCode);
        if (HttpURLConnection.HTTP_OK == resultCode) {
            StringBuffer sb = new StringBuffer();
            String readLine = new String();

            BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            String bbb = sb.toString();
            System.out.println(bbb);
            resultMap.put("resultValue",bbb);
        }else {
            resultMap.put("resultValue","Error");
        }
        return resultMap;

    }

    public HashMap submitTaskExecutionResult(WmsResultsReportedDTO dto) throws IOException {
        String param =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <tem:SendData>\n" +
            "         <!--Optional:-->\n" +
            "         <tem:DATA>"+
            dto.toString()+
            "</tem:DATA>\n" +
            "      </tem:SendData>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";
        HashMap result = requestSoapServices(param, "text/xml");
        if (!result.get("resultCode").equals(200)){
            log.error("WmsResultsReportedDTO[{}]{}提交失败，错误信息：{}", dto.getTaskFlag(),dto.getDec());
        }else{
            log.info("WmsResultsReportedDTO[{}]{}提交成功", dto.getTaskFlag(),dto.getDec());
        }
        return result;
    }
}
