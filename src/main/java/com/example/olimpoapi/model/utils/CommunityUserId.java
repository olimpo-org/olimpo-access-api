package com.example.olimpoapi.model.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CommunityUserId implements Serializable {

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "community_id")
    private Integer communityId;

    public CommunityUserId() {
    }

    public CommunityUserId(Integer customerId, Integer communityId) {
        this.customerId = customerId;
        this.communityId = communityId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
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

