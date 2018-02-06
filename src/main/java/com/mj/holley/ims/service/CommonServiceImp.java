package com.mj.holley.ims.service;

import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.service.dto.MesOrderInfoDto;
import com.mj.holley.ims.service.dto.MesReturnDto;
import com.mj.holley.ims.web.rest.Constants.WebRestConstants;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RedisService redisService;

    @Inject
    private MesSubmitService mesSubmitService;

	@Override
	public String sayHello(String name) {
        redisService.saveValue(WebRestConstants.MES_LINE_STOP, name);
		return "Hello 99999," + name;
	}

	@Override
	public String sayHelloLove(String name){
		return "LOVE Hello 8888," + name;
	}

    @Override
    public String testMesOrder(MesOrderInfoDto mesOrderInfoDto){

        return mesSubmitService.saveMesOrder(mesOrderInfoDto).toString();
    }

    @Override
    public String receiveMesOrders(String mes){
        MesOrderInfoDto mesOrderInfoDto = MesSubmitService.transStringToDto(mes);
        return mesSubmitService.saveMesOrder(mesOrderInfoDto).toString();
    }
}
