import sqlite3

conn = sqlite3.connect('hospital.db')
cur = conn.cursor()

# Ensure tables exist
cur.execute('''CREATE TABLE IF NOT EXISTS register (
    username TEXT PRIMARY KEY, password TEXT, role TEXT, fullName TEXT, email TEXT, contact TEXT, address TEXT
)''')
cur.execute('''CREATE TABLE IF NOT EXISTS adddoctor (
    doctorid INTEGER PRIMARY KEY, doctorname TEXT, gender TEXT, specialist TEXT, contact TEXT, address TEXT
)''')
cur.execute('''CREATE TABLE IF NOT EXISTS addnurse (
    nurseid INTEGER PRIMARY KEY, name TEXT, email TEXT, contact TEXT, address TEXT
)''')
cur.execute('''CREATE TABLE IF NOT EXISTS addpharmacists (
    pharmacistsid INTEGER PRIMARY KEY, name TEXT, email TEXT, contact TEXT, address TEXT
)''')
cur.execute('''CREATE TABLE IF NOT EXISTS addpatient (
    id TEXT PRIMARY KEY, password TEXT, patientname TEXT, gender TEXT, contact TEXT, address TEXT, disease TEXT, roomno TEXT
)''')
cur.execute('''CREATE TABLE IF NOT EXISTS medicines (
    id INTEGER PRIMARY KEY AUTOINCREMENT, medicineName TEXT, genericCategory TEXT, alternatives TEXT, stockQuantity INTEGER, unitPrice REAL
)''')

# 1. 10 DOCTORS
doctors = [
    (101, "Dr. Sarah Jenkins", "Female", "Cardiology", "9811002201", "Cardiac Wing A", "DOC-101", "doc123"),
    (102, "Dr. Robert Chen", "Male", "Neurology", "9811002202", "Neuro Clinic B", "DOC-102", "doc123"),
    (103, "Dr. Emily Taylor", "Female", "Pediatrics", "9811002203", "Children Ward 1", "DOC-103", "doc123"),
    (104, "Dr. Marcus Vance", "Male", "Orthopedics", "9811002204", "Ortho Center", "DOC-104", "doc123"),
    (105, "Dr. Anita Desai", "Female", "Dermatology", "9811002205", "Skin Care Block", "DOC-105", "doc123"),
    (106, "Dr. William Scott", "Male", "Oncology", "9811002206", "Cancer Center 3", "DOC-106", "doc123"),
    (107, "Dr. Lisa Wong", "Female", "Gynecology", "9811002207", "Maternity Wing", "DOC-107", "doc123"),
    (108, "Dr. David Miller", "Male", "Psychiatry", "9811002208", "Behavioral Unit", "DOC-108", "doc123"),
    (109, "Dr. Priya Nair", "Female", "Endocrinology", "9811002209", "Metabolic Clinic", "DOC-109", "doc123"),
    (110, "Dr. Kevin Brown", "Male", "Gastroenterology", "9811002210", "Digestive Health", "DOC-110", "doc123")
]
for d in doctors:
    cur.execute("INSERT OR REPLACE INTO adddoctor (doctorid, doctorname, gender, specialist, contact, address) VALUES (?, ?, ?, ?, ?, ?)", d[:6])
    cur.execute("INSERT OR REPLACE INTO register (username, password, role, fullName, email, contact, address) VALUES (?, ?, ?, ?, ?, ?, ?)", 
                (d[6], d[7], "DOCTOR", d[1], f"{d[6].lower()}@hospital.com", d[4], d[5]))

