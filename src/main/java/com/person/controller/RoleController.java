package com.person.controller;

import com.person.constants.Constants;
import com.person.constants.ErrorConstants;
import com.person.entity.Role;
import com.person.request.RoleRequest;
import com.person.response.Response;
import com.person.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/role/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private final RoleService roleService;

    @PostMapping("saveRole")
    public ResponseEntity<Response> saveRoleData(@RequestBody RoleRequest roleRequest) {
        Object data = roleService.saveRoleDetails(roleRequest);
        if(ObjectUtils.isEmpty(data)) {
            log.debug("Error occurred while saving role details");
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_SAVED_ERROR));
        }
        else if(data.equals(ErrorConstants.ROLE_EXISTS)) {
            log.debug("Role already exists and the given role is :{}", roleRequest.getName());
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_EXISTS));
        }
        log.debug("Role details saved successfully");
        return ResponseEntity.ok(new Response<>(true, Constants.ROLE_DETAILS_SAVED, data));
    }

    @GetMapping("getRole")
    public ResponseEntity<Response> getRoleDetails() {
        List<Role> data = roleService.getRoleList();
        if(CollectionUtils.isEmpty(data)) {
            log.debug("Role details list is empty");
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_LIST_EMPTY));
        }
        log.debug("Role details list fetched successfully");
        return ResponseEntity.ok(new Response<>(true, Constants.ROLE_LIST_FETCHED, data));
    }

    @GetMapping("getRoleById")
    public ResponseEntity<Response> getRoleDataById(@RequestParam Integer id) {
        Object data = roleService.getRoleDataById(id);
        if(data.equals(ErrorConstants.ROLE_NOT_FOUND)) {
            log.debug("Role details not found for this given id :{}", id);
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_NOT_FOUND));
        }
        log.debug("Role details fetched successfully for this given id :{}", id);
        return ResponseEntity.ok(new Response<>(true, Constants.ROLE_DETAILS_FETCHED, data));
    }

    @DeleteMapping("deleteRole")
    public ResponseEntity<Response> deleteRoleData(@RequestParam Integer id) {
        Object data = roleService.deleteRole(id);
        if (data.equals(ErrorConstants.ROLE_NOT_FOUND)) {
            log.debug("Role data not found for this given id :{}", id);
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_NOT_FOUND));
        }
        log.debug("Role data deleted successfully for this given id : {}", id);
        return ResponseEntity.ok(new Response<>(true, Constants.ROLE_DATA_DELETED, data));
    }

    @PutMapping("updateRole")
    public ResponseEntity<Response> updateRoleDetails(@RequestBody RoleRequest roleRequest) {
        Object data = roleService.updateRoleData(roleRequest);
        if(ObjectUtils.isEmpty(data)) {
            log.debug("Error occurred while updating  Role data");
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_UPDATE_ERROR));
        }
        else if(data.equals(ErrorConstants.ROLE_NOT_FOUND)) {
            log.debug("Role data not found for this given id :{}", roleRequest.getId());
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_NOT_FOUND));
        }
        else if(data.equals(ErrorConstants.ROLE_EXISTS)) {
            log.debug("Role already exists and the given role is :{}", roleRequest.getName());
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_EXISTS));
        }
        log.debug("Role data updated successfully for this given id :{}", roleRequest.getId());
        return ResponseEntity.ok(new Response<>(true, Constants.ROLE_DATA_UPDATED, data));
    }
}
