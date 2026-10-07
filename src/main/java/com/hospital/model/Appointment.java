package com.hospital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addappointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patientname;
    private String disease;
    private String doctorappointed;
    private String appointementdate;
    private String appointmenttime;

    public Appointment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPatientname() { return patientname; }
    public void setPatientname(String patientname) { this.patientname = patientname; }
    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }
    public String getDoctorappointed() { return doctorappointed; }
    public void setDoctorappointed(String doctorappointed) { this.doctorappointed = doctorappointed; }
    public String getAppointementdate() { return appointementdate; }
    public void setAppointementdate(String appointementdate) { this.appointementdate = appointementdate; }
    public String getAppointmenttime() { return appointmenttime; }
    public void setAppointmenttime(String appointmenttime) { this.appointmenttime = appointmenttime; }
}