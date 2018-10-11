package com.mj.holley.ims.service;

import com.mj.holley.ims.config.httpClient.HttpTemplateMes;
import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.domain.Processes;
import com.mj.holley.ims.domain.RepeatProcess;
import com.mj.holley.ims.domain.Steps;
import com.mj.holley.ims.domain.util.TimeZone;
import com.mj.holley.ims.repository.OrderInfoRepository;
import com.mj.holley.ims.repository.ProcessesRepository;
import com.mj.holley.ims.repository.RepeatProcessRepository;
import com.mj.holley.ims.repository.StepsRepository;
import com.mj.holley.ims.service.dto.*;
import com.mj.holley.ims.service.util.ConstantValue;
import com.mj.holley.ims.service.util.SortListUtil;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Wanghui on 2018/1/19.
 */
@Slf4j
@Service
@Transactional
public class MesSubmitService {

    //扫描组装登记缺陷
    public static final String SCANNING_REGISTRATION = "/api/MesApi/scanningRegistration";

    public static final String SUBMIT_LINE_STOP = "/api/MesApi/LineStop";

    @Inject
    private HttpTemplateMes httpTemplateMes;

    @Inject
    private OrderInfoRepository orderInfoRepository;

    @Inject
    private StepsRepository stepsRepository;

    @Inject
    private ProcessesRepository processesRepository;

    @Inject
    private RepeatProcessRepository repeatProcessRepository;

//    @SuppressWarnings("unchecked")
    public static MesOrderInfoDto transStringToDto(String input) {
        MesOrderInfoDto mesOrderInfoDto = new MesOrderInfoDto();
        JSONObject jsonObject = JSONObject.fromObject(input);
        Map<String, Object> map = (Map<String, Object>) jsonObject;
        if (map.containsKey("Order")) {
            Map<String, Object> OrderInfo = (Map<String, Object>) map.get("Order");
            OrderInfo ol = new OrderInfo()
                .orderID(OrderInfo.get("OrderID").toString())
                .defID(OrderInfo.get("DefID").toString())
                .defDescript(OrderInfo.get("DefDescript").toString())
                .lineID(OrderInfo.get("LineID").toString())
                .bOPID(OrderInfo.get("BOPID").toString())
                .pPRName(OrderInfo.get("PprName").toString())
                .departID(OrderInfo.get("DepartID").toString())
                .orderType(OrderInfo.get("OrderType").toString())
                .pcbaQuantity(Integer.parseInt(OrderInfo.get("PcbaQuantity").toString()))
                .orderSequence(OrderInfo.get("OrderSequence").toString())
                .planStartDate(ZonedDateTime.now(TimeZone.ASIA_SHANGHAI.getId()))
                //.planEndDate(ZonedDateTime.parse(OrderInfo.get("PlanEndDate").toString()))
                .pCBProgramID(OrderInfo.get("PCBProgramID").toString())
                .quantity(Integer.parseInt(OrderInfo.get("Quantity").toString()));
            mesOrderInfoDto.setOrderInfo(ol);

        }
        if (map.containsKey("Steps")) {
            Object steps = map.get("Steps");
            if (steps instanceof JSONArray) {
                ArrayList<Steps> stepsList = new ArrayList<>();
                JSONArray family = jsonObject.getJSONArray("Steps");
                for (int i = 0; i < family.size(); i++) {
                    Map<String, Object> o = (Map<String, Object>) family.get(i);

                    Steps st = new Steps()
                        .stepID(o.get("StepID").toString())
                        .stepName(o.get("StepName").toString())
                        .sequence(o.get("Sequence").toString())
                        .stepAttrID(o.get("StepAttrID").toString())
                        .stationID(o.get("StationID").toString());
                    stepsList.add(st);
                }
                mesOrderInfoDto.setSteps(stepsList);
            }
        }

        if (map.containsKey("Processes")) {
            Object steps = map.get("Processes");
            if (steps instanceof JSONArray) {
                ArrayList<Processes> processesList = new ArrayList<>();
                JSONArray family = jsonObject.getJSONArray("Processes");
                for (int i = 0; i < family.size(); i++) {
                    Map<String, Object> o = (Map<String, Object>) family.get(i);
                    Processes st = new Processes()
                        .subBopID(o.get("SubBopID").toString())
                        .processID(o.get("ProcessID").toString())
                        .generalSopPath(o.get("GeneralSopPath").toString())
                        .subBopName(o.get("SubBopName").toString());
                    processesList.add(st);
                }
                mesOrderInfoDto.setProcesses(processesList);
            }
        }


        if (map.containsKey("SerialNumbers")) {
            Object serialNumbers = map.get("SerialNumbers");
            if (serialNumbers instanceof JSONArray) {
                ArrayList<SerialNumbers> serialNumbersList = new ArrayList<>();
                JSONArray family = jsonObject.getJSONArray("SerialNumbers");
                for (int i = 0; i < family.size(); i++) {
                    Map<String, Object> o = (Map<String, Object>) family.get(i);
                    SerialNumbers sn = new SerialNumbers();
                        sn.setHutID(o.get("HutID").toString());
                        sn.setNamePlate(o.get("NamePlate").toString());
                    serialNumbersList.add(sn);
                }
                mesOrderInfoDto.setSerialNumbers(serialNumbersList);
            }
        }


        if (map.containsKey("SnDetails")) {
            Object obj = map.get("SnDetails");
            if (obj instanceof JSONArray) {
                ArrayList<String > snList = new ArrayList<>();
                JSONArray family = jsonObject.getJSONArray("SnDetails");
                for (int i = 0; i < family.size(); i++) {
                    Map<String, Object> o = (Map<String, Object>) family.get(i);
                    String  sn = o.get("SerialNumber").toString();
                    snList.add(sn);
                }
                mesOrderInfoDto.setSnDetails(snList);
            }
        }


        return mesOrderInfoDto;
    }



