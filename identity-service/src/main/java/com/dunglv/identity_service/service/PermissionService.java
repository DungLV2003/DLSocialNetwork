package com.dunglv.identity_service.service;

import com.dunglv.identity_service.dto.request.PermissionRequest;
import com.dunglv.identity_service.dto.response.PermissionResponse;
import com.dunglv.identity_service.entity.Permission;
import com.dunglv.identity_service.mapper.IPermissionMapper;
import com.dunglv.identity_service.repository.Interface.IPermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class PermissionService {

    IPermissionRepository permissionRepository;
    IPermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        if (permissions.isEmpty()) {
            return List.of();
        }
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public void delete(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }

}
