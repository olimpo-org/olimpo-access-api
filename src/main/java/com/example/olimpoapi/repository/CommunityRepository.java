package com.example.olimpoapi.repository;

import com.example.olimpoapi.model.postgresql.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommunityRepository extends JpaRepository<Community, UUID> {
}
