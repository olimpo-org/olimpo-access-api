package com.example.olimpoapi.model.postgresql;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "administrador")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "CustomerId cannot be null")
    @Column(name = "customer_id")
    private String customerCpf;

    @NotNull(message = "CommunityId cannot be null")
    @Column(name = "community_id")
    private UUID communityId;

    public Administrator() {
    }

    public Administrator(Long id, String customerCpf, UUID communityId) {
        this.id = id;
        this.customerCpf = customerCpf;
        this.communityId = communityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerCpf() {
        return customerCpf;
    }

    public void setCustomerCpf(String customerCpf) {
        this.customerCpf = customerCpf;
    }

    public UUID getCommunityId() {
        return communityId;
    }

    public void setCommunityId(UUID communityId) {
        this.communityId = communityId;
    }
}