# 2. 12 NURSES
nurses = [
    (201, "Nurse Clara Barton", "clara@hospital.com", "9822003301", "Staff Quarters 1", "NUR-201", "nurse123"),
    (202, "Nurse James Wilson", "james@hospital.com", "9822003302", "Staff Quarters 2", "NUR-202", "nurse123"),
    (203, "Nurse Rachel Adams", "rachel@hospital.com", "9822003303", "Staff Quarters 3", "NUR-203", "nurse123"),
    (204, "Nurse Hannah Scott", "hannah@hospital.com", "9822003304", "Staff Quarters 4", "NUR-204", "nurse123"),
    (205, "Nurse Samuel Green", "samuel@hospital.com", "9822003305", "Staff Quarters 5", "NUR-205", "nurse123"),
    (206, "Nurse Chloe Bennett", "chloe@hospital.com", "9822003306", "Staff Quarters 6", "NUR-206", "nurse123"),
    (207, "Nurse Daniel Craig", "daniel@hospital.com", "9822003307", "Staff Quarters 7", "NUR-207", "nurse123"),
    (208, "Nurse Grace Kelly", "grace@hospital.com", "9822003308", "Staff Quarters 8", "NUR-208", "nurse123"),
    (209, "Nurse Ethan Hunt", "ethan@hospital.com", "9822003309", "Staff Quarters 9", "NUR-209", "nurse123"),
    (210, "Nurse Sophia Loren", "sophia@hospital.com", "9822003310", "Staff Quarters 10", "NUR-210", "nurse123"),
    (211, "Nurse Lucas Gray", "lucas@hospital.com", "9822003311", "Staff Quarters 11", "NUR-211", "nurse123"),
    (212, "Nurse Mia Thorne", "mia@hospital.com", "9822003312", "Staff Quarters 12", "NUR-212", "nurse123")
]
for n in nurses:
    cur.execute("INSERT OR REPLACE INTO addnurse (nurseid, name, email, contact, address) VALUES (?, ?, ?, ?, ?)", n[:5])
    cur.execute("INSERT OR REPLACE INTO register (username, password, role, fullName, email, contact, address) VALUES (?, ?, ?, ?, ?, ?, ?)", 
                (n[5], n[6], "NURSE", n[1], n[2], n[3], n[4]))

# 3. 3 PHARMACISTS
pharmacists = [
    (301, "Pharmacist Alex Miller", "alex.m@hospital.com", "9833004401", "Counter 1", "PHARM-301", "pharm123"),
    (302, "Pharmacist Laura Croft", "laura.c@hospital.com", "9833004402", "Inpatient Store", "PHARM-302", "pharm123"),
    (303, "Pharmacist Bruce Wayne", "bruce.w@hospital.com", "9833004403", "Main Store", "PHARM-303", "pharm123")
]
for ph in pharmacists:
    cur.execute("INSERT OR REPLACE INTO addpharmacists (pharmacistsid, name, email, contact, address) VALUES (?, ?, ?, ?, ?)", ph[:5])
    cur.execute("INSERT OR REPLACE INTO register (username, password, role, fullName, email, contact, address) VALUES (?, ?, ?, ?, ?, ?, ?)", 
                (ph[5], ph[6], "PHARMACIST", ph[1], ph[2], ph[3], ph[4]))

# 4. 10 PATIENTS
patients = [
    ("PAT-1001", "pat123", "John Doe", "Male", "9844005501", "Maple St 10", "Hypertension", "Room 101"),
    ("PAT-1002", "pat123", "Jane Smith", "Female", "9844005502", "Oak Ave 22", "Type 2 Diabetes", "Room 102"),
    ("PAT-1003", "pat123", "Michael Clark", "Male", "9844005503", "Pine Road 45", "Fractured Femur", "Room 103"),
    ("PAT-1004", "pat123", "Emily Watson", "Female", "9844005504", "Cedar Lane 12", "Asthma Attack", "Room 104"),
    ("PAT-1005", "pat123", "George King", "Male", "9844005505", "Birch Blvd 88", "Gastric Ulcer", "Room 105"),
    ("PAT-1006", "pat123", "Alice Cooper", "Female", "9844005506", "Willow Way 3", "Migraine Severe", "Room 106"),
    ("PAT-1007", "pat123", "Thomas Wright", "Male", "9844005507", "Elm St 19", "Pneumonia", "Room 107"),
    ("PAT-1008", "pat123", "Olivia Brown", "Female", "9844005508", "Spruce Court 7", "Kidney Stones", "Room 108"),
    ("PAT-1009", "pat123", "William Davies", "Male", "9844005509", "Ash Grove 61", "Arrhythmia", "Room 109"),
    ("PAT-1010", "pat123", "Sophia Martinez", "Female", "9844005510", "Redwood Pl 14", "Appendicitis", "Room 110")
]
for p in patients:
    cur.execute("INSERT OR REPLACE INTO addpatient (id, password, patientname, gender, contact, address, disease, roomno) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", p)
    cur.execute("INSERT OR REPLACE INTO register (username, password, role, fullName, email, contact, address) VALUES (?, ?, ?, ?, ?, ?, ?)", 
                (p[0], p[1], "PATIENT", p[2], f"{p[0].lower()}@hospital.com", p[4], p[5]))

