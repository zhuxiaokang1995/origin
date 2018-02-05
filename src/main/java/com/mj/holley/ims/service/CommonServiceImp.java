package com.mj.holley.ims.service;

import com.mj.holley.ims.web.rest.Constants.WebRestConstants;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jws.WebService;

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

	@Override
	public String sayHello(String name) {
        redisService.saveValue(WebRestConstants.MES_LINE_STOP, name);
		return "Hello 99999," + name;
	}

	@Override
	public String sayHelloLove(String name){
		return "LOVE Hello 8888," + name;
	}


}
