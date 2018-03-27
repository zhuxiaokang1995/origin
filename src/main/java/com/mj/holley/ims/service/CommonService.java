package com.mj.holley.ims.service;

import com.mj.holley.ims.service.dto.MesOrderInfoDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 接口
 *
 * @author leftso
 *
 */
@WebService(name = "CommonService", // 暴露服务名称
		targetNamespace = "http://webservice.leftso.com/"// 命名空间,一般是接口的包名倒序
)
public interface CommonService {
	@WebMethod
	@WebResult(name = "String", targetNamespace = "")
	public String sayHello(@WebParam(name = "userName") String name);

	@WebMethod
    @WebResult(name = "String", targetNamespace = "")
	public String sayHelloLove(@WebParam(name = "userName") String name);

    @WebMethod
    @WebResult(name = "Orderinfo", targetNamespace = "")
    public String testMesOrder(@WebParam(name = "tem") MesOrderInfoDto mesOrderInfoDto);

    @WebMethod
    @WebResult(name = "Orderinfo", targetNamespace = "")
    public String receiveMesOrders(@WebParam(name = "tem") String mes);

    @WebMethod
    @WebResult(name = "TransportTask", targetNamespace = "")
    public String stationSendsTask(@WebParam(name = "tem") String wms);
}
