package com.example.demo.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Administrator;
import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.Store;
import com.example.demo.repository.AdministratorRepository;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

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

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
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

    @Override
    public List<Administrator> getAllAdmins() {
        logger.debug("Fetching all administrators");
        return adminRepository.findAll();
    }

    @Override
    public Administrator getAdminById(Long id) {
        logger.debug("Fetching administrator with id: {}", id);
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAdminById(Long id) {
        logger.debug("Deleting administrator with id: {}", id);
        adminRepository.deleteById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        logger.debug("Fetching all roles");
        return roleRepository.findAll();
    }

    @Override
    public List<Permission> getAllPermissions() {
        logger.debug("Fetching all permissions");
        return permissionRepository.findAll();
    }

    @Override
    public boolean permissionExistsById(Long id) {
        return permissionRepository.existsById(id);
    }

    @Override
    public Store saveStore(Store store) {
        logger.debug("Saving store: {}", store);
        return storeRepository.save(store);
    }

    @Override
    public Administrator findByEmail(String email) {
        logger.debug("Fetching administrator with email: {}", email);
        return adminRepository.findByEmail(email).orElse(null);
    }
}
