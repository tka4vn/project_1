package com.tkach.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Users {

    @Id
    @Column(name = "USERS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной более 1 символа")
    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private Employee idEmpUse;

    @OneToMany(mappedBy = "idUseIng")
    private List<Ingress> ingressList;

    @OneToMany(mappedBy = "idUseReq")
    private List<Request> requestList;

    public Users() {
    }

    public Users(String name) {
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

    public Employee getIdEmpUse() {
        return idEmpUse;
    }

    public void setIdEmpUse(Employee idEmpUse) {
        this.idEmpUse = idEmpUse;
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
