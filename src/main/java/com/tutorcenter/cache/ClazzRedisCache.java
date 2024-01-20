package com.tutorcenter.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.tutorcenter.model.Clazz;
import com.tutorcenter.repository.ClazzRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
public class ClazzRedisCache {
    private static final String HASH_KEY = "Clazz";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    private HashOperations<Object, String, Clazz> hashOperations;
    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    public ClazzRedisCache(RedisTemplate<Object, Object> redisTemplate, ClazzRepository clazzRepository) {
        this.redisTemplate = redisTemplate;
        // redisTemplate.setKeySerializer(new StringRedisSerializer());
        // redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        // redisTemplate.setValueSerializer(new StringRedisSerializer());
        this.hashOperations = redisTemplate.opsForHash();
        this.clazzRepository = clazzRepository;
        List<Clazz> listClazz = clazzRepository.findAll();
        Map<String, Clazz> mapClazz = new HashMap<>();
        for (Clazz clazz : listClazz) {
        mapClazz.put(String.valueOf(clazz.getId()), clazz);
        }
        saveAll(mapClazz);
    }

    public void save(Clazz c) {
        hashOperations.put(HASH_KEY, String.valueOf(c.getId()), c);
    }

    public void saveAll(Map<String, Clazz> clazzList) {
        hashOperations.putAll(HASH_KEY, clazzList);
    }

    public Map<String, Clazz> findAll() {
        return hashOperations.entries(HASH_KEY);
    }

    public Clazz findById(String id) {
        return hashOperations.get(HASH_KEY, id);
    }

    public void update(Clazz c) {
        save(c);
    }

    public void delete(String id) {
        hashOperations.delete(HASH_KEY, id);
    }

    // @PostConstruct
    // public void init() {
    // List<Clazz> listClazz = clazzRepository.findAll();
    // Map<String, Clazz> mapClazz = new HashMap<>();
    // for (Clazz clazz : listClazz) {
    // mapClazz.put(String.valueOf(clazz.getId()), clazz);
    // }
    // saveAll(mapClazz);
    // }
}
