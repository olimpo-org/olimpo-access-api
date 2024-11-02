package com.example.olimpoapi.service;

import com.example.olimpoapi.config.exception.ExceptionThrower;
import com.example.olimpoapi.model.postgresql.Administrator;
import com.example.olimpoapi.model.postgresql.User;
import com.example.olimpoapi.model.utils.Login;
import com.example.olimpoapi.repository.AdministratorRepository;
import com.example.olimpoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AdministratorRepository administratorRepository;
    @Autowired
    private UserService(
            UserRepository userRepository,
            AdministratorRepository administratorRepository
    ) {
        this.userRepository = userRepository;
        this.administratorRepository = administratorRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User login(Login login){
        User dbUser = findByEmailWithPassword(login.getEmail());
        if(!dbUser.getPassword().equals(login.getPassword())) {
            ExceptionThrower.throwBadRequestException("Wrong password");
        }
        return dbUser;
    }

    public User update(UUID id, User user) {
        Optional<User> dbUser = userRepository.findById(id);
        if(dbUser.isEmpty()) {
            ExceptionThrower.throwNotFoundException("User not found");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            ExceptionThrower.throwNotFoundException("User not found");
        }
        userRepository.delete(user.get());
    }

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(null));
        if(users.isEmpty()) {
            ExceptionThrower.throwNotFoundException("Users not found");
        }
        return users;
    }
    public User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            ExceptionThrower.throwNotFoundException("User not found");
        }
        user.get().setPassword(null);
        return user.get();
    }
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            ExceptionThrower.throwNotFoundException("User not found");
        }
        user.get().setPassword(null);
        return user.get();
    }

    public User findByEmailWithPassword(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            ExceptionThrower.throwNotFoundException("User not found");
        }
        return user.get();
    }

    public User findByCpf(String cpf) {
        Optional<User> user = userRepository.findByCpf(cpf);
        if (user.isEmpty()) {
            ExceptionThrower.throwNotFoundException("User not found");
        }
        user.get().setPassword(null);
        return user.get();
    }

    public boolean verifyIfUserExistsById(UUID id) {
        return userRepository.existsById(id);
    }
    public boolean verifyIfUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public boolean verifyIfUserExistsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }

    public boolean verifyIfIsAdministrator(String customerCpf, UUID communityId) {
        Optional<Administrator> administrator = administratorRepository.findByCustomerCpfAndCommunityId(
                customerCpf, communityId
        );
        if (administrator.isEmpty()) {
            ExceptionThrower.throwNotFoundException("Administrator not found");
        }
        return true;
    }

    public Administrator grantAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }
}