# 5. ADMIN ACCOUNT
cur.execute("INSERT OR REPLACE INTO register (username, password, role, fullName, email, contact, address) VALUES (?, ?, ?, ?, ?, ?, ?)", 
            ("ADM-001", "admin123", "ADMIN", "System Administrator", "admin@hospital.com", "9999999999", "Admin Suite"))

# 6. 160 MEDICINES
cur.execute("DELETE FROM medicines")

catalog = [
    ("Paracetamol 500mg", "Analgesic", "Acetaminophen, Dolo 650", 250, 2.50),
    ("Paracetamol 650mg", "Analgesic", "Dolo 650, Calpol 650", 400, 3.00),
    ("Ibuprofen 200mg", "NSAID", "Advil, Motrin", 300, 4.20),
    ("Ibuprofen 400mg", "NSAID", "Brufen 400, Naproxen", 200, 5.50),
    ("Naproxen 250mg", "NSAID", "Aleve, Naprosyn", 150, 7.00),
    ("Diclofenac 50mg", "NSAID", "Voltaren, Voveran", 280, 4.00),
    ("Aceclofenac 100mg", "NSAID", "Zerodol, Hifenac", 190, 6.50),
    ("Tramadol 50mg", "Opioid Analgesic", "Ultram, Tapentadol", 80, 14.00),
    ("Ketorolac 10mg", "NSAID", "Toradol, Ketanov", 90, 11.50),
    ("Aspirin 75mg", "Antiplatelet", "Ecosprin 75, Disprin", 500, 1.80),
    ("Amoxicillin 250mg", "Antibiotic", "Mox 250, Augmentin", 220, 8.00),
    ("Amoxicillin 500mg", "Antibiotic", "Mox 500, Amoxil", 340, 12.00),
    ("Amoxicillin + Clavulanate 625mg", "Antibiotic", "Augmentin 625, Clavam 625", 250, 18.50),
    ("Azithromycin 250mg", "Macrolide", "Zithromax, Azithral 250", 180, 15.00),
    ("Azithromycin 500mg", "Macrolide", "Azithral 500, Azee 500", 230, 22.00),
    ("Ciprofloxacin 500mg", "Fluoroquinolone", "Cipro 500, Ciplox 500", 190, 11.00),
    ("Levofloxacin 500mg", "Fluoroquinolone", "Levaquin, Loxof 500", 140, 16.50),
    ("Ofloxacin 200mg", "Fluoroquinolone", "Floxin, Oflomac 200", 160, 9.50),
    ("Cefixime 200mg", "Cephalosporin", "Suprax, Zifi 200", 210, 19.00),
    ("Cefuroxime 500mg", "Cephalosporin", "Ceftin, Ceftum 500", 120, 32.00),
    ("Cephalexin 500mg", "Cephalosporin", "Keflex, Phexin 500", 130, 14.50),
    ("Doxycycline 100mg", "Tetracycline", "Vibramycin, Doxypal", 175, 7.50),
    ("Metronidazole 400mg", "Antiprotozoal", "Flagyl 400, Metrogyl", 310, 4.50),
    ("Clindamycin 300mg", "Lincosamide", "Cleocin, Dalacin C", 95, 24.00),
    ("Nitrofurantoin 100mg", "Antibiotic", "Macrobid, Furadantin", 110, 13.00),
    ("Amlodipine 5mg", "Calcium Blocker", "Norvasc, Amlong 5", 350, 4.50),
    ("Amlodipine 10mg", "Calcium Blocker", "Norvasc 10, Stamlo", 200, 6.00),
    ("Atenolol 50mg", "Beta Blocker", "Tenormin, Betacard", 220, 5.00),
    ("Metoprolol 25mg", "Beta Blocker", "Lopressor, Betaloc 25", 280, 6.50),
    ("Metoprolol 50mg", "Beta Blocker", "Toprol XL, Metolar 50", 260, 8.00),
    ("Losartan 50mg", "ARB", "Cozaar, Losacar 50", 310, 7.50),
    ("Telmisartan 40mg", "ARB", "Micardis, Telma 40", 400, 8.50),
    ("Telmisartan 80mg", "ARB", "Micardis 80, Telmikind 80", 180, 12.00),
    ("Enalapril 5mg", "ACE Inhibitor", "Vasotec, Envas 5", 160, 5.50),
    ("Ramipril 2.5mg", "ACE Inhibitor", "Altace, Cardace 2.5", 210, 7.00),
    ("Ramipril 5mg", "ACE Inhibitor", "Altace 5, Cardace 5", 190, 9.00),
    ("Hydrochlorothiazide 12.5mg", "Diuretic", "Microzide, Aquazide", 250, 3.50),
    ("Furosemide 40mg", "Loop Diuretic", "Lasix, Frusenex", 300, 3.00),
    ("Spironolactone 25mg", "Diuretic", "Aldactone, Spiroctan", 140, 8.50),
    ("Clopidogrel 75mg", "Antiplatelet", "Plavix, Deplatt 75", 270, 14.00),
    ("Atorvastatin 10mg", "Statin", "Lipitor 10, Atorva 10", 350, 9.00),
    ("Atorvastatin 20mg", "Statin", "Lipitor 20, Storvas 20", 320, 14.50),
    ("Rosuvastatin 10mg", "Statin", "Crestor, Rosuvas 10", 280, 13.00),
    ("Rosuvastatin 20mg", "Statin", "Crestor 20, Razel 20", 170, 21.00),
    ("Nitroglycerin 0.4mg", "Nitrate", "Nitrostat, Angispan", 90, 18.00),
    ("Metformin 500mg", "Biguanide", "Glucophage, Glycomet 500", 600, 3.20),
    ("Metformin 850mg", "Biguanide", "Glucophage 850, Formin", 400, 4.50),
    ("Metformin 1000mg SR", "Biguanide", "Glucophage XR, Glycomet SR", 380, 5.80),
    ("Glimepiride 1mg", "Sulfonylurea", "Amaryl 1, Glimestar 1", 240, 5.00),
    ("Glimepiride 2mg", "Sulfonylurea", "Amaryl 2, Zoryl 2", 290, 7.20),
    ("Gliclazide 80mg", "Sulfonylurea", "Diamicron, Reclimet", 160, 8.00),
    ("Sitagliptin 50mg", "DPP-4 Inhibitor", "Januvia 50, Istavel", 190, 25.00),
    ("Sitagliptin 100mg", "DPP-4 Inhibitor", "Januvia 100, Zita 100", 210, 38.00),
    ("Vildagliptin 50mg", "DPP-4 Inhibitor", "Galvus, Jalra 50", 180, 22.00),
    ("Dapagliflozin 10mg", "SGLT2 Inhibitor", "Farxiga, Forxiga 10", 150, 42.00),
    ("Empagliflozin 10mg", "SGLT2 Inhibitor", "Jardiance 10, Gibtulio", 130, 45.00),
    ("Levothyroxine 25mcg", "Thyroid", "Synthroid 25, Thyronorm 25", 220, 4.00),
    ("Levothyroxine 50mcg", "Thyroid", "Synthroid 50, Eltroxin 50", 310, 4.80),
    ("Levothyroxine 100mcg", "Thyroid", "Synthroid 100, Thyronorm 100", 280, 5.50),
    ("Human Insulin Regular 100IU", "Insulin", "Humulin R, Actrapid", 85, 95.00),
    ("Human Insulin NPH 100IU", "Insulin", "Humulin N, Insulatard", 75, 105.00),
    ("Omeprazole 20mg", "PPI", "Prilosec, Omez 20", 450, 4.50),
    ("Pantoprazole 40mg", "PPI", "Protonix, Pan 40", 520, 6.00),
    ("Rabeprazole 20mg", "PPI", "Aciphex, Razo 20", 310, 7.80),
    ("Esomeprazole 40mg", "PPI", "Nexium, Nexpro 40", 260, 9.20),
    ("Ranitidine 150mg", "H2 Blocker", "Zantac 150, Rantac 150", 300, 2.50),
    ("Famotidine 20mg", "H2 Blocker", "Pepcid, Famocid 20", 220, 3.80),
    ("Domperidone 10mg", "Antiemetic", "Motilium, Vomistop", 280, 3.50),
    ("Ondansetron 4mg", "Antiemetic", "Zofran 4, Emeset 4", 320, 6.20),
    ("Ondansetron 8mg", "Antiemetic", "Zofran 8, Vomikind 8", 210, 9.00),
    ("Metoclopramide 10mg", "Antiemetic", "Reglan, Perinorm", 180, 2.80),
    ("Sucralfate 1000mg Suspension", "Protectant", "Carafate, Sucrafil", 120, 16.00),
    ("Lactulose 10g/15ml Solution", "Laxative", "Duphalac, Constulose", 160, 21.00),
    ("Bisacodyl 5mg", "Laxative", "Dulcolax, Julax", 240, 2.00),
    ("Loperamide 2mg", "Antidiarrheal", "Imodium, Lopamide", 350, 3.00),
    ("Dicyclomine 20mg", "Antispasmodic", "Bentyl, Meftal Spas", 200, 4.00),
    ("Cetirizine 10mg", "Antihistamine", "Zyrtec, Cetzine 10", 480, 2.20),
    ("Levocetirizine 5mg", "Antihistamine", "Xyzal, Levocet 5", 380, 3.50),
    ("Loratadine 10mg", "Antihistamine", "Claritin, Lorfast 10", 260, 3.00),
    ("Fexofenadine 120mg", "Antihistamine", "Allegra 120, Fexo 120", 220, 11.50),
    ("Fexofenadine 180mg", "Antihistamine", "Allegra 180, Telfast", 190, 15.00),
    ("Montelukast 10mg", "Leukotriene Blocker", "Singulair, Montair 10", 300, 12.00),
    ("Salbutamol 100mcg Inhaler", "Bronchodilator", "Ventolin, Asthalin", 150, 28.00),
    ("Budesonide 200mcg Inhaler", "Corticosteroid", "Pulmicort, Budecort", 110, 42.00),
    ("Fluticasone Spray", "Corticosteroid", "Flonase, Flomist", 95, 34.00),
    ("Dextromethorphan Syrup", "Cough Suppressant", "Robitussin, Benadryl DR", 180, 8.50),
    ("Ambroxol 30mg Tablet", "Mucolytic", "Mucosolvan, Ambrolite", 210, 4.00),
    ("Acetylcysteine 600mg", "Mucolytic", "Mucomyst, Nac 600", 140, 18.00),
    ("Alprazolam 0.25mg", "Benzodiazepine", "Xanax, Alprax 0.25", 120, 4.50),
    ("Alprazolam 0.5mg", "Benzodiazepine", "Xanax 0.5, Restyl 0.5", 140, 6.00),
    ("Clonazepam 0.5mg", "Anticonvulsant", "Klonopin, Clonafit", 160, 5.20),
    ("Diazepam 5mg", "Benzodiazepine", "Valium, Calmpose", 110, 4.00),
    ("Lorazepam 1mg", "Benzodiazepine", "Ativan, Lopez 1", 130, 4.80),
    ("Escitalopram 10mg", "Antidepressant", "Lexapro, Nexito 10", 240, 11.00),
    ("Sertraline 50mg", "Antidepressant", "Zoloft, Daxid 50", 190, 13.50),
    ("Fluoxetine 20mg", "Antidepressant", "Prozac, Flunil 20", 160, 8.50),
    ("Duloxetine 30mg", "Antidepressant", "Cymbalta, Duzela 30", 130, 19.00),
    ("Gabapentin 300mg", "Anticonvulsant", "Neurontin, Gabapin 300", 170, 16.00),
    ("Pregabalin 75mg", "Neuropathic", "Lyrica, Maxgalin 75", 180, 22.00),
    ("Levetiracetam 500mg", "Antiepileptic", "Keppra, Levera 500", 150, 28.00),
    ("Carbamazepine 200mg", "Antiepileptic", "Tegretol, Mazetol 200", 140, 6.50),
    ("Sodium Valproate 500mg", "Antiepileptic", "Depakote, Encorate 500", 160, 14.00),
    ("Baclofen 10mg", "Muscle Relaxant", "Lioresal, Liofen 10", 120, 8.00),
    ("Tizanidine 2mg", "Muscle Relaxant", "Zanaflex, Sirdalud 2", 110, 7.50),
    ("Betamethasone 0.05% Cream", "Corticosteroid", "Diprosone, Betnovate", 160, 6.50),
    ("Hydrocortisone 1% Cream", "Corticosteroid", "Cortaid, H-Cort", 190, 4.50),
    ("Clobetasol 0.05% Ointment", "Corticosteroid", "Temovate, Tenovate", 130, 9.80),
    ("Clotrimazole 1% Cream", "Antifungal", "Lotrimin, Candid", 240, 5.00),
    ("Miconazole 2% Cream", "Antifungal", "Monistat, Micogel", 180, 5.50),
    ("Terbinafine 250mg Tablet", "Antifungal", "Lamisil, Sebifin 250", 140, 24.00),
    ("Fluconazole 150mg", "Antifungal", "Diflucan, Forcan 150", 250, 7.50),
    ("Mupirocin 2% Ointment", "Antibiotic Topical", "Bactroban, T-Bact", 170, 14.00),
    ("Silver Sulfadiazine Cream", "Burn Antibacterial", "Silvadene, Burnol", 110, 11.00),
    ("Permethrin 5% Lotion", "Scabicide", "Elimite, Scaboma", 85, 12.50),
    ("Ciprofloxacin Eye Drops", "Ophthalmic", "Ciloxan, Ciplox Eye", 150, 4.50),
    ("Tobramycin Eye Drops", "Ophthalmic", "Tobrex, Tobacin", 120, 6.00),
    ("Artificial Tears Drops", "Lubricant", "Refresh Tears, Plus", 220, 8.50),
    ("Olopatadine Eye Drops", "Antiallergic", "Patanol, Olopat", 95, 14.00),
    ("Chloramphenicol Ear Drops", "Otic", "Chloromycetin, Otic-C", 110, 5.00),
    ("Vitamin C 500mg Chewable", "Supplement", "Limcee, Celin 500", 600, 1.50),
    ("Vitamin D3 60,000 IU", "Supplement", "Calcirol, D-Rise", 400, 12.00),
    ("Calcium + Vit D3", "Mineral", "Caltrate, Shelcal 500", 500, 5.50),
    ("Iron Ferrous Sulfate 200mg", "Hematinic", "Fefol, Orofer", 350, 4.00),
    ("Folic Acid 5mg", "Vitamin B9", "Folvite 5, Folgard", 450, 2.00),
    ("Vitamin B-Complex + Zinc", "Multivitamin", "Becosules, Cobadex", 550, 3.50),
    ("Methylcobalamin 1500mcg", "Vitamin B12", "Neurobion, Nurokind", 300, 8.50),
    ("Zinc Sulfate 50mg", "Mineral", "Zinconia, Zincovit", 400, 2.50),
    ("Potassium Chloride 600mg", "Electrolyte", "K-Dur, Potclor", 180, 7.00),
    ("ORS Oral Rehydration", "Electrolyte", "Electral, Pedialyte", 700, 1.20),
    ("Prednisolone 5mg", "Steroid", "Prelone, Omnacortil 5", 320, 2.80),
    ("Prednisolone 20mg", "Steroid", "Prelone 20, Omnacortil 20", 240, 6.00),
    ("Methylprednisolone 4mg", "Steroid", "Medrol, Depo-Medrol", 190, 8.50),
    ("Methylprednisolone 16mg", "Steroid", "Medrol 16, Zempred 16", 140, 18.00),
    ("Dexamethasone 0.5mg", "Steroid", "Decadron, Dexona 0.5", 350, 1.80),
    ("Dexamethasone 4mg Inj", "Steroid Injectable", "Decadron IV, Dexona Inj", 200, 4.20),
    ("Hydrocortisone 100mg Inj", "Steroid Injectable", "Solu-Cortef, Efcorlin", 130, 25.00),
    ("Adrenaline 1mg/ml (Epi)", "Emergency", "EpiPen, Vasodrine", 90, 35.00),
    ("Atropine Sulfate 0.6mg", "Emergency", "Atropen", 110, 12.00),
    ("Amiodarone 150mg Inj", "Antiarrhythmic", "Cordarone, Pacerone", 70, 45.00),
    ("Heparin 5000 IU/ml", "Anticoagulant", "Hep-Lock", 80, 65.00),
    ("Enoxaparin 40mg Inj", "Anticoagulant", "Lovenox, Clexane 40", 120, 82.00),
    ("Warfarin 2mg", "Anticoagulant", "Coumadin, Uniwarfin 2", 150, 4.00),
    ("Warfarin 5mg", "Anticoagulant", "Coumadin 5, Warf 5", 130, 5.50),
    ("Naloxone 0.4mg Inj", "Opioid Antagonist", "Narcan", 50, 75.00),
    ("Tranexamic Acid 500mg", "Antifibrinolytic", "Cyklokapron, Pause 500", 160, 14.50),
    ("Dextrose 25% 100ml Infusion", "IV Fluid", "D25 IV Infusion", 140, 15.00),
    ("Normal Saline 0.9% 500ml", "IV Fluid", "0.9% NaCl Infusion", 400, 8.00),
    ("Ringer Lactate 500ml Infusion", "IV Fluid", "Hartmann Solution, RL IV", 350, 9.50)
]

