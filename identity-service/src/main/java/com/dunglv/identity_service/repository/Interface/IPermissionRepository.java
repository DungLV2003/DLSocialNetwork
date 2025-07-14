package com.dunglv.identity_service.repository.Interface;

import com.dunglv.identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, String> {

}
