package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Administrator;
import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.Store;
import com.example.demo.repository.AdministratorRepository;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StoreRepository;

@Service
public class AdminService {

    @Autowired
    private AdministratorRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public void registerOrUpdateAdministrator(Administrator admin) {
        logger.debug("Starting registerOrUpdateAdministrator with admin: {}", admin);

        // パスワードのエンコード
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            logger.debug("Encoding password for admin: {}", admin.getEmail());
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }

        // ストアの保存
        if (admin.getStore() != null && admin.getStore().getId() == null) {
            logger.debug("Saving new store: {}", admin.getStore());
            Store savedStore = storeRepository.save(admin.getStore());
            admin.setStore(savedStore);
        }

        // 役割の設定
        if (admin.getRoles() == null || admin.getRoles().isEmpty()) {
            logger.debug("Setting default role for admin: {}", admin.getEmail());
            Role defaultRole = roleRepository.findByName("店長");
            Set<Role> roles = new HashSet<>();
            roles.add(defaultRole);
            admin.setRoles(roles);
        }

        adminRepository.save(admin);
        logger.debug("Administrator saved: {}", admin);
    }

    public List<Administrator> getAllAdmins() {
        logger.debug("Fetching all administrators");
        return adminRepository.findAll();
    }

    public Administrator getAdminById(Long id) {
        logger.debug("Fetching administrator with id: {}", id);
        return adminRepository.findById(id).orElse(null);
    }

    public void deleteAdminById(Long id) {
        logger.debug("Deleting administrator with id: {}", id);
        adminRepository.deleteById(id);
    }

    public List<Role> getAllRoles() {
        logger.debug("Fetching all roles");
        return roleRepository.findAll();
    }

    public List<Permission> getAllPermissions() {
        logger.debug("Fetching all permissions");
        return permissionRepository.findAll();
    }

    public boolean permissionExistsById(Long id) {
        return permissionRepository.existsById(id);
    }

    public Store saveStore(Store store) {
        logger.debug("Saving store: {}", store);
        return storeRepository.save(store);
    }

    public Administrator findByEmail(String email) {
        logger.debug("Fetching administrator with email: {}", email);
        return adminRepository.findByEmail(email).orElse(null);
    }
}
