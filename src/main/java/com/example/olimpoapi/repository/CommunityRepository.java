package com.example.olimpoapi.repository;

import com.example.olimpoapi.model.postgresql.Community;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.sql.Date;

public interface CommunityRepository extends JpaRepository<Community, Integer> {
    @Procedure(procedureName = "delete_community")
    void deleteCommunity(@Param("p_community_id") Integer communityId);

    @Procedure(procedureName = "insert_community")
    void insertCommunity(
            @Param("p_name") String name,
            @Param("p_date") Date date,
            @Param("p_image") String image,
            @Param("p_neighborhood") String neighborhood,
            @Param("p_customer_cpf") String customerCpf
    );

    @Procedure(procedureName = "update_community")
    void updateCommunity(
            @Param("p_community_id") Integer communityId,
            @Param("p_name") String name,
            @Param("p_date") Date date,
            @Param("p_image") String image
    );
}
