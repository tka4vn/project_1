package com.tkach.services;

import com.tkach.model.Appointment;
import com.tkach.model.Employee;
import com.tkach.model.Users;
import com.tkach.repositories.EmployeeRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findOne(int id) {
        Optional<Employee> foundEmployee = employeeRepository.findById(id);
        return foundEmployee.orElse(null);
    }

    @Transactional
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Transactional
    public void update(int id, Employee updatedEmployee) {
        updatedEmployee.setId(id);
        employeeRepository.save(updatedEmployee);
    }

    @Transactional
    public void delete(int id) {
        employeeRepository.deleteById(id);
    }

    public Optional<Employee> getEmployeeByFullName(String fullName) {
        return employeeRepository.findByFullName(fullName);
    }

    public List<Appointment> getAppointmentByEmployeeId(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            Hibernate.initialize(employee.get().getAppointmentList());
            return employee.get().getAppointmentList();
        }
        else {
            return Collections.emptyList();
        }
    }

    public List<Users> getUsersByEmployeeId(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            Hibernate.initialize(employee.get().getUsersList());
            return employee.get().getUsersList();
        }
        else {
            return Collections.emptyList();
        }
    }
}
