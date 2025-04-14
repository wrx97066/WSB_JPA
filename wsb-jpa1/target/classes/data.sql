-- Existing address
insert into address (id, address_line1, address_line2, city, postal_code)
values (901, 'xx', 'yy', 'city', '60-400');

-- Additional addresses
insert into address (id, address_line1, address_line2, city, postal_code)
values (902, 'Street 1', 'Apt 101', 'Warsaw', '00-001');
insert into address (id, address_line1, address_line2, city, postal_code)
values (903, 'Street 2', 'Apt 202', 'Krakow', '30-001');
insert into address (id, address_line1, address_line2, city, postal_code)
values (904, 'Street 3', 'Apt 303', 'Poznan', '60-001');

-- Doctors
insert into doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
values (101, 'John', 'Smith', '123456789', 'john.smith@example.com', 'DOC001', 'SURGEON', 902);
insert into doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
values (102, 'Mary', 'Johnson', '987654321', 'mary.johnson@example.com', 'DOC002', 'GP', 903);

-- Patients
insert into patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, insured, address_id)
values (201, 'Jane', 'Doe', '555123456', 'jane.doe@example.com', 'PAT001', '1990-01-15', true, 904);
insert into patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, insured, address_id)
values (202, 'Bob', 'Brown', '555654321', 'bob.brown@example.com', 'PAT002', '1985-05-20', false, 901);

-- Visits
insert into visit (id, description, time, doctor_id, patient_id)
values (301, 'Initial consultation', '2023-01-10 10:00:00', 101, 201);
insert into visit (id, description, time, doctor_id, patient_id)
values (302, 'Follow-up', '2023-02-15 14:30:00', 102, 201);
insert into visit (id, description, time, doctor_id, patient_id)
values (303, 'Annual checkup', '2023-03-20 09:15:00', 101, 202);

-- Medical Treatments
insert into medical_treatment (id, description, type, visit_id)
values (401, 'Basic ultrasound', 'USG', 301);
insert into medical_treatment (id, description, type, visit_id)
values (402, 'Heart monitoring', 'EKG', 302);
insert into medical_treatment (id, description, type, visit_id)
values (403, 'Chest X-ray', 'RTG', 303);
insert into medical_treatment (id, description, type, visit_id)
values (404, 'Additional EKG', 'EKG', 303);