package com.person.service.impl;

import com.person.constants.ErrorConstants;
import com.person.controller.DepartmentController;
import com.person.entity.Department;
import com.person.repository.DepartmentRepository;
import com.person.request.DepartmentRequest;
import com.person.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepo;

    @Override
    public Object saveDepartmentDetails(DepartmentRequest departmentRequest) {

        List<Department> findDepartment = departmentRepo.findAll().stream().filter(check ->
                check.getName().equalsIgnoreCase(departmentRequest.getName())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(findDepartment)) {
             Department department = Department.builder()
                     .name(departmentRequest.getName())
                     .build();
             Department save = departmentRepo.save(department);
             return save;
        }
        return ErrorConstants.DEPARTMENT_ALREADY_EXISTS;
    }

    @Override
    public List<Department> getDepartmentDetailsList() {
        List<Department> department = departmentRepo.findAll();
        if(CollectionUtils.isEmpty(department)) {
            return null;
        }
        return department;
    }

    @Override
    public Object getDepartmentById(Integer id) {
        Optional<Department> findDepartment = departmentRepo.findById(id);
        if(findDepartment.isPresent()) {
            return findDepartment;
        }
        return ErrorConstants.DEPARTMENT_NOT_FOUND;
    }

    @Override
    public Object deleteDepartmentDetails(Integer id) {
        Optional<Department> findDepartment = departmentRepo.findById(id);
        if(findDepartment.isPresent()) {
            departmentRepo.deleteById(id);
            return id;
        }
        return ErrorConstants.DEPARTMENT_NOT_FOUND;
    }

    @Override
    public Object updateDepartmentDetails(DepartmentRequest departmentRequest) {
        Optional<Department> findDepartment = departmentRepo.findById(departmentRequest.getId());
        if(findDepartment.isPresent()) {
            Optional<Department> deptByName = departmentRepo.findDepartmentByName(departmentRequest.getName());
            if(deptByName.isEmpty() || deptByName.get().getId()==departmentRequest.getId()) {
                Department department = Department.builder()
                        .name(departmentRequest.getName())
                        .build();
                Department save = departmentRepo.save(department);
                return save;
            }
            return ErrorConstants.DEPARTMENT_ALREADY_EXISTS;
        }
        return ErrorConstants.DEPARTMENT_NOT_FOUND;
    }
}
