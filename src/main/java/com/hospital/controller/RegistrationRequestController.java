package com.hospital.controller;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.model.Patient;
import com.hospital.model.RegistrationRequest;
import com.hospital.model.User;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.RegistrationRequestRepository;
import com.hospital.repository.UserRepository;

@RestController
@RequestMapping("/api/registration-requests")
@CrossOrigin(origins = "*")
public class RegistrationRequestController {

    private final RegistrationRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final SecureRandom secureRandom = new SecureRandom();

    public RegistrationRequestController(RegistrationRequestRepository requestRepository,
                                         UserRepository userRepository,
                                         PatientRepository patientRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @PostMapping
    public ResponseEntity<RegistrationRequest> submitRequest(@RequestBody RegistrationRequest req) {
        req.setRequestDate(LocalDate.now().toString());
        req.setStatus("PENDING");
        return ResponseEntity.ok(requestRepository.saveAndFlush(req));
    }

    @GetMapping
    public ResponseEntity<List<RegistrationRequest>> getAllRequests() {
        return ResponseEntity.ok(requestRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRequestById(@PathVariable Long id) {
        return requestRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/approve")
    @Transactional
    public ResponseEntity<?> approveRequest(@PathVariable Long id) {
        Optional<RegistrationRequest> opt = requestRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RegistrationRequest req = opt.get();

        // 1. Duplicate check against existing users
        List<User> allUsers = userRepository.findAll();
        boolean contactExists = allUsers.stream().anyMatch(u ->
            u.getContact() != null && req.getContactNumber() != null &&
            u.getContact().trim().equalsIgnoreCase(req.getContactNumber().trim())
        );
        boolean emailExists = allUsers.stream().anyMatch(u ->
            u.getEmail() != null && req.getEmail() != null &&
            u.getEmail().trim().equalsIgnoreCase(req.getEmail().trim())
        );

        if (contactExists || emailExists) {
            req.setStatus("REJECTED");
            String duplicateReason = (contactExists && emailExists)
                    ? "Both Mobile number and Email are already registered."
                    : contactExists ? "Mobile number is already registered."
                    : "Email address is already registered.";
            req.setRejectionReason(duplicateReason);
            requestRepository.saveAndFlush(req);

            Map<String, String> err = new HashMap<>();
            err.put("error", "Cannot approve duplicate registration.");
            err.put("reason", duplicateReason);
            return ResponseEntity.badRequest().body(err);
        }

        // 2. GUARANTEED UNIQUE PATIENT ID (Checks DB to eliminate any repetition)
        String patientId;
        do {
            int code = 10000 + secureRandom.nextInt(90000); // 5-digit range (10000 - 99999)
            patientId = "PAT-" + code;
        } while (isPatientIdTaken(patientId, allUsers));

        // 3. CRYPTOGRAPHICALLY SECURE TEMPORARY PASSWORD
        String rawPassword = generateSecureTempPassword();

        // 4. Save to clinical table (addpatient)
        Patient patient = new Patient();
        patient.setPatientId(patientId);
        patient.setPassword(rawPassword);
        patient.setPatientname(req.getFullName());
        patient.setGender(req.getGender());
        patient.setContact(req.getContactNumber());
        patient.setDisease(req.getPreferredDepartment() + " - " + req.getMedicalHistory());
        patient.setRoomno("OPD / Online Registered");
        patient.setAddress(req.getAddress());
        patientRepository.saveAndFlush(patient);

        // 5. Save HASHED password to security table (register)
        User authUser = new User();
        authUser.setUsername(patientId);
        authUser.setPassword(passwordEncoder.encode(rawPassword)); // BCrypt hash ($2a$10$...)
        authUser.setRole("PATIENT");
        authUser.setFullName(req.getFullName());
        authUser.setEmail(req.getEmail());
        authUser.setContact(req.getContactNumber());
        authUser.setAddress(req.getAddress());
        userRepository.saveAndFlush(authUser);

        // 6. Update request record (stores raw credentials so user can view once upon approval)
        req.setStatus("APPROVED");
        req.setGeneratedPatientId(patientId);
        req.setGeneratedPassword(rawPassword);
        requestRepository.saveAndFlush(req);

        Map<String, String> resp = new HashMap<>();
        resp.put("message", "Registration Approved");
        resp.put("patientId", patientId);
        resp.put("password", rawPassword);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectRequest(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return requestRepository.findById(id).map(req -> {
            req.setStatus("REJECTED");
            req.setRejectionReason(body.getOrDefault("reason", "Mobile number or email already in use."));
            requestRepository.saveAndFlush(req);
            return ResponseEntity.ok(req);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Helper: Ensure no ID collision in the database
    private boolean isPatientIdTaken(String id, List<User> existingUsers) {
        return existingUsers.stream().anyMatch(u -> u.getUsername() != null && u.getUsername().equalsIgnoreCase(id));
    }

    // Helper: Generate 8-character mixed alphanumeric secure password
    private String generateSecureTempPassword() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(secureRandom.nextInt(chars.length())));
        }
        return sb.toString();
    }
}