package com.example.olimpoapi.model.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CommunityUserId implements Serializable {

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "community_id")
    private UUID communityId;

    public CommunityUserId() {
    }

    public CommunityUserId(UUID customerId, UUID communityId) {
        this.customerId = customerId;
        this.communityId = communityId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getCommunityId() {
        return communityId;
    }

    public void setCommunityId(UUID communityId) {
        this.communityId = communityId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommunityUserId)) return false;
        CommunityUserId that = (CommunityUserId) o;
        return customerId.equals(that.customerId) && communityId.equals(that.communityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, communityId);
    }
}

