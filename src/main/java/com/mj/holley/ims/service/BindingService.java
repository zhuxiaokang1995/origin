package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.ProcessControl;
import com.mj.holley.ims.domain.Sn;
import com.mj.holley.ims.repository.ProcessControlRepository;
import com.mj.holley.ims.repository.SnRepository;
import com.mj.holley.ims.service.dto.BindingDto;
import com.mj.holley.ims.service.dto.MesReturnDto;
import com.mj.holley.ims.service.dto.ScanningResgistrationDTO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public BindingDto transStringToBindingDto(String input){
        BindingDto bindingDto = new BindingDto();
        JSONObject jsonObject = JSONObject.fromObject(input);
        Map<String, Object> map = (Map<String, Object>) jsonObject;
        Boolean boo = map.containsKey("OpeType") && map.containsKey("SerialNumber")
            && map.containsKey("OrderID") && map.containsKey("HutID")
            && map.containsKey("StationID");
        if(boo){
            bindingDto.setOpeType(Integer.parseInt(map.get("OpeType").toString()));
            bindingDto.setSerialNumber(map.get("SerialNumber").toString());
            bindingDto.setOrderID(map.get("OrderID").toString());
            bindingDto.setHutID(map.get("HutID").toString());
            bindingDto.setStationID(map.get("StationID").toString());
        }
        return bindingDto;

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
     * @param bindingDto
     * @return
     */
    public MesReturnDto bingdingSn(BindingDto bindingDto){
        MesReturnDto result = null;
        if(bindingDto.getOpeType() == 1){
            Sn sn = new Sn();
            sn.setSerialNumber(bindingDto.getSerialNumber());
            sn.setHutID(bindingDto.getHutID());
            sn.setOrderID(bindingDto.getOrderID());
            sn.isBinding(true);
            snRepository.save(sn);
            result =  new MesReturnDto(Boolean.TRUE,"Success","绑定成功");
        }

        if(bindingDto.getOpeType() == 2){
            Sn sn = new Sn();
            sn.setSerialNumber(bindingDto.getSerialNumber());
            sn.setHutID(bindingDto.getHutID());
            sn.setOrderID(bindingDto.getOrderID());
            sn.isBinding(false);
            snRepository.save(sn);
            result = new MesReturnDto(Boolean.TRUE,"Success","解绑成功");
        }
       return result;

    }

}
