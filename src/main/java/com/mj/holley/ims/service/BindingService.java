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

import java.time.ZonedDateTime;
import java.util.List;
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
     * @param stationId
     */
    public void saveProcessControlInfo(Optional<Sn> sn , String stationId){
        if(sn != null && stationId!= null){
            ProcessControl processControl = new ProcessControl()
                .serialNumber(sn.get().getSerialNumber())
                .hutID(sn.get().getHutID())
                .orderID(sn.get().getOrderID())
                .stationID(stationId)
                .result("11111111")
                .mountGuardTime(ZonedDateTime.now());
            processControlRepository.save(processControl);
        }

    }


    /**
     * 绑定解绑
     * @param bindingDto
     * @return
     */
    public MesReturnDto bingdingSn(BindingDto bindingDto) {
        MesReturnDto result = null;
        List<Sn> snList = snRepository.findByHutIDAndSerialNumberAndIsBindingTrue(bindingDto.getHutID(), bindingDto.getSerialNumber());
        Optional<Sn> snOptional = snRepository.findOneBySerialNumber(bindingDto.getSerialNumber());



        //等于1 绑定
        if (bindingDto.getOpeType() == 1) {
            //判断hutId是否已经有绑定
            Sn sn1 = snRepository.findByHutIDAndIsBindingTrue(bindingDto.getHutID());
            if (sn1 == null) {
                if (snOptional.isPresent()) {
                    snRepository.updateSn(Boolean.TRUE, null, bindingDto.getHutID(), bindingDto.getOrderID(), ZonedDateTime.now(), bindingDto.getSerialNumber());
                } else {
                    Sn sn = new Sn();
                    sn.setSerialNumber(bindingDto.getSerialNumber());
                    sn.setHutID(bindingDto.getHutID());
                    sn.setOrderID(bindingDto.getOrderID());
                    sn.isBinding(Boolean.TRUE);
                    sn.setBindingTime(ZonedDateTime.now());
                    sn.setUnbundlingTime(null);
                    snRepository.save(sn);
                }
                result = new MesReturnDto(Boolean.TRUE, "Success", "绑定成功");
            } else {
                return new MesReturnDto(Boolean.TRUE,"fail","绑定失败,hutID已经绑定"+bindingDto.getHutID());
            }
        }
        //等于2 解绑
        if(bindingDto.getOpeType() == 2){
            if (snList.size() != 0){
                snRepository.updateIsBindingFalse(Boolean.FALSE ,ZonedDateTime.now() , bindingDto.getHutID() ,bindingDto.getSerialNumber());
                result = new MesReturnDto(Boolean.TRUE,"Success","解绑成功");
            }else {
                result = new MesReturnDto(Boolean.FALSE,"False","解绑失败");
            }

        }
        //其他情况发送错误信息
        if(bindingDto.getOpeType() != 2 && bindingDto.getOpeType() != 1){
            result = new MesReturnDto(Boolean.FALSE,"False","");
        }
       return result;

    }

}
