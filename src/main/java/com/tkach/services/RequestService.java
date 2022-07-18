package com.tkach.services;

import com.tkach.model.*;
import com.tkach.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> findAll(boolean sortByDateReq) {
        if (sortByDateReq)
            return requestRepository.findAll(Sort.by("dateReq"));
        else
            return requestRepository.findAll();
    }

    public List<Request> findWithPagination(Integer page, Integer requestPerPage, boolean sortByDateReq) {
        if (sortByDateReq)
            return requestRepository.findAll(PageRequest.of(page, requestPerPage, Sort.by("dateReq"))).getContent();
        else
            return requestRepository.findAll(PageRequest.of(page, requestPerPage)).getContent();
    }

    public Request findOne(int id) {
        Optional<Request> foundRequest = requestRepository.findById(id);
        return foundRequest.orElse(null);
    }

    @Transactional
    public void save(Request request) {
        requestRepository.save(request);
    }

    @Transactional
    public void update(int id, Request updatedRequest) {
        Request requestToBeUpdated = requestRepository.findById(id).get();
        updatedRequest.setId(id);
        updatedRequest.setIdUseReq(requestToBeUpdated.getIdUseReq());
        updatedRequest.setIdSerReq(requestToBeUpdated.getIdSerReq());
        updatedRequest.setIdRolReq(requestToBeUpdated.getIdRolReq());
        updatedRequest.setIdStaReq(requestToBeUpdated.getIdStaReq());
        requestRepository.save(updatedRequest);
    }

    @Transactional
    public void delete(int id) {
        requestRepository.deleteById(id);
    }

    public Users getRequestIdUseReq(int id) {
        return requestRepository.findById(id).map(Request::getIdUseReq).orElse(null);
    }
    public Services getRequestIdSerReq(int id) {
        return requestRepository.findById(id).map(Request::getIdSerReq).orElse(null);
    }

    public Role getRequestIdRolReq(int id) {
        return requestRepository.findById(id).map(Request::getIdRolReq).orElse(null);
    }

    public Status getRequestIdStaReq(int id) {
        return requestRepository.findById(id).map(Request::getIdStaReq).orElse(null);
    }

    @Transactional
    public void releaseUsers(int id) {
        requestRepository.findById(id).ifPresent(
                request -> {
                    request.setIdUseReq(null);
                });
    }

    @Transactional
    public void releaseServices(int id) {
        requestRepository.findById(id).ifPresent(
                request -> {
                    request.setIdSerReq(null);
                });
    }

    @Transactional
    public void releaseRole(int id) {
        requestRepository.findById(id).ifPresent(
                request -> {
                    request.setIdRolReq(null);
                });
    }

    @Transactional
    public void releaseStatus(int id) {
        requestRepository.findById(id).ifPresent(
                request -> {
                    request.setIdStaReq(null);
                });
    }

    @Transactional
    public void assignUsers(int id, Users selectedUsers) {
        requestRepository.findById(id).ifPresent(
                request -> {
                    request.setIdUseReq(selectedUsers);
                }
        );
    }
    @Transactional
    public void assignServices(int id, Services selectedServices) {
        requestRepository.findById(id).ifPresent(
                request -> {
                    request.setIdSerReq(selectedServices);
                }
        );
    }
    @Transactional
    public void assignRole(int id, Role selectedRole) {
        requestRepository.findById(id).ifPresent(
                request -> {
                    request.setIdRolReq(selectedRole);
                }
        );
    }
    @Transactional
    public void assignStatus(int id, Status selectedStatus) {
        requestRepository.findById(id).ifPresent(
                request -> {
                    request.setIdStaReq(selectedStatus);
                }
        );
    }
}
