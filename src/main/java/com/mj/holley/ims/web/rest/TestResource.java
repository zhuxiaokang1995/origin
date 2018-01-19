package com.mj.holley.ims.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.mj.holley.ims.service.MesSubmitService;
import com.mj.holley.ims.service.MessagePushService;
import com.mj.holley.ims.service.OpcuaService;
import com.mj.holley.ims.service.RedisService;
import com.mj.holley.ims.service.dto.MesLineStopDto;
import com.mj.holley.ims.domain.util.TimeZone;
import com.mj.holley.ims.web.rest.Constants.WebRestConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.*;

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
    private RedisService redisService;

    @Inject
    private MesSubmitService mesSubmitService;


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
        return redisService.readObject(key);
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

    @GetMapping("/testLineStop")
    @Timed
    public void mesLineStop(@RequestParam String SectionID) {
        redisService.incr(WebRestConstants.MES_LINE_STOP);
        int pk = Integer.parseInt(redisService.readObject(WebRestConstants.MES_LINE_STOP).toString());
        mesSubmitService.submitLineStop(new MesLineStopDto(pk, ZonedDateTime.now(TimeZone.ASIA_SHANGHAI.getId()), SectionID));
    }

}
