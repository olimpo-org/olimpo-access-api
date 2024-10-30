package com.example.olimpoapi.service;

import com.example.olimpoapi.config.exception.ExceptionThrower;
import com.example.olimpoapi.model.postgresql.Community;
import com.example.olimpoapi.model.postgresql.CommunityUser;
import com.example.olimpoapi.model.postgresql.User;
import com.example.olimpoapi.model.redis.Solicitation;
import com.example.olimpoapi.model.utils.CommunityUserId;
import com.example.olimpoapi.repository.CommunityRepository;
import com.example.olimpoapi.repository.CommunityUserRepository;
import com.example.olimpoapi.repository.SolicitationRepository;
import com.example.olimpoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityUserRepository communityUserRepository;
    private final UserRepository userRepository;
    private final SolicitationRepository solicitationRepository;
    @Autowired
    private CommunityService(
            CommunityRepository communityRepository,
            CommunityUserRepository communityUserRepository,
            UserRepository userRepository,
            SolicitationRepository solicitationRepository
    ) {
        this.communityRepository = communityRepository;
        this.communityUserRepository = communityUserRepository;
        this.userRepository = userRepository;
        this.solicitationRepository = solicitationRepository;
    }

    public Community save(Community community) {
        return communityRepository.save(community);
    }

    public List<Community> getAll() {
        try {
            List<Community> communities = communityRepository.findAll();
            if (communities.isEmpty()) {
                ExceptionThrower.throwNotFoundException("Communities not found");
            }
            return communities;
        }catch (Exception e) {
            ExceptionThrower.throwBadRequestException(e.getMessage());
        }
        return null;
    }

    public Community findById(UUID id) {
        Optional<Community> community = communityRepository.findById(id);
        if(community.isEmpty()) {
            ExceptionThrower.throwNotFoundException("Community not found");
        }
        return community.get();
    }

    public boolean verifyIfCommunityExists(UUID id) {
        Optional<Community> community = communityRepository.findById(id);
        return community.isPresent();
    }
    public CommunityUser addUserToCommunity(UUID customerId, UUID communityId) {
        CommunityUserId communityUserId = new CommunityUserId(customerId, communityId);
        CommunityUser communityUser = new CommunityUser(communityUserId);
        return communityUserRepository.save(communityUser);
    }

    public CommunityUser removeUserFromCommunity(UUID customerId, UUID communityId) {
        CommunityUserId communityUserId = new CommunityUserId(customerId, communityId);
        CommunityUser communityUser = communityUserRepository
                .findCommunityUserById(communityUserId);
        if (communityUser == null) {
            ExceptionThrower.throwNotFoundException("CommunityUser not found");
        }
        communityUserRepository.delete(communityUser);
        return communityUser;
    }
    public boolean verifyIfUserIsInCommunity(UUID customerId, UUID communityId) {
        CommunityUserId communityUserId = new CommunityUserId(customerId, communityId);
        CommunityUser communityUser = communityUserRepository.findCommunityUserById(communityUserId);
        return communityUser != null;
    }

    public List<User> getAllUsersByCommunityId(UUID communityId) {
        if (!verifyIfCommunityExists(communityId)) ExceptionThrower.throwNotFoundException("Community not found");
        List<CommunityUser> communityUsers = communityUserRepository
                .findAllByIdCommunityId(communityId);
        if (communityUsers.isEmpty()) {
            ExceptionThrower.throwNotFoundException("CommunityUsers not found");
        }
        List<User> users = new ArrayList<>();
        for (CommunityUser communityUser : communityUsers) {
            Optional<User> user = userRepository.findById(communityUser.getId().getCustomerId());
            if (user.isPresent()) {
                user.get().setPassword(null);
                users.add(user.get());
            }

        }
        return users;
    }

    public List<Community> getAllCommunitiesByUserId(UUID customerId) {
        List<CommunityUser> communityUsers = communityUserRepository
                .findAllByIdCustomerId(customerId);
        if (communityUsers.isEmpty()) {
            ExceptionThrower.throwNotFoundException("CommunityUsers not found");
        }
        List<Community> communities = new ArrayList<>();
        for (CommunityUser communityUser : communityUsers) {
            Optional<Community> community = communityRepository.findById(communityUser.getId().getCommunityId());
            community.ifPresent(communities::add);
        }
        return communities;
    }

    public List<Solicitation> getAllSolicitationsByCommunityId(String communityId) {
        List<Solicitation> o = solicitationRepository
                .getAllByCommunityId(communityId);
        if (o == null) {
            ExceptionThrower.throwNotFoundException("Solicitation not found");
        }
        return o;
    }
    public Solicitation createSolicitation(Solicitation solicitation) {
        solicitationRepository.save(solicitation);
        return solicitation;
    }

    public void acceptSolicitation(Long solicitationId) {
        Solicitation solicitation = solicitationRepository
                .findById(solicitationId);
        if (solicitation == null) {
            ExceptionThrower.throwNotFoundException("Solicitation not found");
        }
        addUserToCommunity(
                solicitation.getUserId(),
                solicitation.getCommunityId()
        );
        solicitationRepository.deleteById(solicitation.getId());
    }

    public void rejectSolicitation(Long solicitationId) {
        Solicitation solicitation = solicitationRepository
                .findById(solicitationId);
        if (solicitation == null) {
            ExceptionThrower.throwNotFoundException("Solicitation not found");
        }
        solicitationRepository.deleteById(solicitation.getId());
    }
}
