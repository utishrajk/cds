package com.feisystems.bham.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.bham.domain.Asi;
import com.feisystems.bham.domain.asi.AsiVariableContainer;
import com.feisystems.bham.domain.reference.ASIRouteCode;
import com.feisystems.bham.domain.reference.ASIRouteCodeRepository;
import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.AdministrativeGenderCodeRepository;
import com.feisystems.bham.domain.reference.EpisodeCode;
import com.feisystems.bham.domain.reference.EpisodeCodeRepository;
import com.feisystems.bham.domain.reference.PatientRatingCode;
import com.feisystems.bham.domain.reference.PatientRatingCodeRepository;
import com.feisystems.bham.domain.reference.ResponseCode;
import com.feisystems.bham.domain.reference.ResponseCodeRepository;
import com.feisystems.bham.infrastructure.DtoToDomainEntityMapper;

@Component
public class AsiVariableContainerToAsiMapper implements DtoToDomainEntityMapper<AsiVariableContainer, Asi> {

	@Autowired
	private ResponseCodeRepository responseCodeRepository;

	@Autowired
	private ASIRouteCodeRepository asiRouteCodeRepository;

	@Autowired
	private EpisodeCodeRepository episodeCodeRepository;

	@Autowired
	private PatientRatingCodeRepository patientRatingCodeRepository;

	@Autowired
	private AdministrativeGenderCodeRepository genderCodeRepository;

