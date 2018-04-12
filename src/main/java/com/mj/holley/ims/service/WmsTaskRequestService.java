package com.mj.holley.ims.service;

import com.mj.holley.ims.service.dto.WmsTaskRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by YXQ on 2018/4/12.
 */
@Slf4j
@Service
@Transactional
public class WmsTaskRequestService {

    @Inject
    private WmsSubmitService wmsSubmitService;

    public HashMap taskRequest(WmsTaskRequestDTO dto) throws IOException {
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
        HashMap result = wmsSubmitService.requestSoapServices(param, "text/xml");
        if (!result.get("resultCode").equals(200)){
            log.error("WmsResultsReportedDTO[{}]{}提交失败，错误信息：{}", dto);
        }else{
            log.info("WmsResultsReportedDTO[{}]{}提交成功", dto);
        }
        return result;
    }
}
