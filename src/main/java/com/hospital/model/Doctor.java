package com.hospital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "adddoctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorid;
    private String doctorname;
    private String gender;
    private String specialist;
    private String contact;
    private String address;

    public Doctor() {}

    public Integer getDoctorid() { return doctorid; }
    public void setDoctorid(Integer doctorid) { this.doctorid = doctorid; }
    public String getDoctorname() { return doctorname; }
    public void setDoctorname(String doctorname) { this.doctorname = doctorname; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getSpecialist() { return specialist; }
    public void setSpecialist(String specialist) { this.specialist = specialist; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}