    public MesReturnDto saveMesOrder(MesOrderInfoDto mesOrderInfoDto){
        OrderInfo result = orderInfoRepository.save(mesOrderInfoDto.getOrderInfo());
        if(mesOrderInfoDto.getSteps()!=null){
            for(Steps steps : mesOrderInfoDto.getSteps()){
                steps.setOrderInfo(result);
            }
            stepsRepository.save(mesOrderInfoDto.getSteps());
            String re = processedSteps(mesOrderInfoDto.getSteps());
            RepeatProcess repeatProcess = new RepeatProcess()
                .processName(re)
                .descriple(re)
                .processNum(re)
                .orderInfo(result);
            repeatProcessRepository.save(repeatProcess);
        }

        if(mesOrderInfoDto.getProcesses()!=null) {
            for (Processes processes : mesOrderInfoDto.getProcesses()) {
                processes.setOrderInfo(result);
            }
            processesRepository.save(mesOrderInfoDto.getProcesses());
        }
        return new MesReturnDto(Boolean.TRUE,"Success","");
    }

    /**
     * 按MES给的工艺路径解析出来PLC需要的路径格式 如11223344
     * @param Steps
     * @return
     */
    public String processedSteps(List<Steps> Steps) {
        List<Steps> list1 = new ArrayList<>();
        list1 = Steps.stream().filter(s -> ConstantValue.REPEAT_STATION_NINE_LIST.contains(s.getStationID())).collect(Collectors.toList());
        SortListUtil.sort(list1, "stationID", SortListUtil.ASC);
        Map<String, Integer> typeMap = new HashMap<String, Integer>();
        Integer num = 1;
        for( int i= 0;i<list1.size();i++){
            if (i==0){
                typeMap.put(list1.get(i).getStationID(),num);
                continue;
            }
            if(list1.get(i).getStepID().equals(list1.get(i-1).getStepID())){
                typeMap.put(list1.get(i).getStationID(),num);
            }else {
                num = num + 1;
                typeMap.put(list1.get(i).getStationID(),num);

            }
        }
        String result = new String();
        for(String station:ConstantValue.REPEAT_STATION_NINE_LIST){
            if (typeMap.containsKey(station)){
                result = result + typeMap.get(station);
            }else {
                result = result + "0";
            }
        }
        return result;

    }

    public HashMap<Object,Object> requestSoapService(String body, String contentType) throws IOException {
        HashMap<Object,Object> resultMap = new HashMap<Object,Object>();
        String urlPath = new String("http://172.30.40.100/ScadaService/ScadaService.asmx");
        //String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));
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
//            String ccc = bbb.substring(bbb.indexOf("resultHeader")+21, bbb.lastIndexOf("</ScadaStoplineResult>"));
            System.out.println(bbb);
            resultMap.put("resultValue",bbb);
        }else {
            resultMap.put("resultValue","Error");
        }
        return resultMap;

    }
//    /**
//     * 扫描组装登记缺陷
//     * @param scanningResgistrationDTO
//     */
//
//    public void submitScanningRegistration (ScanningResgistrationDTO scanningResgistrationDTO ){
//        String url = httpTemplateMes.getMesApiHttpSchemeHierarchical();
//        MesApiAccessResult result = httpTemplateMes.postForObject(url + SCANNING_REGISTRATION, scanningResgistrationDTO, MesApiAccessResult.class);
//        if (result.isSuccess()) {
//            log.info("ScanningRegistration[{}]{}提交成功", scanningResgistrationDTO);
//        }
//        else {
//            log.error("ScanningRegistration[{}]{}提交失败，错误信息：{}", scanningResgistrationDTO);
//        }
//
//    }

    public void submitLineStop(MesLineStopDto dto) throws IOException {
        String param = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <tem:ScadaStopline>\n" +
            "         <!--Optional:-->\n" +
            "         <tem:param>"+
            dto.toString()+
        "</tem:param>\n" +
            "      </tem:ScadaStopline>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";
        HashMap result = requestSoapService(param, "text/xml");
        if (!result.get("resultCode").equals(200)){
            log.error("MesLineStopDto[{}]{}提交失败，错误信息：{}", dto.getPK(),dto.getOperationTime(),dto.getStationID(),dto.getErrorType());
        }else{
            log.info("MesLineStopDto[{}]{}提交成功", dto.getPK(),dto.getOperationTime(),dto.getStationID(),dto.getErrorType());}
    }

    public HashMap submitScanningRegistration (ScanningResgistrationDTO scanningResgistrationDTO ) throws IOException {
        String param = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\n" +
            "   <soap:Header/>\n" +
            "   <soap:Body>\n" +
            "      <tem:ScadaWip>\n" +
            "         <!--Optional:-->\n" +
            "         <tem:param>"+
            scanningResgistrationDTO.toString()+
            "</tem:param>\n" +
            "      </tem:ScadaWip>\n" +
            "   </soap:Body>\n" +
            "</soap:Envelope>";
        HashMap result = requestSoapService(param, "application/soap+xml");
        if (!result.get("resultCode").equals(200)){
            log.error("ScanningResgistrationDTO[{}]{}提交失败，错误信息：{}", scanningResgistrationDTO);
        }else{
            log.info("ScanningResgistrationDTO[{}]{}提交成功", scanningResgistrationDTO);}
        return result;
    }

}
