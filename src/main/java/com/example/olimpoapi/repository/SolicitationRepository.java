package com.example.olimpoapi.repository;

import com.example.olimpoapi.model.redis.Solicitation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SolicitationRepository {

    private final RedisTemplate<String, Solicitation> redisTemplate;

    @Autowired
    public SolicitationRepository(RedisTemplate<String, Solicitation> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String HASH_NAME = "Solicitation";

    public Solicitation save(Solicitation solicitation) {
        redisTemplate.opsForHash().put(HASH_NAME, solicitation.getId().toString(), solicitation);
        return solicitation;
    }

    public List<Object> findAll() {
        return redisTemplate.opsForHash().values(HASH_NAME);
    }

    public Solicitation findById(UUID solicitationId) {
        return (Solicitation) redisTemplate.opsForHash().get(HASH_NAME, solicitationId.toString());
    }

    public void deleteById(UUID solicitationId) {
        redisTemplate.opsForHash().delete(HASH_NAME, solicitationId.toString());
    }
}
