package com.tkach.services;

import com.tkach.model.*;
import com.tkach.repositories.UsersRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> findAll() {
            return usersRepository.findAll();
    }

    public List<Users> findWithPagination(Integer page, Integer usersPerPage, boolean sortByName) {
        if (sortByName)
            return usersRepository.findAll(PageRequest.of(page, usersPerPage, Sort.by("name"))).getContent();
        else
            return usersRepository.findAll(PageRequest.of(page, usersPerPage)).getContent();
    }

    public Users findOne(int id) {
        Optional<Users> foundUsers = usersRepository.findById(id);
        return foundUsers.orElse(null);
    }

    @Transactional
    public void save(Users users) {
        usersRepository.save(users);
    }

    @Transactional
    public void update(int id, Users updatedUsers) {
        Users usersToBeUpdated = usersRepository.findById(id).get();
        updatedUsers.setId(id);
        updatedUsers.setIdEmpUse(usersToBeUpdated.getIdEmpUse()); // чтобы не терялась связь при обновлении
        usersRepository.save(updatedUsers);
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    public Optional<Users> getUsersByName(String name) {
        return usersRepository.findByName(name);
    }

    public List<Ingress> getIngressByUsersId(int id) {
        Optional<Users> users = usersRepository.findById(id);
        if (users.isPresent()) {
            Hibernate.initialize(users.get().getIngressList());
            return users.get().getIngressList();
        }
        else {
            return Collections.emptyList();
        }
    }

    public List<Request> getRequestByUsersId(int id) {
        Optional<Users> users = usersRepository.findById(id);
        if (users.isPresent()) {
            Hibernate.initialize(users.get().getRequestList());
            return users.get().getRequestList();
        }
        else {
            return Collections.emptyList();
        }
    }
    public Employee getUsersIdEmpUse(int id) {
        return usersRepository.findById(id).map(Users::getIdEmpUse).orElse(null);
    }

    @Transactional
    public void releaseEmployee(int id) {
        usersRepository.findById(id).ifPresent(
                users -> {
                    users.setIdEmpUse(null);
                });
    }

    @Transactional
    public void assignEmployee(int id, Employee selectedEmployee) {
        usersRepository.findById(id).ifPresent(
                users -> {
                    users.setIdEmpUse(selectedEmployee);
                }
        );
    }
}
