package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mj.holley.ims.config.httpClient.HttpTemplateMes;
import com.mj.holley.ims.service.*;
import com.mj.holley.ims.service.dto.MesLineStopDto;
import com.mj.holley.ims.service.dto.ScanningResgistrationDTO;
import com.mj.holley.ims.service.dto.WmsResultsReportedDTO;
import com.mj.holley.ims.service.dto.WmsTaskRequestDTO;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.*;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Created by Wanghui on 2017/4/20.
 */
@RestController
@RequestMapping("/api/test")
public class TestResource {


    @Inject
    private MessagePushService messagePushService;

    @Inject
    private OpcuaService opcuaService;

    @Inject
    private WmsTaskRequestService wmsTaskRequestService;

    @Inject
    private RedisService redisService;

    @Inject
    private WmsSubmitService wmsSubmitService;

    @Inject
    private MesSubmitService mesSubmitService;


    @Inject
    private HttpTemplateMes httpTemplateMes;

    @GetMapping("/messagepush/{message}")
    @Timed
    public boolean messagePush(@PathVariable String message) {
        messagePushService.sendMessage("hello", message);
//        messagePushService.sendMessageToAltitudeLogistics(message);
        return true;
    }

    //保存redis，value的数据类型为String
    @PostMapping("/saveRedis(value)")
    @Timed
    public void saveRedisValue(@RequestParam String key,
                               @RequestParam String value) {
        redisService.saveValue(key, value);
    }

    //保存redis，value的数据类型为Object
    @PostMapping("/saveRedis(object)")
    @Timed
    public void saveRedisObject(@RequestParam String key,
                                @RequestParam Object object) {
        redisService.saveObject(key, object);
    }

    //获取redis
    @GetMapping("/getRedis")
    @Timed
    public Object getRedisByKey(@RequestParam String key) {
        return redisService.readAndInc(key);
    }

    //删除redis的一对数据
    @DeleteMapping("/deleteRedis(Object)")
    @Timed
    public void deleteRedisObject(@RequestParam String key) {
        redisService.deleteObject(key);
    }

    //删除redis的所有数据
    @DeleteMapping("/deleteRedis(Objects)")
    @Timed
    public void deleteRedisObjects(@RequestParam Collection<String> keys) {
//        log.debug("REST request to test delete redis");
        redisService.deleteObjects(keys);
    }

    //判断redis是否有指定的key
    @GetMapping("/hasKey")
    @Timed
    public boolean hasKey(@RequestParam String key) {
//        log.debug("REST request to test redis hasKey");
        return redisService.hasKey(key);
    }

    //设定redis指定key的数据的失效时间
    @GetMapping("/expireAtSchedTime")
    @Timed
    public void expireAtSchedTime(@RequestParam String key, @RequestParam String time) {
//        log.debug("REST request to test redis expireAtSchedTime");
        redisService.expireAtSchedTime(key, time);
    }

    //指定的key加1
    @GetMapping("/increase1")
    @Timed
    public void incr1(@RequestParam String key) {
//        log.debug("REST request to test redis key incr1");
        redisService.incr(key);
    }

    //保存redis,value类型为集合List类型
    @PostMapping("/saveList")
    @Timed
    public void saveList(@RequestParam String key, @RequestParam List<?> list) {
//        log.debug("REST request to test redis save list");
        redisService.saveList(key, list);
    }

    //获取redis里指定key的list
    @GetMapping("/readList")
    @Timed
    public List<?> readList(@RequestParam String key) {
//        log.debug("REST request to test redis read list");
        return redisService.readList(key);
    }

    //获取redis里指定key的list的第一个数据
    @GetMapping("/readFirstList")
    @Timed
    public Object readFirstFromList(@RequestParam String key) {
//        log.debug("REST request to test redis read FirstFromList");
        return redisService.readFirstFromList(key);
    }

    //保存redis到队列的最后一位
    @PostMapping("/pushEnd")
    @Timed
    public void pushEnd(@RequestParam String key, @RequestParam Object value) {
//        log.debug("REST request to test redis pushEnd");
        redisService.pushEnd(key, value);
    }

    //读取队列的最后一位
    @GetMapping("/popEnd")
    @Timed
    public Object popEnd(@RequestParam String key) {
//        log.debug("REST request to test redis popEnd");
        return redisService.popEnd(key);
    }

    //保存redis到队列的第一位
    @PostMapping("/pushFirst")
    @Timed
    public void pushFirst(@RequestParam String key, @RequestParam Object value) {
//        log.debug("REST request to test redis pushEnd");
        redisService.pushFirst(key, value);
    }

    //读取队列的第一位
    @GetMapping("/popFirst")
    @Timed
    public Object popFirst(@RequestParam String key) {
//        log.debug("REST request to test redis popEnd");
        return redisService.popFirst(key);
    }

