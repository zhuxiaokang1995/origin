package com.mj.holley.ims.service;

import com.mj.holley.ims.config.httpClient.HttpTemplateMes;
import com.mj.holley.ims.config.httpClient.MesApiAccessResult;
import com.mj.holley.ims.domain.ScanningRegistration;
import com.mj.holley.ims.service.dto.MesLineStopDto;
import com.mj.holley.ims.service.dto.ScanningResgistrationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

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

    /**
     * 扫描组装登记缺陷
     * @param scanningResgistrationDTO
     */

    public void submitScanningRegistration (ScanningResgistrationDTO scanningResgistrationDTO ){
        String url = httpTemplateMes.getMesApiHttpSchemeHierarchical();
        MesApiAccessResult result = httpTemplateMes.postForObject(url + SCANNING_REGISTRATION, scanningResgistrationDTO, MesApiAccessResult.class);
        if (result.isSuccess()) {
            log.info("ScanningRegistration[{}]{}提交成功", scanningResgistrationDTO);
        }
        else {
            log.error("ScanningRegistration[{}]{}提交失败，错误信息：{}", scanningResgistrationDTO);
        }

    }

    public void submitLineStop(MesLineStopDto dto) {
        String url = httpTemplateMes.getMesApiHttpSchemeHierarchical();
        MesApiAccessResult result = httpTemplateMes.postForObject(url + SUBMIT_LINE_STOP, dto, MesApiAccessResult.class);
        if (result.isSuccess()) log.info("MesLineStopDto[{}]{}提交成功", dto.getPK(),dto.getOperationTime(),dto.getSectionID());
        else
            log.error("MesLineStopDto[{}]{}提交失败，错误信息：{}", dto.getPK(),dto.getOperationTime(),dto.getSectionID());
    }
}
