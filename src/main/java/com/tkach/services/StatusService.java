package com.tkach.services;

import com.tkach.model.Request;
import com.tkach.model.Status;
import com.tkach.repositories.StatusRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public Status findOne(int id) {
        Optional<Status> foundStatus = statusRepository.findById(id);
        return foundStatus.orElse(null);
    }

    @Transactional
    public void save(Status status) {
        statusRepository.save(status);
    }

    @Transactional
    public void update(int id, Status updatedStatus) {
        updatedStatus.setId(id);
        statusRepository.save(updatedStatus);
    }

    @Transactional
    public void delete(int id) {
        statusRepository.deleteById(id);
    }

    public Optional<Status> getStatusByName(String name) {
        return statusRepository.findByName(name);
    }

    public List<Request> getRequestByStatusId(int id) {
        Optional<Status> status = statusRepository.findById(id);
        if (status.isPresent()) {
            Hibernate.initialize(status.get().getRequestList());
            return status.get().getRequestList();
        }
        else {
            return Collections.emptyList();
        }
    }
}