    @PostMapping("/testLineStop")
    @Timed
    public void mesLineStop(@RequestBody MesLineStopDto dto) throws IOException {
        mesSubmitService.submitLineStop(dto);
    }

    @PostMapping("/testTaskResult")
    @Timed
    public void wmsTaskResult(@RequestBody WmsResultsReportedDTO dto) throws IOException {
        wmsSubmitService.submitTaskExecutionResult(dto);
    }

    @PostMapping("/testTaskRequest")
    @Timed
    public void wmsTaskRequest(@RequestBody WmsTaskRequestDTO dto) throws IOException {
        wmsTaskRequestService.taskRequest(dto);
    }

    @PostMapping("/testScanningResgistration")
    @Timed
    public void mesScanningResgistration(@RequestBody ScanningResgistrationDTO dto) throws IOException {
        mesSubmitService.submitScanningRegistration(dto);
    }

    @PostMapping("/test111")
    @Timed
    public void test(@RequestBody ScanningResgistrationDTO sr) {
//        JaxWsDynamicClientFactory dcf =JaxWsDynamicClientFactory.newInstance();
//        org.apache.cxf.endpoint.Client client =dcf.createClient("http://60.191.107.133:38080/scadaservice/ScadaService.asmx?wsdl");
        //getUser 为接口中定义的方法名称 张三为传递的参数  返回一个Object数组
//        int pk = Integer.parseInt(redisService.readObject(WebRestConstants.MES_LINE_STOP).toString());
//        String ss = new String("{\"PK\":120,\"SerialNumber\":\"0001\",\"StationID\":\"1\",\"ScanType\":\"1\",\"DefectCode\":\"\",\"Result\":\"OK\",\"SubSns\":\"0001\"}");
        String ss = new String("{\"PK\":120,\"ErrorType\":2,\"OperationTime\": \"2017-12-12 16:04:12\",\"StationID\":\"1\"}");
        try {

//            Object[] objects = client.invoke("ScadaWip",new ScanningResgistrationDTO(pk,sr.getSerialNumber(),sr.getStationID(),sr.getScanType(),sr.getDefectCode(),sr.getResult(),sr.getSubSn())).toString());
//            //输出调用结果
//            System.out.println("*****"+objects[0].toString());


            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            org.apache.cxf.endpoint.Client client = dcf.createClient("http://60.191.107.133:38080/scadaservice/ScadaService.asmx?wsdl");
            Endpoint endpoint = client.getEndpoint();


            // Make use of CXF service model to introspect the existing WSDL
            ServiceInfo serviceInfo = endpoint.getService().getServiceInfos().get(0);
            QName bindingName = new QName("http://tempuri.org/", "ScadaServiceSoap");
            BindingInfo binding = serviceInfo.getBinding(bindingName);
            QName opName = new QName("http://tempuri.org/", "ScadaStopline");
            BindingOperationInfo boi = binding.getOperation(opName); // Operation name is
            BindingMessageInfo inputMessageInfo = null;
            if (!boi.isUnwrapped()) {
                //OrderProcess uses document literal wrapped style.
                inputMessageInfo = boi.getWrappedOperation().getInput();
            } else {
                inputMessageInfo = boi.getUnwrappedOperation().getInput();
            }

            List<MessagePartInfo> parts = inputMessageInfo.getMessageParts();
            MessagePartInfo partInfo = parts.get(0); // Input class is Order

            // Get the input class Order
            Class<?> orderClass = partInfo.getTypeClass();
            Object orderObject = orderClass.newInstance();

            // Populate the Order bean
            // Set customer ID, item ID, price and quantity
//            {"PK":120,
//                "SerialNumber":"0001",
//                "StationID":"1",
//                "ScanType":"1",
//                "DefectCode":"",
//                "Result":"OK",
//                "SubSns":"0001"

//            PropertyDescriptor custProperty = new PropertyDescriptor("InputValue", orderClass);
//            custProperty.getWriteMethod().invoke(orderObject, ss);
//            PropertyDescriptor pkProperty = new PropertyDescriptor("PK", orderClass);
//            pkProperty.getWriteMethod().invoke(orderObject, Integer.valueOf(100));
//            PropertyDescriptor itemProperty = new PropertyDescriptor("SerialNumber", orderClass);
//            itemProperty.getWriteMethod().invoke(orderObject, "I001");
//            PropertyDescriptor priceProperty = new PropertyDescriptor("StationID", orderClass);
//            priceProperty.getWriteMethod().invoke(orderObject, "0002");
//            PropertyDescriptor qtyProperty = new PropertyDescriptor("ScanType", orderClass);
//            qtyProperty.getWriteMethod().invoke(orderObject, "1");
//            PropertyDescriptor defProperty = new PropertyDescriptor("DefectCode", orderClass);
//            defProperty.getWriteMethod().invoke(orderObject, "");
//            PropertyDescriptor reProperty = new PropertyDescriptor("Result", orderClass);
//            reProperty.getWriteMethod().invoke(orderObject, "OK");
//            PropertyDescriptor subProperty = new PropertyDescriptor("SubSns", orderClass);
//            subProperty.getWriteMethod().invoke(orderObject, "1");
            // Invoke the processOrder() method and print the result
            // The response class is String

            Object[] result = client.invoke(opName, new Object[]{ss});
            System.out.println("The order ID is " + result[0]);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @PostMapping("/test222")
    @Timed
    public void test2(@RequestBody ScanningResgistrationDTO sr) throws IOException {
//        String url = httpTemplateMes.getMesApiHttpSchemeHierarchical();
        String a = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\n" +
            "   <soap:Header/>\n" +
            "   <soap:Body>\n" +
            "      <tem:ScadaWip>\n" +
            "         <!--Optional:-->\n" +
            "         <tem:param>{\"PK\":120,\n" +
            "\"SerialNumber\":\"0001\",\n" +
            "\"StationID\":\"1\",\n" +
            "\"ScanType\":\"1\",\n" +
            "\"DefectCode\":\"\",\n" +
            "\"Result\":\"OK\",\n" +
            "\"SubSns\":\"0001\"\n" +
            "}</tem:param>\n" +
            "      </tem:ScadaWip>\n" +
            "   </soap:Body>\n" +
            "</soap:Envelope>";
//        MesApiAccessResult result = httpTemplateMes.postForObject(url + "/scadaservice/ScadaService.asmx", a, MesApiAccessResult.class);
//        if (result.isSuccess()) System.out.println("MesLineStopDto[{}]{}提交成功");
//        else
//            System.out.println("MesLineStopDto[{}]{}提交失败，错误信息：{}");
//
        String urlPath = new String("http://60.191.107.133:38080/scadaservice/ScadaService.asmx");
        //String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));
        String param = a;
        //建立连接
        URL url = new URL(urlPath);
        HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
        //设置参数
        httpConn.setDoOutput(true); //需要输出
        httpConn.setDoInput(true); //需要输入
        httpConn.setUseCaches(false); //不允许缓存
        httpConn.setRequestMethod("POST"); //设置POST方式连接
        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "application/soap+xml");
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        //建立输入流，向指向的URL传入参数
        DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
        dos.writeBytes(param);
        dos.flush();
        dos.close();
        //获得响应状态
        int resultCode = httpConn.getResponseCode();
        if (HttpURLConnection.HTTP_OK == resultCode) {
            StringBuffer sb = new StringBuffer();
            String readLine = new String();

            BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
                }
            responseReader.close();
            String bbb = sb.toString();
            String ccc = bbb.substring(bbb.indexOf("<ScadaWipResult>")+16, bbb.lastIndexOf("</ScadaWipResult>"));
            System.out.println(ccc);
            }
    }

