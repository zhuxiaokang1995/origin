package com.mj.holley.ims.service;

import com.mj.holley.ims.service.dto.WmsTaskRequestDTO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YXQ on 2018/4/12.
 */
@Slf4j
@Service
@Transactional
public class WmsTaskRequestService {

    @Inject
    private WmsSubmitService wmsSubmitService;

    public String dtoToJson(WmsTaskRequestDTO dto){
        JSONObject obj = new JSONObject();
        obj.accumulate("FUN_ID" , dto.getFunID());
        obj.accumulate("USER_ID" , dto.getUserID());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String str = sdf.format(date);
        obj.accumulate("CREATE_DATE" , str);
        Map map = new HashMap();
        map.put("HEAD" , obj);
        JSONArray jsonObject = JSONArray.fromObject(dto.getTaskRequestDTO());
        map.put("BODY" , jsonObject);
        Map ma = new HashMap();
        ma.put("MSG", map);
        JSONObject js = JSONObject.fromObject(ma);
        return js.toString();
    }

    public HashMap taskRequest(WmsTaskRequestDTO dto) throws IOException {
        String param =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <tem:SendData>\n" +
            "         <!--Optional:-->\n" +
            "         <tem:DATA>"+
            dtoToJson(dto)+
            "</tem:DATA>\n" +
            "      </tem:SendData>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";
        HashMap result = wmsSubmitService.requestSoapServices(param, "text/xml");
        if (!result.get("resultCode").equals(200)){
            log.error("WmsResultsReportedDTO[{}]{}提交失败，错误信息：{}", dto);
        }else{
            log.info("WmsResultsReportedDTO[{}]{}提交成功", dto);
        }
        return result;
    }
}
