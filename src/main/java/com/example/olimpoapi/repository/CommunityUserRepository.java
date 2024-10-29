package com.example.olimpoapi.repository;

import com.example.olimpoapi.model.postgresql.CommunityUser;
import com.example.olimpoapi.model.utils.CommunityUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CommunityUserRepository extends JpaRepository<CommunityUser, CommunityUserId> {
    CommunityUser findCommunityUserById(CommunityUserId communityUserId);
    List<CommunityUser> findAllByIdCommunityId(Long communityId);

    List<CommunityUser> findAllByIdCustomerId(UUID userId);
}
