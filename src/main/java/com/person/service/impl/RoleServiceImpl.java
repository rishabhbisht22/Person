package com.person.service.impl;

import com.person.constants.ErrorConstants;
import com.person.entity.Role;
import com.person.repository.RoleRepository;
import com.person.request.RoleRequest;
import com.person.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Object saveRoleDetails(RoleRequest roleRequest) {
        Optional<Role> findRole = roleRepository.findByRoleName(roleRequest.getName());
        if(findRole.isEmpty()) {
              Role role = Role.builder()
                      .name(roleRequest.getName().toUpperCase())
                      .build();
              Role save = roleRepository.save(role);
              return save;
        }
        return ErrorConstants.ROLE_EXISTS;
    }

    @Override
    public List<Role> getRoleList() {
        List<Role> list = roleRepository.findAll();
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    @Override
    public Object getRoleDataById(Integer id) {
        Optional<Role> data = roleRepository.findById(id);
        if(data.isPresent()) {
            return data;
        }
        return ErrorConstants.ROLE_NOT_FOUND;
    }

    @Override
    public Object deleteRole(Integer id) {
        Optional<Role> findRole = roleRepository.findById(id);
        if(findRole.isPresent()) {

            roleRepository.deleteById(id);
            return id;
        }
        return ErrorConstants.ROLE_NOT_FOUND;
     }

    @Override
    public Object updateRoleData(RoleRequest roleRequest) {
        Optional<Role> findRole = roleRepository.findById(roleRequest.getId());
        if(findRole.isPresent()) {
            Optional<Role> roleName = roleRepository.findByRoleName(roleRequest.getName());
            if(roleName.isEmpty() || roleName.get().getId()== roleRequest.getId()) {
                 findRole.get().setName(roleRequest.getName());
                 Role update = roleRepository.save(findRole.get());
                 return update;
            }
            return ErrorConstants.ROLE_EXISTS;
        }
        return ErrorConstants.ROLE_NOT_FOUND;
    }
}
