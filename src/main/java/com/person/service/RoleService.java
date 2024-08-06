package com.person.service;

import com.person.entity.Role;
import com.person.request.RoleRequest;

import java.util.List;

public interface RoleService {
    Object saveRoleDetails(RoleRequest roleRequest);

    List<Role> getRoleList();

    Object getRoleDataById(Integer id);

    Object deleteRole(Integer id);

    Object updateRoleData(RoleRequest roleRequest);
}
