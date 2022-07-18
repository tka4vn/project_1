package com.tkach.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "APPOINTMENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Appointment {

    @Id
    @Column(name = "APPOINTMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть длиной более 1 символа")
    @Column(name = "DATE_APP")
    private String dateApp;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID")
    private Department idDepApp;

    @ManyToOne
    @JoinColumn(name = "POSITION_ID", referencedColumnName = "POSITION_ID")
    private Position idPosApp;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID")
    private Employee idEmpApp;

    public Appointment() {
    }

    public Appointment(String dateApp) {
        this.dateApp = dateApp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateApp() {
        return dateApp;
    }

    public void setDateApp(String title) {
        this.dateApp = title;
    }

    public Department getIdDepApp() {
        return idDepApp;
    }

    public void setIdDepApp(Department idDepApp) {
        this.idDepApp = idDepApp;
    }

    public Position getIdPosApp() {
        return idPosApp;
    }

    public void setIdPosApp(Position idPosApp) {
        this.idPosApp = idPosApp;
    }

    public Employee getIdEmpApp() {
        return idEmpApp;
    }

    public void setIdEmpApp(Employee idEmpApp) {
        this.idEmpApp = idEmpApp;
    }
}
