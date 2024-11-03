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
        try {
            if (user.getGenderId() == 1) {
                userRepository.insertCustomer(user.getEmail(), user.getPassword(), user.getName(), user.getSurname(), user.getCpf(), "Feminino", "Tecnologia", user.getProfileImage());
            } else if (user.getGenderId() == 2) {
                userRepository.insertCustomer(user.getEmail(), user.getPassword(), user.getName(), user.getSurname(), user.getCpf(), "Masculino", "Tecnologia", user.getProfileImage());
            } else {
                userRepository.insertCustomer(user.getEmail(), user.getPassword(), user.getName(), user.getSurname(), user.getCpf(), "Outro", "Tecnologia", user.getProfileImage());
            }
            Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
            if (dbUser.isEmpty()) {
                ExceptionThrower.throwNotFoundException("User not found");
            }
            dbUser.get().setPassword(null);
            return dbUser.get();
        } catch (Exception e) {
            ExceptionThrower.throwBadRequestException(e.getMessage());
            return null;
        }
    }

    public User login(Login login){
        User dbUser = findByEmailWithPassword(login.getEmail());
        if(!dbUser.getPassword().equals(login.getPassword())) {
            ExceptionThrower.throwBadRequestException("Wrong password");
        }
        return dbUser;
    }

    public User update(Integer id, User user) {
        try {
            if (user.getGenderId() == 1) {
                userRepository.updateCustomer(id, user.getEmail(), user.getName(), user.getSurname(), user.getCpf(), "Feminino", user.getProfileImage());
            } else if (user.getGenderId() == 2) {
                userRepository.updateCustomer(id, user.getEmail(), user.getName(), user.getSurname(), user.getCpf(), "Masculino", user.getProfileImage());
            } else {
                userRepository.updateCustomer(id, user.getEmail(), user.getName(), user.getSurname(), user.getCpf(), "Outro", user.getProfileImage());
            }
            Optional<User> dbUser = userRepository.findById(id);
            if (dbUser.isEmpty()) {
                ExceptionThrower.throwNotFoundException("User not found");
            } else {
                dbUser.get().setPassword(null);
                return dbUser.get();
            }
        } catch (Exception e) {
            ExceptionThrower.throwBadRequestException(e.getMessage());
            return null;
        }
        return null;
    }

    public void delete(Integer id) {
        userRepository.deleteCustomer(id);
    }

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(null));
        if(users.isEmpty()) {
            ExceptionThrower.throwNotFoundException("Users not found");
        }
        return users;
    }
    public User findById(Integer id) {
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

    public boolean verifyIfUserExistsById(Integer id) {
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
