package com.tomo.service;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisClient {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public <T> List<T> getValuesByKeys(String hashName, List<String> keys, Class<T> clazz) {
        HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
        List<String> resultStr = hashOps.multiGet(hashName, keys);
        if (clazz == String.class) {
            return (List<T>) resultStr;
        }
        return resultStr.stream().map(k -> JsonService.fromJson(k, clazz)).toList();
    }


    // 向 Redis 的 Hash 结构中添加元素
    public <E> void hset(String hashKey, String field, E value) {
        if (value instanceof String str) {
            stringRedisTemplate.opsForHash().put(hashKey, field, str);
        } else {
            stringRedisTemplate.opsForHash().put(hashKey, field, JsonService.toJson(value));
        }
    }

    // 从 Redis 的 Hash 结构中删除元素
    public void hdel(String hashKey, String field) {
        stringRedisTemplate.opsForHash().delete(hashKey, field);
    }

    public <E> E hget(String hashKey, String field, Class<E> clazz) {
        String value = (String) stringRedisTemplate.opsForHash().get(hashKey, field);
        if (value == null) {
            return null;
        }
        if (clazz == String.class) {
            return (E) value;
        }
        return JsonService.fromJson(value, clazz);
    }

    public <E> void set(String key, E value, long timeout, TimeUnit unit) {
        if (value instanceof String str) {
            stringRedisTemplate.opsForValue().set(key, str, timeout, unit);
        } else {
            stringRedisTemplate.opsForValue().set(key, JsonService.toJson(value), timeout, unit);
        }
    }


    public <E> Boolean setIfPresent(String key, E value, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForValue().setIfPresent(key, JsonService.toJson(value), timeout, unit);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public <E> E get(String key, Class<E> klass) {
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s == null) {
            return null;
        }
        if (klass == String.class) {
            return (E) s;
        } else {
            return JsonService.fromJson(s, klass);
        }
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
    }

    // TODO YUKINO 2024/11/20: 考虑分页
    public List<String> multiGet(Collection<String> keys) {
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    public void multiSet(Map<String, String> map, long expireSeconds) {
        stringRedisTemplate.opsForValue().multiSet(map);
        stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            map.keySet().forEach((key) -> {
                connection.expire(key.getBytes(), expireSeconds);
            });
            return null;
        });
    }

    public Boolean expireKey(String key, long expireTime, TimeUnit timeUnit) {
        return stringRedisTemplate.expire(key, expireTime, timeUnit);
    }
}