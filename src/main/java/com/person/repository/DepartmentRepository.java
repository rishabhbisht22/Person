package com.person.repository;

import com.person.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query(value = "Select * from department where name=?1", nativeQuery = true)
    Optional<Department> findDepartmentByName(String name);
}
