package com.example.olimpoapi.repository;

import com.example.olimpoapi.model.redis.Solicitation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SolicitationRepository extends CrudRepository<Solicitation, UUID> {
}
