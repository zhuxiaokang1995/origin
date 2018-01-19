package com.mj.holley.ims.service;


import com.mj.holley.ims.domain.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liumin
 */

@Slf4j
@Service
public class RedisService {

    @Inject
    private RedisTemplate<String, Object> redisTemplate;

    public void saveValue(String key, String value) {
        log.debug("Save data to redis, key:{}, value:{}", key, value);
        redisTemplate.opsForValue().set(key, value);
    }

    public void saveObject(String key, Object object) {
        log.debug("Save data to redis, key:{}, value:{}", key, object);
        redisTemplate.opsForValue().set(key, object);
    }

    public Object readObject(String key) {
        log.debug("Read data from redis, key:{}", key);
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteObject(String key) {
        log.debug("Delete data from redis, key:{}", key);
        redisTemplate.delete(key);
    }

    public void deleteObjects(Collection<String> keys) {
        log.debug("Delete data from redis, keys:", keys);
        redisTemplate.delete(keys);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void expireKey(String key, Long times, TimeUnit unit) {
        log.debug("expire key in redis, key:{}", key);
        redisTemplate.expire(key, times, unit);
    }

    /**
     * 给指定的key设置一个失效的时间点
     * 注意time的输入格式(24小时制 格式 HH:mm:ss)
     *
     * @param key
     * @param time
     */
    public void expireAtSchedTime(String key, String time) {
        Long now = ZonedDateTime.now(TimeZone.ASIA_SHANGHAI.getId()).toEpochSecond();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(LocalDate.now(TimeZone.ASIA_SHANGHAI.getId()) + " " + time);
            Long existSeconds = (date.getTime() / 1000 - now);
            if (existSeconds < 0) {
                date = format.parse(LocalDate.now(TimeZone.ASIA_SHANGHAI.getId()).plusDays(1) + " " + time);
                existSeconds = (date.getTime() / 1000 - now);
            }
            redisTemplate.expire(key, existSeconds, TimeUnit.SECONDS);
        } catch (ParseException e) {
            log.error("redis expire parse time error: {}", e);
        }
    }

    // 自加１
    public void incr(String key) {
        log.debug("increase a key value : ", key);
        redisTemplate.opsForValue().increment(key, 1);
    }

    public void saveList(String key, List<?> list) {
        log.debug("save a list in redis, key:{}", key);
        redisTemplate.delete(key);
        for (Object l : list) {
            redisTemplate.opsForList().rightPush(key, l);
        }
    }

    /**
     * 读取全部list元素
     *
     * @param key
     * @return
     */
    public List<?> readList(String key) {
        log.debug("read a list from redis, key:{}", key);
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 获得list第一个元素
     *
     * @param key
     * @return
     */
    public Object readFirstFromList(String key) {
        if (hasKey(key)) {
            return redisTemplate.opsForList().range(key, 0, 0).get(0);
        } else {
            return null;
        }
    }

    /**
     * 放入队列的最后
     *
     * @param key
     * @param value
     */
    public void pushEnd(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 取队列里面最后一个元素
     *
     * @param key
     * @return
     */
    public Object popEnd(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 放入队列的第一个位置
     *
     * @param key
     * @param value
     */
    public void pushFirst(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 取队列里面的第一个元素
     *
     * @param key
     * @return
     */
    public Object popFirst(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }
}
