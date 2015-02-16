package com.feisystems.bham.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.feisystems.bham.domain.Asi;
import com.feisystems.bham.domain.AuditRecommendations;
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
public class AsiDtoToAsiMapper implements DtoToDomainEntityMapper<AsiDto, Asi> {

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
	
	@Autowired
	private UsernameService usernameService;

	@Override
	public Asi map(AsiDto dto) {

		Asi asi = new Asi();

		asi.setTimeStamp(new Date());

		asi.setAsi(true);

		asi.setRequestId(dto.getRequestId());

		asi.setStaffId(dto.getStaffId());

		asi.setUniqueClientNumber(dto.getUniqueClientNumber());

		asi.setNote1(dto.getNote1());

		asi.setNote2(dto.getNote2());

		asi.setE19_days_employment_problem(dto.getE19_days_employment_problem());

		asi.setD2a_intoxication_30(dto.getD2a_intoxication_30());

		asi.setF28b_is_abused_p_lifetime_Code(retrieveResponseCode(dto.getF28b_is_abused_p_lifetime_Code()));

		asi.setF29b_is_abused_s_lifetime_Code(retrieveResponseCode(dto.getF29b_is_abused_s_lifetime_Code()));

		asi.setP9a_is_suicidal_30_Code(retrieveResponseCode(dto.getP9a_is_suicidal_30_Code()));

		asi.setP5a_is_anxiety_30_Code(retrieveResponseCode(dto.getP5a_is_anxiety_30_Code()));

		asi.setP4a_is_depression_30_Code(retrieveResponseCode(dto.getP4a_is_depression_30_Code()));

		asi.setP8a_is_violent_30_Code(retrieveResponseCode(dto.getP8a_is_violent_30_Code()));

		asi.setP11a_is_prescribed_30_Code(retrieveResponseCode(dto.getP11a_is_prescribed_30_Code()));

		asi.setGenderCode(retrieveAdministrativeGenderCode(dto.getGenderCode()));

		asi.setD3a_heroin_30(dto.getD3a_heroin_30());

		asi.setD4a_methadone_30(dto.getD4a_methadone_30());

		asi.setD5a_opiates_30(dto.getD5a_opiates_30());

		asi.setD3c_route_of_intake_type_Code(retrieveASIAsiRouteCode(dto.getD3c_route_of_intake_type_Code()));

		asi.setD4c_route_of_intake_type_Code(retrieveASIAsiRouteCode(dto.getD4c_route_of_intake_type_Code()));

		asi.setD5c_route_of_intake_type_Code(retrieveASIAsiRouteCode(dto.getD5c_route_of_intake_type_Code()));

		asi.setAge(dto.getAge());

		asi.setState(dto.getState());

		asi.setEpisodeCode(retrieveEpisodeCode(dto.getEpisodeCode()));

		asi.setM6_days_medical_problem(dto.getM6_days_medical_problem());

		asi.setM7_patient_rating_Code(retrievePatientRatingCode(dto.getM7_patient_rating_Code()));

		asi.setM8_patient_rating_Code(retrievePatientRatingCode(dto.getM8_patient_rating_Code()));

		asi.setF3_is_satisfied_marital_Code(retrieveResponseCode(dto.getF3_is_satisfied_marital_Code()));

		asi.setF30_days_conflict_family(dto.getF30_days_conflict_family());

		asi.setF32_patient_rating_Code(retrievePatientRatingCode(dto.getF32_patient_rating_Code()));

		asi.setF34_patient_rating_Code(retrievePatientRatingCode(dto.getF34_patient_rating_Code()));

		asi.setF18a_is_mother_30_Code(retrieveResponseCode(dto.getF18a_is_mother_30_Code()));

		asi.setF19a_is_father_30_Code(retrieveResponseCode(dto.getF19a_is_father_30_Code()));

		asi.setF20a_is_sibling_30_Code(retrieveResponseCode(dto.getF20a_is_sibling_30_Code()));

		asi.setF21a_is_spouse_30_Code(retrieveResponseCode(dto.getF21a_is_spouse_30_Code()));

		asi.setF22a_is_children_30_Code(retrieveResponseCode(dto.getF22a_is_children_30_Code()));

		asi.setF23a_is_other_30_Code(retrieveResponseCode(dto.getF23a_is_other_30_Code()));

		asi.setF24a_is_friends_30_Code(retrieveResponseCode(dto.getF24a_is_friends_30_Code()));

		asi.setF25a_is_neighbor_30_Code(retrieveResponseCode(dto.getF25a_is_neighbor_30_Code()));

		asi.setF26a_is_co_worker_30_Code(retrieveResponseCode(dto.getF26a_is_co_worker_30_Code()));

		asi.setL24_is_waiting_trial_Code(retrieveResponseCode(dto.getL24_is_waiting_trial_Code()));

		asi.setL27_days_illegal(dto.getL27_days_illegal());

		asi.setL28_patient_rating_Code(retrievePatientRatingCode(dto.getL28_patient_rating_Code()));

		asi.setL29_patient_rating_Code(retrievePatientRatingCode(dto.getL29_patient_rating_Code()));

		asi.setE17_income_illegal(dto.getE17_income_illegal());

		Set<AuditRecommendations> auditRecommendationsSet = new HashSet<AuditRecommendations>();
		AuditRecommendations auditRecommendations = new AuditRecommendations();
		auditRecommendations.setRecommendationXML(dto.getRecommendationXml());
		auditRecommendations.setRequestId(dto.getRequestId());
		auditRecommendations.setTimestamp(new Date());
		auditRecommendations.setAsi(asi);
		auditRecommendations.setUsername(usernameService.retrieveUsername());
		
		auditRecommendationsSet.add(auditRecommendations);
		asi.setAuditRecommendations(auditRecommendationsSet);

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
