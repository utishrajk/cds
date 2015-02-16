package com.feisystems.bham.exceptions;

public class ClinicalDataNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -344592920848724403L;
	
	private final Long patientId;

	public ClinicalDataNotFoundException(Long id) {
		patientId = id;
	}

	public Long getPatientId() {
		return patientId;
	}


}
