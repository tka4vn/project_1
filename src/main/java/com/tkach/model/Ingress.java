package com.tkach.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "INGRESS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Ingress {

    @Id
    @Column(name = "INGRESS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной более 1 символа")
    @Column(name = "DATE_ING")
    private String dateIng;

    @ManyToOne
    @JoinColumn(name = "USERS_ID", referencedColumnName = "USERS_ID")
    private Users idUseIng;

    @ManyToOne
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERVICE_ID")
    private Services idSerIng;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    private Role idRolIng;

    public Ingress() {
    }

    public Ingress(String dateIng) {
        this.dateIng = dateIng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateIng() {
        return dateIng;
    }

    public void setDateIng(String dateIng) {
        this.dateIng = dateIng;
    }

    public Users getIdUseIng() {
        return idUseIng;
    }

    public void setIdUseIng(Users idUseIng) {
        this.idUseIng = idUseIng;
    }

    public Services getIdSerIng() {
        return idSerIng;
    }

    public void setIdSerIng(Services idSerIng) {
        this.idSerIng = idSerIng;
    }

    public Role getIdRolIng() {
        return idRolIng;
    }

    public void setIdRolIng(Role idRolIng) {
        this.idRolIng = idRolIng;
    }
}
