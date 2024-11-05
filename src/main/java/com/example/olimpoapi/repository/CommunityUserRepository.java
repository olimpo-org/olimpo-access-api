package com.example.olimpoapi.repository;

import com.example.olimpoapi.model.postgresql.CommunityUser;
import com.example.olimpoapi.model.utils.CommunityUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.query.Procedure;

public interface CommunityUserRepository extends JpaRepository<CommunityUser, CommunityUserId> {
    CommunityUser findCommunityUserById(CommunityUserId communityUserId);
    List<CommunityUser> findAllByIdCommunityId(Integer communityId);

    List<CommunityUser> findAllByIdCustomerId(Integer userId);

    @Procedure(procedureName = "add_customer_to_community")
    void addCustomerToCommunity(@Param("p_customer_id") Integer customerId, @Param("p_community_id") Integer communityId);
}
