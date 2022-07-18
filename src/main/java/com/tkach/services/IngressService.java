package com.tkach.services;

import com.tkach.model.*;
import com.tkach.repositories.IngressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class IngressService {

    private final IngressRepository ingressRepository;

    @Autowired
    public IngressService(IngressRepository ingressRepository) {
        this.ingressRepository = ingressRepository;
    }

    public List<Ingress> findAll(boolean sortByDateIng) {
        if (sortByDateIng)
            return ingressRepository.findAll(Sort.by("dateIng"));
        else
            return ingressRepository.findAll();
    }

    public List<Ingress> findWithPagination(Integer page, Integer ingressPerPage, boolean sortByDateIng) {
        if (sortByDateIng)
            return ingressRepository.findAll(PageRequest.of(page, ingressPerPage, Sort.by("dateIng"))).getContent();
        else
            return ingressRepository.findAll(PageRequest.of(page, ingressPerPage)).getContent();
    }

    public Ingress findOne(int id) {
        Optional<Ingress> foundIngress = ingressRepository.findById(id);
        return foundIngress.orElse(null);
    }

    @Transactional
    public void save(Ingress ingress) {
        ingressRepository.save(ingress);
    }

    @Transactional
    public void update(int id, Ingress updatedIngress) {
        Ingress ingressToBeUpdated = ingressRepository.findById(id).get();
        updatedIngress.setId(id);
        updatedIngress.setIdUseIng(ingressToBeUpdated.getIdUseIng());
        updatedIngress.setIdSerIng(ingressToBeUpdated.getIdSerIng());
        updatedIngress.setIdRolIng(ingressToBeUpdated.getIdRolIng());
        ingressRepository.save(updatedIngress);
    }

    @Transactional
    public void delete(int id) {
        ingressRepository.deleteById(id);
    }

    public Users getIngressIdUseIng(int id) {
        return ingressRepository.findById(id).map(Ingress::getIdUseIng).orElse(null);
    }
    public Services getIngressIdSerIng(int id) {
        return ingressRepository.findById(id).map(Ingress::getIdSerIng).orElse(null);
    }

    public Role getIngressIdRolIng(int id) {
        return ingressRepository.findById(id).map(Ingress::getIdRolIng).orElse(null);
    }

    @Transactional
    public void releaseUsers(int id) {
        ingressRepository.findById(id).ifPresent(
                ingress -> {
                    ingress.setIdUseIng(null);
                });
    }

    @Transactional
    public void releaseServices(int id) {
        ingressRepository.findById(id).ifPresent(
                ingress -> {
                    ingress.setIdSerIng(null);
                });
    }

    @Transactional
    public void releaseRole(int id) {
        ingressRepository.findById(id).ifPresent(
                ingress -> {
                    ingress.setIdRolIng(null);
                });
    }

    @Transactional
    public void assignUsers(int id, Users selectedUsers) {
        ingressRepository.findById(id).ifPresent(
                ingress -> {
                    ingress.setIdUseIng(selectedUsers);
                }
        );
    }
    @Transactional
    public void assignServices(int id, Services selectedServices) {
        ingressRepository.findById(id).ifPresent(
                ingress -> {
                    ingress.setIdSerIng(selectedServices);
                }
        );
    }
    @Transactional
    public void assignRole(int id, Role selectedRole) {
        ingressRepository.findById(id).ifPresent(
                ingress -> {
                    ingress.setIdRolIng(selectedRole);
                }
        );
    }
}
