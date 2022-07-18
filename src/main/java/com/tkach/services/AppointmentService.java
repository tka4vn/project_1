package com.tkach.services;

import com.tkach.model.Appointment;
import com.tkach.model.Department;
import com.tkach.model.Employee;
import com.tkach.model.Position;
import com.tkach.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> findAll(boolean sortByDateApp) {
        if (sortByDateApp)
            return appointmentRepository.findAll(Sort.by("dateApp"));
        else
            return appointmentRepository.findAll();
    }

    public List<Appointment> findWithPagination(Integer page, Integer appointmentPerPage, boolean sortByDateApp) {
        if (sortByDateApp)
            return appointmentRepository.findAll(PageRequest.of(page, appointmentPerPage, Sort.by("dateApp"))).getContent();
        else
            return appointmentRepository.findAll(PageRequest.of(page, appointmentPerPage)).getContent();
    }

    public Appointment findOne(int id) {
        Optional<Appointment> foundAppointment = appointmentRepository.findById(id);
        return foundAppointment.orElse(null);
    }

    @Transactional
    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void update(int id, Appointment updatedAppointment) {
        Appointment appointmentToBeUpdated = appointmentRepository.findById(id).get();
        updatedAppointment.setId(id);
        updatedAppointment.setIdDepApp(appointmentToBeUpdated.getIdDepApp());
        updatedAppointment.setIdPosApp(appointmentToBeUpdated.getIdPosApp());
        updatedAppointment.setIdEmpApp(appointmentToBeUpdated.getIdEmpApp());
        appointmentRepository.save(updatedAppointment);
    }

    @Transactional
    public void delete(int id) {
        appointmentRepository.deleteById(id);
    }

    public Department getAppointmentIdDepApp(int id) {
        return appointmentRepository.findById(id).map(Appointment::getIdDepApp).orElse(null);
    }
    public Position getAppointmentIdPosApp(int id) {
        return appointmentRepository.findById(id).map(Appointment::getIdPosApp).orElse(null);
    }

    public Employee getAppointmentIdEmpApp(int id) {
        return appointmentRepository.findById(id).map(Appointment::getIdEmpApp).orElse(null);
    }

    @Transactional
    public void releaseDepartment(int id) {
        appointmentRepository.findById(id).ifPresent(
                appointment -> {
                    appointment.setIdDepApp(null);
                });
    }

    @Transactional
    public void releasePosition(int id) {
        appointmentRepository.findById(id).ifPresent(
                appointment -> {
                    appointment.setIdPosApp(null);
                });
    }

    @Transactional
    public void releaseEmployee(int id) {
        appointmentRepository.findById(id).ifPresent(
                appointment -> {
                    appointment.setIdEmpApp(null);
                });
    }

    @Transactional
    public void assignDepartment(int id, Department selectedDepartment) {
        appointmentRepository.findById(id).ifPresent(
                appointment -> {
                    appointment.setIdDepApp(selectedDepartment);
                }
        );
    }
    @Transactional
    public void assignPosition(int id, Position selectedPosition) {
        appointmentRepository.findById(id).ifPresent(
                appointment -> {
                    appointment.setIdPosApp(selectedPosition);
                }
        );
    }
    @Transactional
    public void assignEmployee(int id, Employee selectedEmployee) {
        appointmentRepository.findById(id).ifPresent(
                appointment -> {
                    appointment.setIdEmpApp(selectedEmployee);
                }
        );
    }
}
