package com.tkach.services;

import com.tkach.model.Ingress;
import com.tkach.model.Request;
import com.tkach.model.Role;
import com.tkach.repositories.RoleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findOne(int id) {
        Optional<Role> foundRole = roleRepository.findById(id);
        return foundRole.orElse(null);
    }

    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void update(int id, Role updatedRole) {
        updatedRole.setId(id);
        roleRepository.save(updatedRole);
    }

    @Transactional
    public void delete(int id) {
        roleRepository.deleteById(id);
    }

    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<Ingress> getIngressByRoleId(int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            Hibernate.initialize(role.get().getIngressList());
            return role.get().getIngressList();
        }
        else {
            return Collections.emptyList();
        }
    }

    public List<Request> getRequestByRoleId(int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            Hibernate.initialize(role.get().getRequestList());
            return role.get().getRequestList();
        }
        else {
            return Collections.emptyList();
        }
    }
}
