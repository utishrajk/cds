package com.feisystems.bham.domain.asi;

import javax.xml.bind.annotation.XmlElement;

import com.feisystems.bham.domain.gpra.EnumDays;

public class AsiVariableContainer {

	@XmlElement
	public Integer e19_days_employment_problem;

	@XmlElement
	public Integer d2a_intoxication_30;

	@XmlElement
	public Float legal_score;

	@XmlElement
	public Float medical_score;

	@XmlElement
	public Float family_score;

	@XmlElement
	public EnumResponse f28b_is_abused_p_lifetime;

	@XmlElement
	public EnumResponse f29b_is_abused_s_lifetime;

	@XmlElement
	public EnumResponse p9a_is_suicidal_30;

	@XmlElement
	public EnumResponse p5a_is_anxiety_30;

	@XmlElement
	public EnumResponse p4a_is_depression_30;

	@XmlElement
	public EnumResponse p8a_is_violent_30;

	@XmlElement
	public EnumResponse p11a_is_prescribed_30;

	@XmlElement
	public EnumGenderCode gender_code;

	@XmlElement
	public Integer d3a_heroin_30;

	@XmlElement
	public Integer d4a_methadone_30;

	@XmlElement
	public Integer d5a_opiates_30;

	@XmlElement
	public EnumAsiRoute d3c_route_of_intake_type_code;

	@XmlElement
	public EnumAsiRoute d4c_route_of_intake_type_code;

	@XmlElement
	public EnumAsiRoute d5c_route_of_intake_type_code;

	@XmlElement
	public Integer age;

	@XmlElement
	public Integer zip3;

	@XmlElement
	public String state;

	@XmlElement
	public Integer priorEpisode;

	// For Medical Score

	@XmlElement
	public EnumDays m6_days_medical_problem;

	@XmlElement
	public EnumPatientRating m7_patient_rating_code;

	@XmlElement
	public EnumPatientRating m8_patient_rating_code;

	// For Family Score

	@XmlElement
	public EnumResponse f3_is_satisfied_marital;

	@XmlElement
	public EnumDays f30_days_conflict_family;

	@XmlElement
	public EnumPatientRating f32_patient_rating_code;

	@XmlElement
	public EnumPatientRating f34_patient_rating_code;

	@XmlElement
	public EnumResponse f18a_is_mother_30;

	@XmlElement
	public EnumResponse f19a_is_father_30;

	@XmlElement
	public EnumResponse f20a_is_sibling_30;

	@XmlElement
	public EnumResponse f21a_is_spouse_30;

	@XmlElement
	public EnumResponse f22a_is_children_30;

	@XmlElement
	public EnumResponse f23a_is_other_30;

	@XmlElement
	public EnumResponse f24a_is_friends_30;

	@XmlElement
	public EnumResponse f25a_is_neighbor_30;

	@XmlElement
	public EnumResponse f26a_is_co_worker_30;

	// For Legal Score

	@XmlElement
	public EnumResponse l24_is_waiting_trial;

	@XmlElement
	public EnumDays l27_days_illegal;

	@XmlElement
	public EnumPatientRating l28_patient_rating_code;

	@XmlElement
	public EnumPatientRating l29_patient_rating_code;

	@XmlElement
	public Integer e17_income_illegal;

}
