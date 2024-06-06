package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Permission;

public interface PermissionService {
    List<Permission> getAllPermissions();
    Permission getPermissionById(Long id);
    Permission createPermission(Permission permission);
    Permission updatePermission(Permission permission);
    void deletePermission(Long id);
    boolean permissionExistsById(Long id);
}
