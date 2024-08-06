package com.example.person.service.impl;

import com.person.constants.ErrorConstants;
import com.person.entity.Department;
import com.person.repository.DepartmentRepository;
import com.person.request.DepartmentRequest;
import com.person.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    Department department = Department.builder()
            .id(1)
            .name("Admin")
            .build();
    DepartmentRequest request = DepartmentRequest.builder()
            .id(1)
            .name("Admin")
            .build();

    @Test
    void saveDepartmentDetailsForAlreadyDepartmentTest() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        Object data = departmentService.saveDepartmentDetails(request);
        assertEquals(ErrorConstants.DEPARTMENT_ALREADY_EXISTS, data);
    }

    @Test
    void saveDepartmentDetailsTest() {
        when(departmentRepository.findAll()).thenReturn(List.of());
        when(departmentRepository.save(any())).thenReturn(department);
        Object data = departmentService.saveDepartmentDetails(request);
        assertNotNull(data);
    }
}
