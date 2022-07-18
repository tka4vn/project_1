package com.tkach.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "REQUEST")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Request {

    @Id
    @Column(name = "REQUEST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной более 1 символа")
    @Column(name = "DATE_REQUEST")
    private String dateReq;

    @ManyToOne
    @JoinColumn(name = "USERS_ID", referencedColumnName = "USERS_ID")
    private Users idUseReq;

    @ManyToOne
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERVICE_ID")
    private Services idSerReq;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    private Role idRolReq;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
    private Status idStaReq;

    public Request() {
    }

    public Request(String dateReq) {
        this.dateReq = dateReq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateReq() {
        return dateReq;
    }

    public void setDateReq(String dateReq) {
        this.dateReq = dateReq;
    }

    public Users getIdUseReq() {
        return idUseReq;
    }

    public void setIdUseReq(Users idUseReq) {
        this.idUseReq = idUseReq;
    }

    public Services getIdSerReq() {
        return idSerReq;
    }

    public void setIdSerReq(Services idSerReq) {
        this.idSerReq = idSerReq;
    }

    public Role getIdRolReq() {
        return idRolReq;
    }

    public void setIdRolReq(Role idRolReq) {
        this.idRolReq = idRolReq;
    }

    public Status getIdStaReq() {
        return idStaReq;
    }

    public void setIdStaReq(Status idStaReq) {
        this.idStaReq = idStaReq;
    }
}
