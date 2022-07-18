package com.tkach.repositories;

import com.tkach.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
        Optional<Employee> findByFullName(String fullName);
        }