    @PostMapping("/test333")
    @Timed
    public void test3(@RequestBody ScanningResgistrationDTO sr) throws IOException {
//        String url = httpTemplateMes.getMesApiHttpSchemeHierarchical();
        String a = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <tem:ScadaStopline>\n" +
            "         <!--Optional:-->\n" +
            "         <tem:param>{\"PK\":120,\n" +
            "\"ErrorType\":2,\n" +
            "\"OperationTime\": \"2017-12-12 16:04:12\",\n" +
            "\"StationID\":\"1\"\n" +
            "}</tem:param>\n" +
            "      </tem:ScadaStopline>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";
//        MesApiAccessResult result = httpTemplateMes.postForObject(url + "/scadaservice/ScadaService.asmx", a, MesApiAccessResult.class);
//        if (result.isSuccess()) System.out.println("MesLineStopDto[{}]{}提交成功");
//        else
//            System.out.println("MesLineStopDto[{}]{}提交失败，错误信息：{}");
//
        String urlPath = new String("http://60.191.107.133:38080/scadaservice/ScadaService.asmx");
        //String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));
        String param = a;
        //建立连接
        URL url = new URL(urlPath);
        HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
        //设置参数
        httpConn.setDoOutput(true); //需要输出
        httpConn.setDoInput(true); //需要输入
        httpConn.setUseCaches(false); //不允许缓存
        httpConn.setRequestMethod("POST"); //设置POST方式连接
        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "text/xml");
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        //建立输入流，向指向的URL传入参数
        DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
        dos.writeBytes(param);
        dos.flush();
        dos.close();
        //获得响应状态
        int resultCode = httpConn.getResponseCode();
        if (HttpURLConnection.HTTP_OK == resultCode) {
            StringBuffer sb = new StringBuffer();
            String readLine = new String();

            BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            String bbb = sb.toString();
            String ccc = bbb.substring(bbb.indexOf("<ScadaStoplineResult>")+21, bbb.lastIndexOf("</ScadaStoplineResult>"));
            System.out.println(ccc);
        }
    }
}
