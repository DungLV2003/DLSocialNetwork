package com.dunglv.identity.service;

import com.dunglv.identity.dto.request.RoleRequest;
import com.dunglv.identity.dto.response.RoleResponse;
import com.dunglv.identity.mapper.IRoleMapper;
import com.dunglv.identity.repository.Interface.IPermissionRepository;
import com.dunglv.identity.repository.Interface.IRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class RoleService {
    IRoleRepository roleRepository;
    IPermissionRepository permissionRepository;
    IRoleMapper roleMapper;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            return List.of();
        }
        return roles.stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }
    public void delete(String roleName) {
        roleRepository.deleteById(roleName);
    }

}
