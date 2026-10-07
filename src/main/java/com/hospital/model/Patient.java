package com.hospital.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addpatient")
public class Patient {

    @Id
    @Column(name = "id", columnDefinition = "TEXT")
    private String patientId;

    private String password;
    private String patientname;
    private String gender;
    private String contact;
    private String address;
    private String disease;
    private String roomno;

    public Patient() {}

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPatientname() { return patientname; }
    public void setPatientname(String patientname) { this.patientname = patientname; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }

    public String getRoomno() { return roomno; }
    public void setRoomno(String roomno) { this.roomno = roomno; }
}