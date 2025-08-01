package com.dunglv.identity.controller;

import com.dunglv.identity.dto.request.RoleRequest;
import com.dunglv.identity.dto.response.ApiResponse;
import com.dunglv.identity.dto.response.RoleResponse;
import com.dunglv.identity.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create (@RequestBody RoleRequest request) {

        RoleResponse response = roleService.create(request);
        return ApiResponse.<RoleResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping
    ApiResponse<?> getAll() {
        var responses = roleService.getAll();
        return ApiResponse.<Object>builder()
                .result(responses)
                .build();
    }

    @DeleteMapping("/{roleName}")
    ApiResponse<Void> delete(@PathVariable String roleName) {
        roleService.delete(roleName);
        return ApiResponse.<Void>builder()
                .build();
    }
}
