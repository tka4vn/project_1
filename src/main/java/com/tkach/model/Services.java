package com.tkach.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "SERVICE")
public class Services {

    @Id
    @Column(name = "SERVICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной более 1 символа")
    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "idSerIng")
    private List<Ingress> ingressList;

    @OneToMany(mappedBy = "idSerReq")
    private List<Request> requestList;

    public Services() {
    }

    public Services(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingress> getIngressList() {
        return ingressList;
    }

    public void setIngressList(List<Ingress> ingressList) {
        this.ingressList = ingressList;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }
}
