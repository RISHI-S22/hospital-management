package com.hospital;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hospital.model.Doctor;
import com.hospital.model.Medicine;
import com.hospital.model.Nurse;
import com.hospital.model.Patient;
import com.hospital.model.Pharmacist;
import com.hospital.model.User;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.MedicineRepository;
import com.hospital.repository.NurseRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.PharmacistRepository;
import com.hospital.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final PharmacistRepository pharmacistRepository;
    private final PatientRepository patientRepository;
    private final MedicineRepository medicineRepository;

    public DataInitializer(UserRepository userRepository,
                           DoctorRepository doctorRepository,
                           NurseRepository nurseRepository,
                           PharmacistRepository pharmacistRepository,
                           PatientRepository patientRepository,
                           MedicineRepository medicineRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.pharmacistRepository = pharmacistRepository;
        this.patientRepository = patientRepository;
        this.medicineRepository = medicineRepository;
    }

    private Doctor buildDoctor(String name, String gender, String spec, String phone, String addr) {
        Doctor d = new Doctor();
        d.setDoctorname(name);
        d.setGender(gender);
        d.setSpecialist(spec);
        d.setContact(phone);
        d.setAddress(addr);
        return d;
    }

    private Nurse buildNurse(String name, String email, String phone, String addr) {
        Nurse n = new Nurse();
        n.setName(name);
        n.setEmail(email);
        n.setContact(phone);
        n.setAddress(addr);
        return n;
    }

   private Pharmacist buildPharmacist(int id, String name, String email, String phone, String addr) {
        Pharmacist p = new Pharmacist();
        p.setPharmacistsid(id); // Assign the required ID
        p.setName(name);
        p.setEmail(email);
        p.setContact(phone);
        p.setAddress(addr);
        return p;
    }

    private Patient buildPatient(String id, String pass, String name, String gender, String phone, String addr, String disease, String room) {
        Patient p = new Patient();
        p.setPatientId(id);
        p.setPassword(pass);
        p.setPatientname(name);
        p.setGender(gender);
        p.setContact(phone);
        p.setAddress(addr);
        p.setDisease(disease);
        p.setRoomno(room);
        return p;
    }

    private User buildUser(String username, String email, String pass, String role, String fullName, String phone, String addr) {
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(pass);
        u.setRole(role);
        u.setFullName(fullName);
        u.setContact(phone);
        u.setAddress(addr);
        return u;
    }

    @Override
    public void run(String... args) {
        if (doctorRepository.count() > 0 && medicineRepository.count() > 0) {
            System.out.println(">> Database already populated. Skipping initialization.");
            return;
        }

        System.out.println(">> Initializing Hospital Database records...");

        // 1. ADMIN
        userRepository.save(buildUser("ADM-001", "admin@hospital.com", "admin123", "ADMIN", "System Administrator", "9999999999", "Admin Office"));

        // 2. 10 DOCTORS (Do NOT set ID manually; let JPA generate it)
        List<Doctor> rawDocs = List.of(
            buildDoctor("Dr. Sarah Jenkins", "Female", "Cardiology", "9811002201", "Cardiac Wing A"),
            buildDoctor("Dr. Robert Chen", "Male", "Neurology", "9811002202", "Neuro Clinic B"),
            buildDoctor("Dr. Emily Taylor", "Female", "Pediatrics", "9811002203", "Children Ward 1"),
            buildDoctor("Dr. Marcus Vance", "Male", "Orthopedics", "9811002204", "Ortho Center"),
            buildDoctor("Dr. Anita Desai", "Female", "Dermatology", "9811002205", "Skin Care Block"),
            buildDoctor("Dr. William Scott", "Male", "Oncology", "9811002206", "Cancer Center 3"),
            buildDoctor("Dr. Lisa Wong", "Female", "Gynecology", "9811002207", "Maternity Wing"),
            buildDoctor("Dr. David Miller", "Male", "Psychiatry", "9811002208", "Behavioral Unit"),
            buildDoctor("Dr. Priya Nair", "Female", "Endocrinology", "9811002209", "Metabolic Clinic"),
            buildDoctor("Dr. Kevin Brown", "Male", "Gastroenterology", "9811002210", "Digestive Health")
        );

        int docIdx = 101;
        for (Doctor d : rawDocs) {
            Doctor saved = doctorRepository.save(d);
            String docIdStr = "DOC-" + (docIdx++);
            userRepository.save(buildUser(docIdStr, "doc" + saved.getDoctorid() + "@hospital.com", "doc123", "DOCTOR", saved.getDoctorname(), saved.getContact(), saved.getAddress()));
        }

        // 3. 12 NURSES
        List<Nurse> rawNurses = List.of(
            buildNurse("Nurse Clara Barton", "clara@hospital.com", "9822003301", "Quarters 1"),
            buildNurse("Nurse James Wilson", "james@hospital.com", "9822003302", "Quarters 2"),
            buildNurse("Nurse Rachel Adams", "rachel@hospital.com", "9822003303", "Quarters 3"),
            buildNurse("Nurse Hannah Scott", "hannah@hospital.com", "9822003304", "Quarters 4"),
            buildNurse("Nurse Samuel Green", "samuel@hospital.com", "9822003305", "Quarters 5"),
            buildNurse("Nurse Chloe Bennett", "chloe@hospital.com", "9822003306", "Quarters 6"),
            buildNurse("Nurse Daniel Craig", "daniel@hospital.com", "9822003307", "Quarters 7"),
            buildNurse("Nurse Grace Kelly", "grace@hospital.com", "9822003308", "Quarters 8"),
            buildNurse("Nurse Ethan Hunt", "ethan@hospital.com", "9822003309", "Quarters 9"),
            buildNurse("Nurse Sophia Loren", "sophia@hospital.com", "9822003310", "Quarters 10"),
            buildNurse("Nurse Lucas Gray", "lucas@hospital.com", "9822003311", "Quarters 11"),
            buildNurse("Nurse Mia Thorne", "mia@hospital.com", "9822003312", "Quarters 12")
        );

        int nurseIdx = 201;
        for (Nurse n : rawNurses) {
            Nurse saved = nurseRepository.save(n);
            String nurseIdStr = "NUR-" + (nurseIdx++);
            userRepository.save(buildUser(nurseIdStr, saved.getEmail(), "nurse123", "NURSE", saved.getName(), saved.getContact(), saved.getAddress()));
        }

        // 4. 3 PHARMACISTS
       // 4. 3 PHARMACISTS
        List<Pharmacist> rawPharms = List.of(
            buildPharmacist(301, "Alex Miller", "alex.m@hospital.com", "9833004401", "Counter 1"),
            buildPharmacist(302, "Laura Croft", "laura.c@hospital.com", "9833004402", "Inpatient Store"),
            buildPharmacist(303, "Bruce Wayne", "bruce.w@hospital.com", "9833004403", "Main Store")
        );

        for (Pharmacist p : rawPharms) {
            Pharmacist saved = pharmacistRepository.save(p);
            String pharmIdStr = "PHARM-" + saved.getPharmacistsid();
            userRepository.save(buildUser(pharmIdStr, saved.getEmail(), "pharm123", "PHARMACIST", saved.getName(), saved.getContact(), saved.getAddress()));
        }

        // 5. 10 PATIENTS (Patient uses natural key PAT-xxxx)
        List<Patient> patients = List.of(
            buildPatient("PAT-1001", "pat123", "John Doe", "Male", "9844005501", "Maple St 10", "Hypertension", "Room 101"),
            buildPatient("PAT-1002", "pat123", "Jane Smith", "Female", "9844005502", "Oak Ave 22", "Type 2 Diabetes", "Room 102"),
            buildPatient("PAT-1003", "pat123", "Michael Clark", "Male", "9844005503", "Pine Road 45", "Fractured Femur", "Room 103"),
            buildPatient("PAT-1004", "pat123", "Emily Watson", "Female", "9844005504", "Cedar Lane 12", "Asthma Attack", "Room 104"),
            buildPatient("PAT-1005", "pat123", "George King", "Male", "9844005505", "Birch Blvd 88", "Gastric Ulcer", "Room 105"),
            buildPatient("PAT-1006", "pat123", "Alice Cooper", "Female", "9844005506", "Willow Way 3", "Migraine Severe", "Room 106"),
            buildPatient("PAT-1007", "pat123", "Thomas Wright", "Male", "9844005507", "Elm St 19", "Pneumonia", "Room 107"),
            buildPatient("PAT-1008", "pat123", "Olivia Brown", "Female", "9844005508", "Spruce Court 7", "Kidney Stones", "Room 108"),
            buildPatient("PAT-1009", "pat123", "William Davies", "Male", "9844005509", "Ash Grove 61", "Arrhythmia", "Room 109"),
            buildPatient("PAT-1010", "pat123", "Sophia Martinez", "Female", "9844005510", "Redwood Pl 14", "Appendicitis", "Room 110")
        );
        for (Patient pt : patients) {
            patientRepository.save(pt);
            userRepository.save(buildUser(pt.getPatientId(), pt.getPatientId().toLowerCase() + "@hospital.com", pt.getPassword(), "PATIENT", pt.getPatientname(), pt.getContact(), pt.getAddress()));
        }

        // 6. MEDICINES (150+ records)
        String[][] rawMeds = {
            {"Paracetamol 500mg", "Analgesic", "Acetaminophen, Dolo 650", "250", "2.50"},
            {"Paracetamol 650mg", "Analgesic", "Dolo 650, Calpol 650", "400", "3.00"},
            {"Ibuprofen 200mg", "NSAID", "Advil, Motrin", "300", "4.20"},
            {"Ibuprofen 400mg", "NSAID", "Brufen 400, Naproxen", "200", "5.50"},
            {"Naproxen 250mg", "NSAID", "Aleve, Naprosyn", "150", "7.00"},
            {"Diclofenac 50mg", "NSAID", "Voltaren, Voveran", "280", "4.00"},
            {"Aceclofenac 100mg", "NSAID", "Zerodol, Hifenac", "190", "6.50"},
            {"Tramadol 50mg", "Opioid Analgesic", "Ultram, Tapentadol", "80", "14.00"},
            {"Ketorolac 10mg", "NSAID", "Toradol, Ketanov", "90", "11.50"},
            {"Aspirin 75mg", "Antiplatelet", "Ecosprin 75, Disprin", "500", "1.80"},
            {"Amoxicillin 250mg", "Antibiotic", "Mox 250, Augmentin", "220", "8.00"},
            {"Amoxicillin 500mg", "Antibiotic", "Mox 500, Amoxil", "340", "12.00"},
            {"Amoxicillin + Clavulanate 625mg", "Antibiotic", "Augmentin 625, Clavam 625", "250", "18.50"},
            {"Azithromycin 250mg", "Macrolide", "Zithromax, Azithral 250", "180", "15.00"},
            {"Azithromycin 500mg", "Macrolide", "Azithral 500, Azee 500", "230", "22.00"},
            {"Ciprofloxacin 500mg", "Fluoroquinolone", "Cipro 500, Ciplox 500", "190", "11.00"},
            {"Levofloxacin 500mg", "Fluoroquinolone", "Levaquin, Loxof 500", "140", "16.50"},
            {"Ofloxacin 200mg", "Fluoroquinolone", "Floxin, Oflomac 200", "160", "9.50"},
            {"Cefixime 200mg", "Cephalosporin", "Suprax, Zifi 200", "210", "19.00"},
            {"Cefuroxime 500mg", "Cephalosporin", "Ceftin, Ceftum 500", "120", "32.00"},
            {"Cephalexin 500mg", "Cephalosporin", "Keflex, Phexin 500", "130", "14.50"},
            {"Doxycycline 100mg", "Tetracycline", "Vibramycin, Doxypal", "175", "7.50"},
            {"Metronidazole 400mg", "Antiprotozoal", "Flagyl 400, Metrogyl", "310", "4.50"},
            {"Clindamycin 300mg", "Lincosamide", "Cleocin, Dalacin C", "95", "24.00"},
            {"Nitrofurantoin 100mg", "Antibiotic", "Macrobid, Furadantin", "110", "13.00"},
            {"Amlodipine 5mg", "Calcium Blocker", "Norvasc, Amlong 5", "350", "4.50"},
            {"Amlodipine 10mg", "Calcium Blocker", "Norvasc 10, Stamlo", "200", "6.00"},
            {"Atenolol 50mg", "Beta Blocker", "Tenormin, Betacard", "220", "5.00"},
            {"Metoprolol 25mg", "Beta Blocker", "Lopressor, Betaloc 25", "280", "6.50"},
            {"Metoprolol 50mg", "Beta Blocker", "Toprol XL, Metolar 50", "260", "8.00"},
            {"Losartan 50mg", "ARB", "Cozaar, Losacar 50", "310", "7.50"},
            {"Telmisartan 40mg", "ARB", "Micardis, Telma 40", "400", "8.50"},
            {"Telmisartan 80mg", "ARB", "Micardis 80, Telmikind 80", "180", "12.00"},
            {"Enalapril 5mg", "ACE Inhibitor", "Vasotec, Envas 5", "160", "5.50"},
            {"Ramipril 2.5mg", "ACE Inhibitor", "Altace, Cardace 2.5", "210", "7.00"},
            {"Ramipril 5mg", "ACE Inhibitor", "Altace 5, Cardace 5", "190", "9.00"},
            {"Hydrochlorothiazide 12.5mg", "Diuretic", "Microzide, Aquazide", "250", "3.50"},
            {"Furosemide 40mg", "Loop Diuretic", "Lasix, Frusenex", "300", "3.00"},
            {"Spironolactone 25mg", "Diuretic", "Aldactone, Spiroctan", "140", "8.50"},
            {"Clopidogrel 75mg", "Antiplatelet", "Plavix, Deplatt 75", "270", "14.00"},
            {"Atorvastatin 10mg", "Statin", "Lipitor 10, Atorva 10", "350", "9.00"},
            {"Atorvastatin 20mg", "Statin", "Lipitor 20, Storvas 20", "320", "14.50"},
            {"Rosuvastatin 10mg", "Statin", "Crestor, Rosuvas 10", "280", "13.00"},
            {"Rosuvastatin 20mg", "Statin", "Crestor 20, Razel 20", "170", "21.00"},
            {"Nitroglycerin 0.4mg", "Nitrate", "Nitrostat, Angispan", "90", "18.00"},
            {"Metformin 500mg", "Biguanide", "Glucophage, Glycomet 500", "600", "3.20"},
            {"Metformin 850mg", "Biguanide", "Glucophage 850, Formin", "400", "4.50"},
            {"Metformin 1000mg SR", "Biguanide", "Glucophage XR, Glycomet SR", "380", "5.80"},
            {"Glimepiride 1mg", "Sulfonylurea", "Amaryl 1, Glimestar 1", "240", "5.00"},
            {"Glimepiride 2mg", "Sulfonylurea", "Amaryl 2, Zoryl 2", "290", "7.20"},
            {"Gliclazide 80mg", "Sulfonylurea", "Diamicron, Reclimet", "160", "8.00"},
            {"Sitagliptin 50mg", "DPP-4 Inhibitor", "Januvia 50, Istavel", "190", "25.00"},
            {"Sitagliptin 100mg", "DPP-4 Inhibitor", "Januvia 100, Zita 100", "210", "38.00"},
            {"Vildagliptin 50mg", "DPP-4 Inhibitor", "Galvus, Jalra 50", "180", "22.00"},
            {"Dapagliflozin 10mg", "SGLT2 Inhibitor", "Farxiga, Forxiga 10", "150", "42.00"},
            {"Empagliflozin 10mg", "SGLT2 Inhibitor", "Jardiance 10, Gibtulio", "130", "45.00"},
            {"Levothyroxine 25mcg", "Thyroid", "Synthroid 25, Thyronorm 25", "220", "4.00"},
            {"Levothyroxine 50mcg", "Thyroid", "Synthroid 50, Eltroxin 50", "310", "4.80"},
            {"Levothyroxine 100mcg", "Thyroid", "Synthroid 100, Thyronorm 100", "280", "5.50"},
            {"Human Insulin Regular 100IU", "Insulin", "Humulin R, Actrapid", "85", "95.00"},
            {"Human Insulin NPH 100IU", "Insulin", "Humulin N, Insulatard", "75", "105.00"},
            {"Omeprazole 20mg", "PPI", "Prilosec, Omez 20", "450", "4.50"},
            {"Pantoprazole 40mg", "PPI", "Protonix, Pan 40", "520", "6.00"},
            {"Rabeprazole 20mg", "PPI", "Aciphex, Razo 20", "310", "7.80"},
            {"Esomeprazole 40mg", "PPI", "Nexium, Nexpro 40", "260", "9.20"},
            {"Ranitidine 150mg", "H2 Blocker", "Zantac 150, Rantac 150", "300", "2.50"},
            {"Famotidine 20mg", "H2 Blocker", "Pepcid, Famocid 20", "220", "3.80"},
            {"Domperidone 10mg", "Antiemetic", "Motilium, Vomistop", "280", "3.50"},
            {"Ondansetron 4mg", "Antiemetic", "Zofran 4, Emeset 4", "320", "6.20"},
            {"Ondansetron 8mg", "Antiemetic", "Zofran 8, Vomikind 8", "210", "9.00"},
            {"Metoclopramide 10mg", "Antiemetic", "Reglan, Perinorm", "180", "2.80"},
            {"Sucralfate 1000mg Suspension", "Protectant", "Carafate, Sucrafil", "120", "16.00"},
            {"Lactulose Solution 15ml", "Laxative", "Duphalac, Constulose", "160", "21.00"},
            {"Bisacodyl 5mg", "Laxative", "Dulcolax, Julax", "240", "2.00"},
            {"Loperamide 2mg", "Antidiarrheal", "Imodium, Lopamide", "350", "3.00"},
            {"Dicyclomine 20mg", "Antispasmodic", "Bentyl, Meftal Spas", "200", "4.00"},
            {"Cetirizine 10mg", "Antihistamine", "Zyrtec, Cetzine 10", "480", "2.20"},
            {"Levocetirizine 5mg", "Antihistamine", "Xyzal, Levocet 5", "380", "3.50"},
            {"Loratadine 10mg", "Antihistamine", "Claritin, Lorfast 10", "260", "3.00"},
            {"Fexofenadine 120mg", "Antihistamine", "Allegra 120, Fexo 120", "220", "11.50"},
            {"Fexofenadine 180mg", "Antihistamine", "Allegra 180, Telfast", "190", "15.00"},
            {"Montelukast 10mg", "Leukotriene Blocker", "Singulair, Montair 10", "300", "12.00"},
            {"Salbutamol Inhaler 100mcg", "Bronchodilator", "Ventolin, Asthalin", "150", "28.00"},
            {"Budesonide Inhaler 200mcg", "Corticosteroid", "Pulmicort, Budecort", "110", "42.00"},
            {"Fluticasone Nasal Spray", "Corticosteroid", "Flonase, Flomist", "95", "34.00"},
            {"Dextromethorphan Syrup 10mg", "Cough Suppressant", "Robitussin, Benadryl DR", "180", "8.50"},
            {"Ambroxol 30mg", "Mucolytic", "Mucosolvan, Ambrolite", "210", "4.00"},
            {"Acetylcysteine 600mg", "Mucolytic", "Mucomyst, Nac 600", "140", "18.00"},
            {"Alprazolam 0.25mg", "Benzodiazepine", "Xanax, Alprax 0.25", "120", "4.50"},
            {"Alprazolam 0.5mg", "Benzodiazepine", "Xanax 0.5, Restyl 0.5", "140", "6.00"},
            {"Clonazepam 0.5mg", "Anticonvulsant", "Klonopin, Clonafit", "160", "5.20"},
            {"Diazepam 5mg", "Benzodiazepine", "Valium, Calmpose", "110", "4.00"},
            {"Lorazepam 1mg", "Benzodiazepine", "Ativan, Lopez 1", "130", "4.80"},
            {"Escitalopram 10mg", "Antidepressant", "Lexapro, Nexito 10", "240", "11.00"},
            {"Sertraline 50mg", "Antidepressant", "Zoloft, Daxid 50", "190", "13.50"},
            {"Fluoxetine 20mg", "Antidepressant", "Prozac, Flunil 20", "160", "8.50"},
            {"Duloxetine 30mg", "Antidepressant", "Cymbalta, Duzela 30", "130", "19.00"},
            {"Gabapentin 300mg", "Anticonvulsant", "Neurontin, Gabapin 300", "170", "16.00"},
            {"Pregabalin 75mg", "Neuropathic", "Lyrica, Maxgalin 75", "180", "22.00"},
            {"Levetiracetam 500mg", "Antiepileptic", "Keppra, Levera 500", "150", "28.00"},
            {"Carbamazepine 200mg", "Antiepileptic", "Tegretol, Mazetol 200", "140", "6.50"},
            {"Sodium Valproate 500mg", "Antiepileptic", "Depakote, Encorate 500", "160", "14.00"},
            {"Baclofen 10mg", "Muscle Relaxant", "Lioresal, Liofen 10", "120", "8.00"},
            {"Tizanidine 2mg", "Muscle Relaxant", "Zanaflex, Sirdalud 2", "110", "7.50"},
            {"Betamethasone Cream 0.05%", "Corticosteroid", "Diprosone, Betnovate", "160", "6.50"},
            {"Hydrocortisone Cream 1%", "Corticosteroid", "Cortaid, H-Cort", "190", "4.50"},
            {"Clobetasol Ointment 0.05%", "Corticosteroid", "Temovate, Tenovate", "130", "9.80"},
            {"Clotrimazole Cream 1%", "Antifungal", "Lotrimin, Candid", "240", "5.00"},
            {"Miconazole Cream 2%", "Antifungal", "Monistat, Micogel", "180", "5.50"},
            {"Terbinafine 250mg Tablet", "Antifungal", "Lamisil, Sebifin 250", "140", "24.00"},
            {"Fluconazole 150mg", "Antifungal", "Diflucan, Forcan 150", "250", "7.50"},
            {"Mupirocin Ointment 2%", "Antibiotic Topical", "Bactroban, T-Bact", "170", "14.00"},
            {"Silver Sulfadiazine Cream 1%", "Burn Antibacterial", "Silvadene, Burnol", "110", "11.00"},
            {"Permethrin Lotion 5%", "Scabicide", "Elimite, Scaboma", "85", "12.50"},
            {"Ciprofloxacin Eye Drops", "Ophthalmic", "Ciloxan, Ciplox Eye", "150", "4.50"},
            {"Tobramycin Eye Drops", "Ophthalmic", "Tobrex, Tobacin", "120", "6.00"},
            {"Artificial Tears Drops", "Lubricant", "Refresh Tears, Plus", "220", "8.50"},
            {"Olopatadine Eye Drops", "Antiallergic", "Patanol, Olopat", "95", "14.00"},
            {"Chloramphenicol Ear Drops", "Otic", "Chloromycetin, Otic-C", "110", "5.00"},
            {"Vitamin C 500mg Chewable", "Supplement", "Limcee, Celin 500", "600", "1.50"},
            {"Vitamin D3 60000 IU", "Supplement", "Calcirol, D-Rise", "400", "12.00"},
            {"Calcium + Vitamin D3", "Mineral", "Caltrate, Shelcal 500", "500", "5.50"},
            {"Ferrous Sulfate 200mg", "Hematinic", "Fefol, Orofer", "350", "4.00"},
            {"Folic Acid 5mg", "Vitamin B9", "Folvite 5, Folgard", "450", "2.00"},
            {"Vitamin B-Complex + Zinc", "Multivitamin", "Becosules, Cobadex", "550", "3.50"},
            {"Methylcobalamin 1500mcg", "Vitamin B12", "Neurobion, Nurokind", "300", "8.50"},
            {"Zinc Sulfate 50mg", "Mineral", "Zinconia, Zincovit", "400", "2.50"},
            {"Potassium Chloride 600mg", "Electrolyte", "K-Dur, Potclor", "180", "7.00"},
            {"Oral Rehydration Salts (ORS)", "Electrolyte", "Electral, Pedialyte", "700", "1.20"},
            {"Prednisolone 5mg", "Steroid", "Prelone, Omnacortil 5", "320", "2.80"},
            {"Prednisolone 20mg", "Steroid", "Prelone 20, Omnacortil 20", "240", "6.00"},
            {"Methylprednisolone 4mg", "Steroid", "Medrol, Depo-Medrol", "190", "8.50"},
            {"Methylprednisolone 16mg", "Steroid", "Medrol 16, Zempred 16", "140", "18.00"},
            {"Dexamethasone 0.5mg", "Steroid", "Decadron, Dexona 0.5", "350", "1.80"},
            {"Dexamethasone 4mg Injection", "Steroid Injectable", "Decadron IV, Dexona Inj", "200", "4.20"},
            {"Hydrocortisone 100mg Injection", "Steroid Injectable", "Solu-Cortef, Efcorlin", "130", "25.00"},
            {"Adrenaline 1mg/ml (Epinephrine)", "Emergency", "EpiPen, Vasodrine", "90", "35.00"},
            {"Atropine Sulfate 0.6mg/ml", "Emergency", "Atropen", "110", "12.00"},
            {"Amiodarone 150mg Injection", "Antiarrhythmic", "Cordarone, Pacerone", "70", "45.00"},
            {"Heparin 5000 IU/ml", "Anticoagulant", "Hep-Lock", "80", "65.00"},
            {"Enoxaparin 40mg Injection", "Anticoagulant", "Lovenox, Clexane 40", "120", "82.00"},
            {"Warfarin 2mg", "Anticoagulant", "Coumadin, Uniwarfin 2", "150", "4.00"},
            {"Warfarin 5mg", "Anticoagulant", "Coumadin 5, Warf 5", "130", "5.50"},
            {"Naloxone 0.4mg Injection", "Opioid Antagonist", "Narcan", "50", "75.00"},
            {"Tranexamic Acid 500mg", "Antifibrinolytic", "Cyklokapron, Pause 500", "160", "14.50"},
            {"Dextrose 25% 100ml Infusion", "IV Fluid", "D25 IV Infusion", "140", "15.00"},
            {"Normal Saline 0.9% 500ml", "IV Fluid", "0.9% NaCl Infusion", "400", "8.00"},
            {"Ringer Lactate 500ml Infusion", "IV Fluid", "Hartmann Solution, RL IV", "350", "9.50"}
        };

        for (String[] row : rawMeds) {
            Medicine m = new Medicine();
            m.setMedicineName(row[0]);
            m.setGenericCategory(row[1]);
            m.setAlternatives(row[2]);
            m.setStockQuantity(Integer.parseInt(row[3]));
            m.setUnitPrice(Double.parseDouble(row[4]));
            medicineRepository.save(m);
        }

        System.out.println(">> Database Initialization Completed Successfully.");
    }
}