for m in catalog:
    cur.execute("INSERT INTO medicines (medicineName, genericCategory, alternatives, stockQuantity, unitPrice) VALUES (?, ?, ?, ?, ?)", m)

conn.commit()

c_doc = cur.execute("SELECT COUNT(*) FROM adddoctor").fetchone()[0]
c_nur = cur.execute("SELECT COUNT(*) FROM addnurse").fetchone()[0]
c_ph  = cur.execute("SELECT COUNT(*) FROM addpharmacists").fetchone()[0]
c_pat = cur.execute("SELECT COUNT(*) FROM addpatient").fetchone()[0]
c_med = cur.execute("SELECT COUNT(*) FROM medicines").fetchone()[0]
c_usr = cur.execute("SELECT COUNT(*) FROM register").fetchone()[0]
conn.close()

print(f"\nDATABASE SEEDING SUCCESSFUL!")
print(f"Doctors Added     : {c_doc} (DOC-101 to DOC-110)")
print(f"Nurses Added      : {c_nur} (NUR-201 to NUR-212)")
print(f"Pharmacists Added : {c_ph}  (PHARM-301 to PHARM-303)")
print(f"Patients Added    : {c_pat} (PAT-1001 to PAT-1010)")
print(f"Medicines Stocked : {c_med} catalog records")
print(f"Total Logins Ready: {c_usr} credentials")
