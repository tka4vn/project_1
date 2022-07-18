package com.tkach.services;

import com.tkach.model.Appointment;
import com.tkach.model.Department;
import com.tkach.repositories.DepartmentRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findOne(int id) {
        Optional<Department> foundDepartment = departmentRepository.findById(id);
        return foundDepartment.orElse(null);
    }

    @Transactional
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Transactional
    public void update(int id, Department updatedDepartment) {
        updatedDepartment.setId(id);
        departmentRepository.save(updatedDepartment);
    }

    @Transactional
    public void delete(int id) {
        departmentRepository.deleteById(id);
    }

    public Optional<Department> getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    public List<Appointment> getAppointmentByDepartmentId(int id) {
        Optional<Department> department = departmentRepository.findById(id);

        if (department.isPresent()) {
            Hibernate.initialize(department.get().getAppointmentList());
            return department.get().getAppointmentList();
        }
        else {
            return Collections.emptyList();
        }
    }
}
