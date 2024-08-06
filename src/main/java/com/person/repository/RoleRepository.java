package com.person.repository;

import com.person.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "Select * from role where name = ?1", nativeQuery = true)
    Optional<Role> findByRoleName(String name);
}
