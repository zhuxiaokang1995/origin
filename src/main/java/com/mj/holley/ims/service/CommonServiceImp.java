package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.repository.OrderInfoRepository;
import com.mj.holley.ims.repository.ProcessesRepository;
import com.mj.holley.ims.repository.RepeatProcessRepository;
import com.mj.holley.ims.repository.StepsRepository;
import com.mj.holley.ims.service.dto.BindingDto;
import com.mj.holley.ims.service.dto.MesOrderInfoDto;
import com.mj.holley.ims.service.dto.MesReturnDto;
import com.mj.holley.ims.service.dto.WmsTransportTaskDTO;
import com.mj.holley.ims.web.rest.Constants.WebRestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jws.WebService;
import java.util.List;

/**
 * 接口实现
 *
 * @author leftso
 *
 */

@WebService(serviceName = "CommonService", // 与接口中指定的name一致
		targetNamespace = "http://webservice.leftso.com/", // 与接口中的命名空间一致,一般是接口的包名倒
		endpointInterface = "com.mj.holley.ims.service.CommonService"// 接口地址
)
@Component
public class CommonServiceImp implements CommonService {

    @Inject
    private BindingService bindingService;

    @Inject
    private RedisService redisService;

    @Inject
    private MesSubmitService mesSubmitService;

    @Autowired
    private WmsSubmitService wmsSubmitService;

    @Inject
    private OrderInfoRepository orderInfoRepository;

    @Inject
    private ProcessesRepository processesRepository;

    @Inject
    private StepsRepository stepsRepository;

    @Inject
    private RepeatProcessRepository repeatProcessRepository;

    @Override
    public String receiveMesOrders(String mes){
        //soap webservice接收的String类型解析成对应的json对象
        MesOrderInfoDto mesOrderInfoDto = MesSubmitService.transStringToDto(mes);
        if (mesOrderInfoDto.getOrderInfo().getDepartID().equals("ASY")){   //ASY类型为组装车间生产订单
            List<OrderInfo> orderInfoList = orderInfoRepository.findAllByOrderID(mesOrderInfoDto.getOrderInfo().getOrderID());
            if (orderInfoList.size() != 0){                //当前下发的订单系统中已经存在则覆盖
                orderInfoList.forEach(order ->{
                    processesRepository.deleteByOrderInfo(order);   //删除之前存在的订单、及订单对应的数据
                    stepsRepository.deleteByOrderInfo(order);       //删除之前存在的订单、及订单对应的数据
                    repeatProcessRepository.deleteByOrderInfo(order);
                    orderInfoRepository.delete(order);              //删除之前存在的订单、及订单对应的数据
                    redisService.deleteObject(order.getOrderID());
                }
                    );
            }
            return mesSubmitService.saveMesOrder(mesOrderInfoDto).toString();
        }
        return new MesReturnDto(Boolean.TRUE,"Success","").toString();
    }

    @Override
    public String SendData(String wms){
        WmsTransportTaskDTO wmsTransportTaskDTO = WmsSubmitService.transportTaskToDTO(wms);
        return wmsSubmitService.saveWmsTransportTask(wmsTransportTaskDTO);
    }

    @Override
    public String bindingSn(String mes) {
        String result = null;
        //字符转换
        BindingDto bindingDto = bindingService.transStringToBindingDto(mes);
        // 绑定解绑
        MesReturnDto mesReturnDto = bindingService.bingdingSn(bindingDto);
        if (mesReturnDto != null){
            result =  mesReturnDto.toString();
        }
        return result;

    }
}