	@Override
	public Asi map(AsiVariableContainer container) {
		System.out.println("Starting saveAsiVariableContainer...");

		long startTime = System.currentTimeMillis();

		Asi asi = new Asi();

		asi.setAsi(true);

		asi.setTimeStamp(new Date());

		asi.setE19_days_employment_problem(container.e19_days_employment_problem);

		asi.setD2a_intoxication_30(container.d2a_intoxication_30);

		asi.setLegal_score(container.legal_score);

		asi.setMedical_score(container.medical_score);

		asi.setFamily_score(container.family_score);

		if (container.f28b_is_abused_p_lifetime != null) {
			asi.setF28b_is_abused_p_lifetime_Code(retrieveResponseCode(container.f28b_is_abused_p_lifetime.getValue()));
		}

		if (container.f29b_is_abused_s_lifetime != null) {
			asi.setF29b_is_abused_s_lifetime_Code(retrieveResponseCode(container.f29b_is_abused_s_lifetime.getValue()));
		}

		if (container.p9a_is_suicidal_30 != null) {
			asi.setP9a_is_suicidal_30_Code(retrieveResponseCode(container.p9a_is_suicidal_30.getValue()));
		}

		if (container.p5a_is_anxiety_30 != null) {
			asi.setP5a_is_anxiety_30_Code(retrieveResponseCode(container.p5a_is_anxiety_30.getValue()));
		}

		if (container.p4a_is_depression_30 != null) {
			asi.setP4a_is_depression_30_Code(retrieveResponseCode(container.p4a_is_depression_30.getValue()));
		}

		if (container.p8a_is_violent_30 != null) {
			asi.setP8a_is_violent_30_Code(retrieveResponseCode(container.p8a_is_violent_30.getValue()));
		}

		if (container.p11a_is_prescribed_30 != null) {
			asi.setP11a_is_prescribed_30_Code(retrieveResponseCode(container.p11a_is_prescribed_30.getValue()));
		}

		if (container.gender_code != null) {
			asi.setGenderCode(retrieveAdministrativeGenderCode(container.gender_code.getValue()));
		}

		asi.setD3a_heroin_30(container.d3a_heroin_30);

		asi.setD4a_methadone_30(container.d4a_methadone_30);

		asi.setD5a_opiates_30(container.d5a_opiates_30);

		if (container.d3c_route_of_intake_type_code != null) {
			asi.setD3c_route_of_intake_type_Code(retrieveASIAsiRouteCode(container.d3c_route_of_intake_type_code.getValue()));
		}

		if (container.d4c_route_of_intake_type_code != null) {
			asi.setD4c_route_of_intake_type_Code(retrieveASIAsiRouteCode(container.d4c_route_of_intake_type_code.getValue()));
		}

		if (container.d5c_route_of_intake_type_code != null) {
			asi.setD5c_route_of_intake_type_Code(retrieveASIAsiRouteCode(container.d5c_route_of_intake_type_code.getValue()));
		}

		asi.setAge(container.age);

		asi.setState(container.state);

		asi.setEpisodeCode(retrieveEpisodeCode(Integer.toString(container.priorEpisode)));

		if (container.m6_days_medical_problem != null) {
			asi.setM6_days_medical_problem(container.m6_days_medical_problem.getValue());
		}

		if (container.m7_patient_rating_code != null) {
			asi.setM7_patient_rating_Code(retrievePatientRatingCode(Integer.toString(container.m7_patient_rating_code.getValue())));
		}

		if (container.m8_patient_rating_code != null) {
			asi.setM8_patient_rating_Code(retrievePatientRatingCode(Integer.toString(container.m8_patient_rating_code.getValue())));
		}

		if (container.f3_is_satisfied_marital != null) {
			asi.setF3_is_satisfied_marital_Code(retrieveResponseCode(container.f3_is_satisfied_marital.getValue()));
		}

		if (container.f30_days_conflict_family != null) {
			asi.setF30_days_conflict_family(container.f30_days_conflict_family.getValue());
		}

		if (container.f32_patient_rating_code != null) {
			asi.setF32_patient_rating_Code(retrievePatientRatingCode(Integer.toString(container.f32_patient_rating_code.getValue())));
		}

		if (container.f34_patient_rating_code != null) {
			asi.setF34_patient_rating_Code(retrievePatientRatingCode(Integer.toString(container.f34_patient_rating_code.getValue())));
		}

		if (container.f18a_is_mother_30 != null) {
			asi.setF18a_is_mother_30_Code(retrieveResponseCode(container.f18a_is_mother_30.getValue()));
		}

		if (container.f19a_is_father_30 != null) {
			asi.setF19a_is_father_30_Code(retrieveResponseCode(container.f19a_is_father_30.getValue()));
		}

		if (container.f20a_is_sibling_30 != null) {
			asi.setF20a_is_sibling_30_Code(retrieveResponseCode(container.f20a_is_sibling_30.getValue()));
		}

		if (container.f21a_is_spouse_30 != null) {
			asi.setF21a_is_spouse_30_Code(retrieveResponseCode(container.f21a_is_spouse_30.getValue()));
		}

		if (container.f22a_is_children_30 != null) {
			asi.setF22a_is_children_30_Code(retrieveResponseCode(container.f22a_is_children_30.getValue()));
		}

		if (container.f23a_is_other_30 != null) {
			asi.setF23a_is_other_30_Code(retrieveResponseCode(container.f23a_is_other_30.getValue()));
		}

		if (container.f24a_is_friends_30 != null) {
			asi.setF24a_is_friends_30_Code(retrieveResponseCode(container.f24a_is_friends_30.getValue()));
		}

		if (container.f25a_is_neighbor_30 != null) {
			asi.setF25a_is_neighbor_30_Code(retrieveResponseCode(container.f25a_is_neighbor_30.getValue()));
		}

		if (container.f26a_is_co_worker_30 != null) {
			asi.setF26a_is_co_worker_30_Code(retrieveResponseCode(container.f26a_is_co_worker_30.getValue()));
		}

		if (container.l24_is_waiting_trial != null) {
			asi.setL24_is_waiting_trial_Code(retrieveResponseCode(container.l24_is_waiting_trial.getValue()));
		}

		if (container.l27_days_illegal != null) {
			asi.setL27_days_illegal(container.l27_days_illegal.getValue());
		}

		if (container.l28_patient_rating_code != null) {
			asi.setL28_patient_rating_Code(retrievePatientRatingCode(Integer.toString(container.l28_patient_rating_code.getValue())));
		}

		if (container.l29_patient_rating_code != null) {
			asi.setL29_patient_rating_Code(retrievePatientRatingCode(Integer.toString(container.l29_patient_rating_code.getValue())));
		}

		asi.setE17_income_illegal(container.e17_income_illegal);

		long endTime = System.currentTimeMillis();

		System.out.println("difference : " + (endTime - startTime));
		return asi;
	}

	private ResponseCode retrieveResponseCode(String code) {
		return responseCodeRepository.findByCode(code);
	}

	private PatientRatingCode retrievePatientRatingCode(String code) {
		return patientRatingCodeRepository.findByCode(code);
	}

	private ASIRouteCode retrieveASIAsiRouteCode(String code) {
		return asiRouteCodeRepository.findByCode(code);
	}

	private AdministrativeGenderCode retrieveAdministrativeGenderCode(String code) {
		return genderCodeRepository.findByCode(code);
	}

	private EpisodeCode retrieveEpisodeCode(String code) {
		return episodeCodeRepository.findByCode(code);
	}
}
