package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.ProcessControl;
import com.mj.holley.ims.domain.Sn;
import com.mj.holley.ims.repository.ProcessControlRepository;
import com.mj.holley.ims.repository.SnRepository;
import com.mj.holley.ims.service.dto.BingdingDto;
import com.mj.holley.ims.service.dto.MesReturnDto;
import com.mj.holley.ims.service.dto.ScanningResgistrationDTO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

/**
 * Created by vtstar on 2018/3/27.
 */
@Slf4j
@Service
@Transactional
public class BindingService {

    @Autowired
    private ProcessControlRepository processControlRepository;

    @Autowired
    private SnRepository snRepository;

    //字符转Dto
    public  BingdingDto transStringToBingdingDto(String input){
        BingdingDto bingdingDto = new BingdingDto();
        JSONObject jsonObject = JSONObject.fromObject(input);
        Map<String, Object> map = (Map<String, Object>) jsonObject;
        if (map.containsKey("BingdingDto")) {
            Map<String, Object> objectMap = (Map<String, Object>) map.get("BingdingDto");
            bingdingDto.setOpeType(Integer.parseInt(objectMap.get("opeType").toString()));
            bingdingDto.setSerialNumber(objectMap.get("serialNumber").toString());
            bingdingDto.setOrderID(objectMap.get("orderID").toString());
            bingdingDto.setHutID(objectMap.get("orderID").toString());
            bingdingDto.setStationID(objectMap.get("stationID").toString());
        }
        return  bingdingDto;
    }

    /**
     * 保存过站信息
     * @param sn
     * @param scanningResgistrationDTO
     */
    public void saveProcessControlInfo(Optional<Sn> sn , ScanningResgistrationDTO scanningResgistrationDTO){
        if(sn != null && scanningResgistrationDTO!= null){
            ProcessControl processControl = new ProcessControl()
                .serialNumber(sn.get().getSerialNumber())
                .hutID(sn.get().getHutID())
                .orderID(sn.get().getOrderID())
                .stationID(scanningResgistrationDTO.getStationID())
                .result("11111111");
            processControlRepository.save(processControl);
        }

    }


    /**
     * 绑定解绑
     * @param bingdingDto
     * @return
     */
    public MesReturnDto bingdingSn(BingdingDto bingdingDto ){
        MesReturnDto result = null;
        if(bingdingDto.getOpeType() == 1){
            Sn sn = new Sn();
            sn.setSerialNumber(bingdingDto.getSerialNumber());
            sn.setHutID(bingdingDto.getHutID());
            sn.setOrderID(bingdingDto.getOrderID());
            sn.isBinding(true);
            snRepository.save(sn);
            result =  new MesReturnDto(Boolean.TRUE,"Success","绑定成功");
        }

        if(bingdingDto.getOpeType() == 2){
            Sn sn = new Sn();
            sn.setSerialNumber(bingdingDto.getSerialNumber());
            sn.setHutID(bingdingDto.getHutID());
            sn.setOrderID(bingdingDto.getOrderID());
            sn.isBinding(false);
            snRepository.save(sn);
            result = new MesReturnDto(Boolean.TRUE,"Success","解绑成功");
        }
       return result;

    }

}
