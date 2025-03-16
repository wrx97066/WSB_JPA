package com.jpacourse.persistance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private DoctorEntity doctor;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private PatientEntity patient;

	@OneToMany(mappedBy = "visit", cascade = CascadeType.ALL)
	private List<MedicalTreatmentEntity> treatments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public DoctorEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public List<MedicalTreatmentEntity> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<MedicalTreatmentEntity> treatments) {
		this.treatments = treatments;
	}

}
