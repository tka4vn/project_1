package com.tkach.services;


import com.tkach.model.Ingress;
import com.tkach.model.Request;
import com.tkach.model.Services;
import com.tkach.repositories.ServicesRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ServicesService {

    private final ServicesRepository servicesRepository;

    @Autowired
    public ServicesService(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    public List<Services> findAll() {
        return servicesRepository.findAll();
    }

    public Services findOne(int id) {
        Optional<Services> foundServices = servicesRepository.findById(id);
        return foundServices.orElse(null);
    }

    @Transactional
    public void save(Services services) {
        servicesRepository.save(services);
    }

    @Transactional
    public void update(int id, Services updatedServices) {
        updatedServices.setId(id);
        servicesRepository.save(updatedServices);
    }

    @Transactional
    public void delete(int id) {
        servicesRepository.deleteById(id);
    }

    public Optional<Services> getServicesByName(String name) {
        return servicesRepository.findByName(name);
    }

    public List<Ingress> getIngressByServicesId(int id) {
        Optional<Services> services = servicesRepository.findById(id);
        if (services.isPresent()) {
            Hibernate.initialize(services.get().getIngressList());
            return services.get().getIngressList();
        }
        else {
            return Collections.emptyList();
        }
    }

    public List<Request> getRequestByServicesId(int id) {
        Optional<Services> services = servicesRepository.findById(id);
        if (services.isPresent()) {
            Hibernate.initialize(services.get().getRequestList());
            return services.get().getRequestList();
        }
        else {
            return Collections.emptyList();
        }
    }
}
