package com.person.controller;

import com.person.constants.Constants;
import com.person.constants.ErrorConstants;
import com.person.entity.Department;
import com.person.request.DepartmentRequest;
import com.person.response.Response;
import com.person.service.DepartmentService;
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
@RequestMapping("/department/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("saveDepartment")
    public ResponseEntity<Response> saveDepartmentDetails(@RequestBody DepartmentRequest departmentRequest) {
        Object data = departmentService.saveDepartmentDetails(departmentRequest);
        if(ObjectUtils.isEmpty(data)) {
            log.debug("Error occured while saving Department details");
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.DEPARTMENT_SAVED_ERROR));
        }
        else if(data.equals(ErrorConstants.DEPARTMENT_ALREADY_EXISTS)) {
            log.debug("Department details already exists and the given department name is :{}", departmentRequest.getName());
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.DEPARTMENT_ALREADY_EXISTS));
        }
        log.debug("Department details saved successfully");
        return ResponseEntity.ok(new Response<>(true, Constants.DEPARTMENT_DETAILS_SAVED, data));
    }

    @GetMapping("getDepartment")
    public ResponseEntity<Response> getDepartmentDetailsList() {
        List<Department> data = departmentService.getDepartmentDetailsList();
        if(CollectionUtils.isEmpty(data)) {
            log.debug("Department details list is empty");
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.DEPARTMENT_LIST_EMPTY));
        }
        log.debug("Department details list fetched successfully");
        return ResponseEntity.ok(new Response<>(true, Constants.DEPARTMENT_LIST_FETCHED, data));
    }

    @GetMapping("getDeptById")
    public ResponseEntity<Response> getDepartmentDataById(@RequestParam Integer id) {
        Object data = departmentService.getDepartmentById(id);
        if(data.equals(ErrorConstants.DEPARTMENT_NOT_FOUND)) {
            log.debug("Department details not found for this given id :{}",id);
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.DEPARTMENT_NOT_FOUND));
        }
        log.debug("Department details fetched successfully for this given id :{}",id);
        return ResponseEntity.ok(new Response<>(true, Constants.DEPARTMENT_DETAILS_FETCHED, data));
    }

    @DeleteMapping("deleteDept")
    public ResponseEntity<Response> deleteDepartmentDetails(@RequestParam Integer id) {
        Object data = departmentService.deleteDepartmentDetails(id);
        if(data.equals(ErrorConstants.DEPARTMENT_NOT_FOUND)) {
            log.debug("Department details not found for this given id :{}", id);
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.DEPARTMENT_NOT_FOUND));
        }
        log.debug("Department details deleted successfully for this given id :{}", id);
        return ResponseEntity.ok(new Response<>(true, Constants.DEPARTMENT_DETAILS_DELETED, data));
    }

    @PutMapping("updateDept")
    public ResponseEntity<Response> updateDepartmentDetails(@RequestBody DepartmentRequest departmentRequest) {
        Object data = departmentService.updateDepartmentDetails(departmentRequest);
        if(ObjectUtils.isEmpty(data)) {
            log.debug("Error occured while updating departmnt details");
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.DEPARTMENT_UPDATE_ERROR));
        }
        else if(data.equals(ErrorConstants.DEPARTMENT_NOT_FOUND)) {
            log.debug("Department not found for this given id :{}", departmentRequest.getId());
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
        }
        else if(data.equals(ErrorConstants.DEPARTMENT_ALREADY_EXISTS)) {
            log.debug("Department already exists and the given department is :{}", departmentRequest.getName());
            return ResponseEntity.ok(new Response<>(false, ErrorConstants.EMAIL_ALREADY_EXISTS));
        }
        log.debug("Department details updated successfully for this given id :{}",departmentRequest.getId());
        return ResponseEntity.ok(new Response<>(true, Constants.DEPARTMENT_DETAILS_UPDATED, data));
    }
}
