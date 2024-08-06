package com.person.service;

import com.person.entity.Department;
import com.person.request.DepartmentRequest;

import java.util.List;

public interface DepartmentService {

    Object saveDepartmentDetails(DepartmentRequest departmentRequest);

    List<Department> getDepartmentDetailsList();

    Object getDepartmentById(Integer id);

    Object deleteDepartmentDetails(Integer id);

    Object updateDepartmentDetails(DepartmentRequest departmentRequest);
}
