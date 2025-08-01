package com.dunglv.identity.controller;

import com.dunglv.identity.dto.request.PermissionRequest;
import com.dunglv.identity.dto.response.ApiResponse;
import com.dunglv.identity.dto.response.PermissionResponse;
import com.dunglv.identity.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/permissions")
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        PermissionResponse response = permissionService.create(request);
        return ApiResponse.<PermissionResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        List<PermissionResponse> responses = permissionService.getAll();
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(responses)
                .build();
    }

    @DeleteMapping("/{permissionName}")
    ApiResponse<Void> deletePermission(@PathVariable String permissionName) {
        permissionService.delete(permissionName);
        return ApiResponse.<Void>builder()
                .build();
    }
}
