package com.example.olimpoapi.model.redis;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@RedisHash("solicitation")
public class Solicitation {
    @Id
    private UUID id;
    private UUID communityId;
    private UUID userId;
    private String userName;
    private String userUrlImage;
    @TimeToLive
    private Long expirationTime;

    public Solicitation(
            UUID id,
            UUID communityId,
            UUID userId,
            String userName,
            String userUrlImage,
            Long expirationTime
    ) {
        this.id = id;
        this.communityId = communityId;
        this.userId = userId;
        this.userName = userName;
        this.userUrlImage = userUrlImage;
        this.expirationTime = expirationTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCommunityId() {
        return communityId;
    }

    public void setCommunityId(UUID communityId) {
        this.communityId = communityId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUrlImage() {
        return userUrlImage;
    }

    public void setUserUrlImage(String userUrlImage) {
        this.userUrlImage = userUrlImage;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
