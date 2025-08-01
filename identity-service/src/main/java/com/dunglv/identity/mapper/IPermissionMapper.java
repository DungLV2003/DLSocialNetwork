package com.dunglv.identity.mapper;

import com.dunglv.identity.dto.request.PermissionRequest;
import com.dunglv.identity.dto.response.PermissionResponse;
import com.dunglv.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // This annotation allows Spring to manage this mapper as a bean
public interface IPermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
