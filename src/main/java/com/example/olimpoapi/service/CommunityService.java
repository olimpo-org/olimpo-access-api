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
    private static final Long expirationTime = 259200L;

    @Autowired
    public CommunityService(
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

    public Community save(Community community, Integer userId) {
        communityRepository.insertCommunity(
                community.getName(),
                community.getStartDate(),
                community.getImageUrl(),
                community.getNeighborhood(),
                userId
        );
       List<Community> communities = communityRepository.findAll();
       return communities.get(communities.size() - 1);
    }

    public Community update(Integer id, Community community) {
        communityRepository.updateCommunity(
                id,
                community.getName(),
                community.getStartDate(),
                community.getImageUrl()
        );
        Optional<Community> updatedCommunity = communityRepository.findById(id);
        if (updatedCommunity.isEmpty()) {
            ExceptionThrower.throwNotFoundException("Community not found");
        }
        return updatedCommunity.get();
    }

    public void deleteById(Integer id) {
        communityRepository.deleteCommunity(id);
    }

    public List<Community> getAll() {
        List<Community> communities = communityRepository.findAll();
        if (communities.isEmpty()) {
            ExceptionThrower.throwNotFoundException("Communities not found");
        }
        return communities;
    }

    public Community findById(Integer id) {
        return communityRepository.findById(id)
                .orElseThrow();
    }

    public CommunityUser addUserToCommunity(Integer communityId, Integer customerId) {
        communityUserRepository.addCustomerToCommunity(customerId, communityId);
        return communityUserRepository
                .findCommunityUserById(new CommunityUserId(customerId, communityId));
    }

    public CommunityUser removeUserFromCommunity(Integer communityId, Integer customerId) {
        CommunityUserId communityUserId = new CommunityUserId(customerId, communityId);
        CommunityUser communityUser = communityUserRepository
                .findCommunityUserById(communityUserId);
        if (communityUser == null) {
            ExceptionThrower.throwNotFoundException("CommunityUser not found");
        }
        communityUserRepository.delete(communityUser);
        return communityUser;
    }

    public List<Community> getAllCommunitiesByUserId(Integer userId) {
        List<CommunityUser> communityUsers = communityUserRepository
                .findAllByIdCustomerId(userId);
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

    public List<Community> getAllCommunitiesThatUserIsNotMember(Integer userId) {
        try {
            List<Community> communityList = communityRepository.findAll();
            if (communityList.isEmpty()) {
                return new ArrayList<>();
            }
            List<CommunityUser> communityUsers = communityUserRepository
                    .findAllByIdCustomerId(userId);
            if (communityUsers.isEmpty()) {
                return communityList;
            }

            for (CommunityUser communityUser : communityUsers) {
                communityList.removeIf(community -> community.getId().equals(communityUser.getId().getCommunityId()));
            }
            return communityList;
        } catch (Exception e) {
            ExceptionThrower.throwNotFoundException("Community not found" + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<User> getAllUsersByCommunityId(Integer communityId) {
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

    public Solicitation createSolicitation(Solicitation solicitation) {
        solicitation.setId(UUID.randomUUID());
        solicitation.setExpirationTime(expirationTime);
        return solicitationRepository.save(solicitation);
    }

    public List<Solicitation> getAllSolicitationsByCommunityId(Integer communityId) {
        List<Object> solicitations = solicitationRepository.findAll();
        List<Solicitation> solicitationList = new ArrayList<>();
        for (Object object : solicitations) {
            if (object instanceof Solicitation solicitation) {
                if (solicitation.getCommunityId().equals(communityId)) {
                    solicitationList.add(solicitation);
                }
            }
        }
        if (solicitationList.isEmpty()) {
            ExceptionThrower.throwNotFoundException("Solicitation not found");
        }
        return solicitationList;
    }

    public List<Solicitation> getAllSolicitationsByUserId(Integer userId) {
        List<Object> solicitations = solicitationRepository.findAll();
        List<CommunityUser> communityUsers = communityUserRepository.findAllByIdCustomerId(userId);
        List<Solicitation> solicitationList = new ArrayList<>();
        for (Object object : solicitations) {
            if (object instanceof Solicitation solicitation) {
                for (CommunityUser communityUser : communityUsers) {
                    if (solicitation.getCommunityId().equals(communityUser.getId().getCommunityId())) {
                        solicitationList.add(solicitation);
                    }
                }
            }
        }
        if (solicitationList.isEmpty()) {
            ExceptionThrower.throwNotFoundException("Solicitation not found");
        }
        return solicitationList;
    }

    public void acceptSolicitation(UUID solicitationId) {
        Solicitation solicitation = solicitationRepository.findById(solicitationId);
        if (solicitation == null) {
            ExceptionThrower.throwNotFoundException("Solicitation not found");
        } else {
            communityUserRepository.addCustomerToCommunity(solicitation.getUserId(), solicitation.getCommunityId());
            solicitationRepository.deleteById(solicitationId);
        }
    }

    public void rejectSolicitation(UUID solicitationId) {
        try {
            solicitationRepository.deleteById(solicitationId);
        } catch (Exception e) {
            ExceptionThrower.throwNotFoundException("Solicitation not found");
        }
    }
}
