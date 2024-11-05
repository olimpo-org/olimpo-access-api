package com.example.olimpoapi.repository;

import com.example.olimpoapi.model.postgresql.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    @Procedure(procedureName = "insert_customer")
    void insertCustomer(
            @Param("p_email") String email,
            @Param("p_password") String password,
            @Param("p_name") String name,
            @Param("p_surname") String surname,
            @Param("p_cpf") String cpf,
            @Param("p_gender_name") String genderName,
            @Param("p_interest_name") String interestName,
            @Param("p_profile_image") String profileImage
    );

    @Procedure(procedureName = "update_customer")
    void updateCustomer(
            @Param("p_customer_id") Integer customerId,
            @Param("p_email") String email,
            @Param("p_name") String name,
            @Param("p_surname") String surname,
            @Param("p_cpf") String cpf,
            @Param("p_gender_name") String genderName,
            @Param("p_profile_image") String profileImage
    );

    @Procedure(procedureName = "delete_customer")
    void deleteCustomer(@Param("p_customer_id") Integer customerId);
}
