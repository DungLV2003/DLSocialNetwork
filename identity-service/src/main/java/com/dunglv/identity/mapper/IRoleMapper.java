package com.dunglv.identity.mapper;

import com.dunglv.identity.dto.request.RoleRequest;
import com.dunglv.identity.dto.response.RoleResponse;
import com.dunglv.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
