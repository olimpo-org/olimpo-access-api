package com.example.olimpoapi.service;

import com.example.olimpoapi.config.exception.ExceptionThrower;
import com.example.olimpoapi.model.postgresql.Admin;
import com.example.olimpoapi.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin create(Admin admin) {
        List<Admin> admins = adminRepository.findAll();
        admin.setId(admins.size() + 1);
        return adminRepository.save(admin);
    }

    public Admin update(Integer id, Admin admin) {
        try {
            Admin existingAdmin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
            existingAdmin.setUsername(admin.getUsername());
            existingAdmin.setPassword(admin.getPassword());
            return adminRepository.save(existingAdmin);
        } catch (Exception e) {
            ExceptionThrower.throwNotFoundException("Admin not found");
        }
        return null;
    }

    public void delete(Integer id) {
        try {
            adminRepository.deleteById(id);
        } catch (Exception e) {
            ExceptionThrower.throwNotFoundException("Admin not found");
        }
    }

    public Admin verify(String username, String password) {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return admin.get();
        } else {
            ExceptionThrower.throwBadRequestException("Invalid username or password");
        }
        return null;
    }
}
