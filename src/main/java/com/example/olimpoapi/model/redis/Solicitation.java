package com.example.olimpoapi.model.redis;

import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash("solicitation")
public class Solicitation {
    private Long id;
    private UUID communityId;
    private UUID userId;
    private String userName;
    private String userUrlImage;

    public Solicitation(
            Long id,
            UUID communityId,
            UUID userId,
            String userName,
            String userUrlImage
    ) {
        this.id = id;
        this.communityId = communityId;
        this.userId = userId;
        this.userName = userName;
        this.userUrlImage = userUrlImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
