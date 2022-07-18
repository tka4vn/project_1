package com.tkach.services;

import com.tkach.model.Appointment;
import com.tkach.model.Position;
import com.tkach.repositories.PositionRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PositionService {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    public Position findOne(int id) {
        Optional<Position> foundPosition = positionRepository.findById(id);
        return foundPosition.orElse(null);
    }

    @Transactional
    public void save(Position position) {
        positionRepository.save(position);
    }

    @Transactional
    public void update(int id, Position updatedPosition) {
        updatedPosition.setId(id);
        positionRepository.save(updatedPosition);
    }

    @Transactional
    public void delete(int id) {
        positionRepository.deleteById(id);
    }

    public Optional<Position> getPositionByName(String name) {
        return positionRepository.findByName(name);
    }

    public List<Appointment> getAppointmentByPositionId(int id) {
        Optional<Position> position = positionRepository.findById(id);
        if (position.isPresent()) {
            Hibernate.initialize(position.get().getAppointmentList());
            return position.get().getAppointmentList();
        }
        else {
            return Collections.emptyList();
        }
    }
}
