package com.feisystems.bham.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feisystems.bham.util.Constant;

public class AsiDto implements DataElementsDto {

	private Long id;

	private String staffId;
	
	private String uniqueClientNumber;

	private String requestId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy", timezone = Constant.NEWYORK_TIMEZONE)
	private Date timeStamp;

	private boolean asi;

	private boolean gpra;

	private String note1;

	private String note2;

	private Integer e19_days_employment_problem;

	private Integer d2a_intoxication_30;

	private Float legal_score;

	private Float medical_score;

	private Float family_score;

	private String f28b_is_abused_p_lifetime_Code;

	private String f28b_is_abused_p_lifetime_DisplayName;

	private String f29b_is_abused_s_lifetime_Code;

	private String f29b_is_abused_s_lifetime_DisplayName;

	private String p9a_is_suicidal_30_Code;

	private String p9a_is_suicidal_30_DisplayName;

	private String p5a_is_anxiety_30_Code;

	private String p5a_is_anxiety_30_DisplayName;

	private String p4a_is_depression_30_Code;

	private String p4a_is_depression_30_DisplayName;

	private String p8a_is_violent_30_Code;

	private String p8a_is_violent_30_DisplayName;

	private String p11a_is_prescribed_30_Code;

	private String p11a_is_prescribed_30_DisplayName;

	private String genderCode;

	private String genderDisplayName;

	private Integer d3a_heroin_30;

	private Integer d4a_methadone_30;

	private Integer d5a_opiates_30;

	private String d3c_route_of_intake_type_Code;

	private String d3c_route_of_intake_type_DisplayName;

	private String d4c_route_of_intake_type_Code;

	private String d4c_route_of_intake_type_DisplayName;

	private String d5c_route_of_intake_type_Code;

	private String d5c_route_of_intake_type_DisplayName;

	private Integer age;

	private String state;

	private String episodeCode;

	private String episodeDisplayName;

	// For Medical Score

	private Integer m6_days_medical_problem;

	private String m7_patient_rating_Code;

	private String m7_patient_rating_DisplayName;

	private String m8_patient_rating_Code;

	private String m8_patient_rating_DisplayName;

	// For Family Score

	private String f3_is_satisfied_marital_Code;

	private String f3_is_satisfied_marital_DisplayName;

	private Integer f30_days_conflict_family;

	private String f32_patient_rating_Code;

	private String f32_patient_rating_DisplayName;

	private String f34_patient_rating_Code;

	private String f34_patient_rating_DisplayName;

	private String f18a_is_mother_30_Code;

	private String f18a_is_mother_30_DisplayName;

	private String f19a_is_father_30_Code;

	private String f19a_is_father_30_DisplayName;

	private String f20a_is_sibling_30_Code;

	private String f20a_is_sibling_30_DisplayName;

	private String f21a_is_spouse_30_Code;

	private String f21a_is_spouse_30_DisplayName;

	private String f22a_is_children_30_Code;

	private String f22a_is_children_30_DisplayName;

	private String f23a_is_other_30_Code;

	private String f23a_is_other_30_DisplayName;

	private String f24a_is_friends_30_Code;

	private String f24a_is_friends_30_DisplayName;

	private String f25a_is_neighbor_30_Code;

	private String f25a_is_neighbor_30_DisplayName;

	private String f26a_is_co_worker_30_Code;

	private String f26a_is_co_worker_30_DisplayName;

	// For Legal Score

	private String l24_is_waiting_trial_Code;

	private String l24_is_waiting_trial_DisplayName;

	private Integer l27_days_illegal;

	private String l28_patient_rating_Code;

	private String l28_patient_rating_DisplayName;

	private String l29_patient_rating_Code;

	private String l29_patient_rating_DisplayName;

	private Integer e17_income_illegal;

	private String recommendationXml;

	private String otherDescription;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	
	public String getUniqueClientNumber() {
		return uniqueClientNumber;
	}

	public void setUniqueClientNumber(String uniqueClientNumber) {
		this.uniqueClientNumber = uniqueClientNumber;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean isAsi() {
		return asi;
	}

	public void setAsi(boolean asi) {
		this.asi = asi;
	}

	public boolean isGpra() {
		return gpra;
	}

	public void setGpra(boolean gpra) {
		this.gpra = gpra;
	}

	public Integer getE19_days_employment_problem() {
		return e19_days_employment_problem;
	}

	public void setE19_days_employment_problem(Integer e19_days_employment_problem) {
		this.e19_days_employment_problem = e19_days_employment_problem;
	}

	public Integer getD2a_intoxication_30() {
		return d2a_intoxication_30;
	}

	public void setD2a_intoxication_30(Integer d2a_intoxication_30) {
		this.d2a_intoxication_30 = d2a_intoxication_30;
	}

	public Float getLegal_score() {
		return legal_score;
	}

	public void setLegal_score(Float legal_score) {
		this.legal_score = legal_score;
	}

	public Float getMedical_score() {
		return medical_score;
	}

	public void setMedical_score(Float medical_score) {
		this.medical_score = medical_score;
	}

	public Float getFamily_score() {
		return family_score;
	}

	public void setFamily_score(Float family_score) {
		this.family_score = family_score;
	}

	public String getF28b_is_abused_p_lifetime_Code() {
		return f28b_is_abused_p_lifetime_Code;
	}

	public void setF28b_is_abused_p_lifetime_Code(String f28b_is_abused_p_lifetime_Code) {
		this.f28b_is_abused_p_lifetime_Code = f28b_is_abused_p_lifetime_Code;
	}

	public String getF28b_is_abused_p_lifetime_DisplayName() {
		return f28b_is_abused_p_lifetime_DisplayName;
	}

	public void setF28b_is_abused_p_lifetime_DisplayName(String f28b_is_abused_p_lifetime_DisplayName) {
		this.f28b_is_abused_p_lifetime_DisplayName = f28b_is_abused_p_lifetime_DisplayName;
	}

	public String getF29b_is_abused_s_lifetime_Code() {
		return f29b_is_abused_s_lifetime_Code;
	}

	public void setF29b_is_abused_s_lifetime_Code(String f29b_is_abused_s_lifetime_Code) {
		this.f29b_is_abused_s_lifetime_Code = f29b_is_abused_s_lifetime_Code;
	}

	public String getF29b_is_abused_s_lifetime_DisplayName() {
		return f29b_is_abused_s_lifetime_DisplayName;
	}

	public void setF29b_is_abused_s_lifetime_DisplayName(String f29b_is_abused_s_lifetime_DisplayName) {
		this.f29b_is_abused_s_lifetime_DisplayName = f29b_is_abused_s_lifetime_DisplayName;
	}

	public String getP9a_is_suicidal_30_Code() {
		return p9a_is_suicidal_30_Code;
	}

	public void setP9a_is_suicidal_30_Code(String p9a_is_suicidal_30_Code) {
		this.p9a_is_suicidal_30_Code = p9a_is_suicidal_30_Code;
	}

	public String getP9a_is_suicidal_30_DisplayName() {
		return p9a_is_suicidal_30_DisplayName;
	}

	public void setP9a_is_suicidal_30_DisplayName(String p9a_is_suicidal_30_DisplayName) {
		this.p9a_is_suicidal_30_DisplayName = p9a_is_suicidal_30_DisplayName;
	}

	public String getP5a_is_anxiety_30_Code() {
		return p5a_is_anxiety_30_Code;
	}

	public void setP5a_is_anxiety_30_Code(String p5a_is_anxiety_30_Code) {
		this.p5a_is_anxiety_30_Code = p5a_is_anxiety_30_Code;
	}

	public String getP5a_is_anxiety_30_DisplayName() {
		return p5a_is_anxiety_30_DisplayName;
	}

	public void setP5a_is_anxiety_30_DisplayName(String p5a_is_anxiety_30_DisplayName) {
		this.p5a_is_anxiety_30_DisplayName = p5a_is_anxiety_30_DisplayName;
	}

	public String getP4a_is_depression_30_Code() {
		return p4a_is_depression_30_Code;
	}

	public void setP4a_is_depression_30_Code(String p4a_is_depression_30_Code) {
		this.p4a_is_depression_30_Code = p4a_is_depression_30_Code;
	}

	public String getP4a_is_depression_30_DisplayName() {
		return p4a_is_depression_30_DisplayName;
	}

	public void setP4a_is_depression_30_DisplayName(String p4a_is_depression_30_DisplayName) {
		this.p4a_is_depression_30_DisplayName = p4a_is_depression_30_DisplayName;
	}

	public String getP8a_is_violent_30_Code() {
		return p8a_is_violent_30_Code;
	}

	public void setP8a_is_violent_30_Code(String p8a_is_violent_30_Code) {
		this.p8a_is_violent_30_Code = p8a_is_violent_30_Code;
	}

	public String getP8a_is_violent_30_DisplayName() {
		return p8a_is_violent_30_DisplayName;
	}

	public void setP8a_is_violent_30_DisplayName(String p8a_is_violent_30_DisplayName) {
		this.p8a_is_violent_30_DisplayName = p8a_is_violent_30_DisplayName;
	}

	public String getP11a_is_prescribed_30_Code() {
		return p11a_is_prescribed_30_Code;
	}

	public void setP11a_is_prescribed_30_Code(String p11a_is_prescribed_30_Code) {
		this.p11a_is_prescribed_30_Code = p11a_is_prescribed_30_Code;
	}

	public String getP11a_is_prescribed_30_DisplayName() {
		return p11a_is_prescribed_30_DisplayName;
	}

	public void setP11a_is_prescribed_30_DisplayName(String p11a_is_prescribed_30_DisplayName) {
		this.p11a_is_prescribed_30_DisplayName = p11a_is_prescribed_30_DisplayName;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getGenderDisplayName() {
		return genderDisplayName;
	}

	public void setGenderDisplayName(String genderDisplayName) {
		this.genderDisplayName = genderDisplayName;
	}

	public Integer getD3a_heroin_30() {
		return d3a_heroin_30;
	}

	public void setD3a_heroin_30(Integer d3a_heroin_30) {
		this.d3a_heroin_30 = d3a_heroin_30;
	}

	public Integer getD4a_methadone_30() {
		return d4a_methadone_30;
	}

	public void setD4a_methadone_30(Integer d4a_methadone_30) {
		this.d4a_methadone_30 = d4a_methadone_30;
	}

	public Integer getD5a_opiates_30() {
		return d5a_opiates_30;
	}

	public void setD5a_opiates_30(Integer d5a_opiates_30) {
		this.d5a_opiates_30 = d5a_opiates_30;
	}

	public String getD3c_route_of_intake_type_Code() {
		return d3c_route_of_intake_type_Code;
	}

	public void setD3c_route_of_intake_type_Code(String d3c_route_of_intake_type_Code) {
		this.d3c_route_of_intake_type_Code = d3c_route_of_intake_type_Code;
	}

	public String getD3c_route_of_intake_type_DisplayName() {
		return d3c_route_of_intake_type_DisplayName;
	}

	public void setD3c_route_of_intake_type_DisplayName(String d3c_route_of_intake_type_DisplayName) {
		this.d3c_route_of_intake_type_DisplayName = d3c_route_of_intake_type_DisplayName;
	}

	public String getD4c_route_of_intake_type_Code() {
		return d4c_route_of_intake_type_Code;
	}

	public void setD4c_route_of_intake_type_Code(String d4c_route_of_intake_type_Code) {
		this.d4c_route_of_intake_type_Code = d4c_route_of_intake_type_Code;
	}

	public String getD4c_route_of_intake_type_DisplayName() {
		return d4c_route_of_intake_type_DisplayName;
	}

	public void setD4c_route_of_intake_type_DisplayName(String d4c_route_of_intake_type_DisplayName) {
		this.d4c_route_of_intake_type_DisplayName = d4c_route_of_intake_type_DisplayName;
	}

	public String getD5c_route_of_intake_type_Code() {
		return d5c_route_of_intake_type_Code;
	}

	public void setD5c_route_of_intake_type_Code(String d5c_route_of_intake_type_Code) {
		this.d5c_route_of_intake_type_Code = d5c_route_of_intake_type_Code;
	}

	public String getD5c_route_of_intake_type_DisplayName() {
		return d5c_route_of_intake_type_DisplayName;
	}

	public void setD5c_route_of_intake_type_DisplayName(String d5c_route_of_intake_type_DisplayName) {
		this.d5c_route_of_intake_type_DisplayName = d5c_route_of_intake_type_DisplayName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEpisodeCode() {
		return episodeCode;
	}

	public void setEpisodeCode(String episodeCode) {
		this.episodeCode = episodeCode;
	}

	public String getEpisodeDisplayName() {
		return episodeDisplayName;
	}

	public void setEpisodeDisplayName(String episodeDisplayName) {
		this.episodeDisplayName = episodeDisplayName;
	}

	public Integer getM6_days_medical_problem() {
		return m6_days_medical_problem;
	}

	public void setM6_days_medical_problem(Integer m6_days_medical_problem) {
		this.m6_days_medical_problem = m6_days_medical_problem;
	}

	public String getM7_patient_rating_Code() {
		return m7_patient_rating_Code;
	}

	public void setM7_patient_rating_Code(String m7_patient_rating_Code) {
		this.m7_patient_rating_Code = m7_patient_rating_Code;
	}

	public String getM7_patient_rating_DisplayName() {
		return m7_patient_rating_DisplayName;
	}

	public void setM7_patient_rating_DisplayName(String m7_patient_rating_DisplayName) {
		this.m7_patient_rating_DisplayName = m7_patient_rating_DisplayName;
	}

	public String getM8_patient_rating_Code() {
		return m8_patient_rating_Code;
	}

	public void setM8_patient_rating_Code(String m8_patient_rating_Code) {
		this.m8_patient_rating_Code = m8_patient_rating_Code;
	}

	public String getM8_patient_rating_DisplayName() {
		return m8_patient_rating_DisplayName;
	}

	public void setM8_patient_rating_DisplayName(String m8_patient_rating_DisplayName) {
		this.m8_patient_rating_DisplayName = m8_patient_rating_DisplayName;
	}

	public String getF3_is_satisfied_marital_Code() {
		return f3_is_satisfied_marital_Code;
	}

	public void setF3_is_satisfied_marital_Code(String f3_is_satisfied_marital_Code) {
		this.f3_is_satisfied_marital_Code = f3_is_satisfied_marital_Code;
	}

	public String getF3_is_satisfied_marital_DisplayName() {
		return f3_is_satisfied_marital_DisplayName;
	}

	public void setF3_is_satisfied_marital_DisplayName(String f3_is_satisfied_marital_DisplayName) {
		this.f3_is_satisfied_marital_DisplayName = f3_is_satisfied_marital_DisplayName;
	}

	public Integer getF30_days_conflict_family() {
		return f30_days_conflict_family;
	}

	public void setF30_days_conflict_family(Integer f30_days_conflict_family) {
		this.f30_days_conflict_family = f30_days_conflict_family;
	}

	public String getF32_patient_rating_Code() {
		return f32_patient_rating_Code;
	}

	public void setF32_patient_rating_Code(String f32_patient_rating_Code) {
		this.f32_patient_rating_Code = f32_patient_rating_Code;
	}

	public String getF32_patient_rating_DisplayName() {
		return f32_patient_rating_DisplayName;
	}

	public void setF32_patient_rating_DisplayName(String f32_patient_rating_DisplayName) {
		this.f32_patient_rating_DisplayName = f32_patient_rating_DisplayName;
	}

	public String getF34_patient_rating_Code() {
		return f34_patient_rating_Code;
	}

	public void setF34_patient_rating_Code(String f34_patient_rating_Code) {
		this.f34_patient_rating_Code = f34_patient_rating_Code;
	}

	public String getF34_patient_rating_DisplayName() {
		return f34_patient_rating_DisplayName;
	}

	public void setF34_patient_rating_DisplayName(String f34_patient_rating_DisplayName) {
		this.f34_patient_rating_DisplayName = f34_patient_rating_DisplayName;
	}

	public String getF18a_is_mother_30_Code() {
		return f18a_is_mother_30_Code;
	}

	public void setF18a_is_mother_30_Code(String f18a_is_mother_30_Code) {
		this.f18a_is_mother_30_Code = f18a_is_mother_30_Code;
	}

	public String getF18a_is_mother_30_DisplayName() {
		return f18a_is_mother_30_DisplayName;
	}

	public void setF18a_is_mother_30_DisplayName(String f18a_is_mother_30_DisplayName) {
		this.f18a_is_mother_30_DisplayName = f18a_is_mother_30_DisplayName;
	}

	public String getF19a_is_father_30_Code() {
		return f19a_is_father_30_Code;
	}

	public void setF19a_is_father_30_Code(String f19a_is_father_30_Code) {
		this.f19a_is_father_30_Code = f19a_is_father_30_Code;
	}

	public String getF19a_is_father_30_DisplayName() {
		return f19a_is_father_30_DisplayName;
	}

	public void setF19a_is_father_30_DisplayName(String f19a_is_father_30_DisplayName) {
		this.f19a_is_father_30_DisplayName = f19a_is_father_30_DisplayName;
	}

	public String getF20a_is_sibling_30_Code() {
		return f20a_is_sibling_30_Code;
	}

	public void setF20a_is_sibling_30_Code(String f20a_is_sibling_30_Code) {
		this.f20a_is_sibling_30_Code = f20a_is_sibling_30_Code;
	}

	public String getF20a_is_sibling_30_DisplayName() {
		return f20a_is_sibling_30_DisplayName;
	}

	public void setF20a_is_sibling_30_DisplayName(String f20a_is_sibling_30_DisplayName) {
		this.f20a_is_sibling_30_DisplayName = f20a_is_sibling_30_DisplayName;
	}

	public String getF21a_is_spouse_30_Code() {
		return f21a_is_spouse_30_Code;
	}

	public void setF21a_is_spouse_30_Code(String f21a_is_spouse_30_Code) {
		this.f21a_is_spouse_30_Code = f21a_is_spouse_30_Code;
	}

	public String getF21a_is_spouse_30_DisplayName() {
		return f21a_is_spouse_30_DisplayName;
	}

	public void setF21a_is_spouse_30_DisplayName(String f21a_is_spouse_30_DisplayName) {
		this.f21a_is_spouse_30_DisplayName = f21a_is_spouse_30_DisplayName;
	}

	public String getF22a_is_children_30_Code() {
		return f22a_is_children_30_Code;
	}

	public void setF22a_is_children_30_Code(String f22a_is_children_30_Code) {
		this.f22a_is_children_30_Code = f22a_is_children_30_Code;
	}

	public String getF22a_is_children_30_DisplayName() {
		return f22a_is_children_30_DisplayName;
	}

	public void setF22a_is_children_30_DisplayName(String f22a_is_children_30_DisplayName) {
		this.f22a_is_children_30_DisplayName = f22a_is_children_30_DisplayName;
	}

	public String getF23a_is_other_30_Code() {
		return f23a_is_other_30_Code;
	}

	public void setF23a_is_other_30_Code(String f23a_is_other_30_Code) {
		this.f23a_is_other_30_Code = f23a_is_other_30_Code;
	}

	public String getF23a_is_other_30_DisplayName() {
		return f23a_is_other_30_DisplayName;
	}

	public void setF23a_is_other_30_DisplayName(String f23a_is_other_30_DisplayName) {
		this.f23a_is_other_30_DisplayName = f23a_is_other_30_DisplayName;
	}

	public String getF24a_is_friends_30_Code() {
		return f24a_is_friends_30_Code;
	}

	public void setF24a_is_friends_30_Code(String f24a_is_friends_30_Code) {
		this.f24a_is_friends_30_Code = f24a_is_friends_30_Code;
	}

	public String getF24a_is_friends_30_DisplayName() {
		return f24a_is_friends_30_DisplayName;
	}

	public void setF24a_is_friends_30_DisplayName(String f24a_is_friends_30_DisplayName) {
		this.f24a_is_friends_30_DisplayName = f24a_is_friends_30_DisplayName;
	}

	public String getF25a_is_neighbor_30_Code() {
		return f25a_is_neighbor_30_Code;
	}

	public void setF25a_is_neighbor_30_Code(String f25a_is_neighbor_30_Code) {
		this.f25a_is_neighbor_30_Code = f25a_is_neighbor_30_Code;
	}

	public String getF25a_is_neighbor_30_DisplayName() {
		return f25a_is_neighbor_30_DisplayName;
	}

	public void setF25a_is_neighbor_30_DisplayName(String f25a_is_neighbor_30_DisplayName) {
		this.f25a_is_neighbor_30_DisplayName = f25a_is_neighbor_30_DisplayName;
	}

	public String getF26a_is_co_worker_30_Code() {
		return f26a_is_co_worker_30_Code;
	}

	public void setF26a_is_co_worker_30_Code(String f26a_is_co_worker_30_Code) {
		this.f26a_is_co_worker_30_Code = f26a_is_co_worker_30_Code;
	}

	public String getF26a_is_co_worker_30_DisplayName() {
		return f26a_is_co_worker_30_DisplayName;
	}

	public void setF26a_is_co_worker_30_DisplayName(String f26a_is_co_worker_30_DisplayName) {
		this.f26a_is_co_worker_30_DisplayName = f26a_is_co_worker_30_DisplayName;
	}

	public String getL24_is_waiting_trial_Code() {
		return l24_is_waiting_trial_Code;
	}

	public void setL24_is_waiting_trial_Code(String l24_is_waiting_trial_Code) {
		this.l24_is_waiting_trial_Code = l24_is_waiting_trial_Code;
	}

	public String getL24_is_waiting_trial_DisplayName() {
		return l24_is_waiting_trial_DisplayName;
	}

	public void setL24_is_waiting_trial_DisplayName(String l24_is_waiting_trial_DisplayName) {
		this.l24_is_waiting_trial_DisplayName = l24_is_waiting_trial_DisplayName;
	}

	public Integer getL27_days_illegal() {
		return l27_days_illegal;
	}

	public void setL27_days_illegal(Integer l27_days_illegal) {
		this.l27_days_illegal = l27_days_illegal;
	}

	public String getL28_patient_rating_Code() {
		return l28_patient_rating_Code;
	}

	public void setL28_patient_rating_Code(String l28_patient_rating_Code) {
		this.l28_patient_rating_Code = l28_patient_rating_Code;
	}

	public String getL28_patient_rating_DisplayName() {
		return l28_patient_rating_DisplayName;
	}

	public void setL28_patient_rating_DisplayName(String l28_patient_rating_DisplayName) {
		this.l28_patient_rating_DisplayName = l28_patient_rating_DisplayName;
	}

	public String getL29_patient_rating_Code() {
		return l29_patient_rating_Code;
	}

	public void setL29_patient_rating_Code(String l29_patient_rating_Code) {
		this.l29_patient_rating_Code = l29_patient_rating_Code;
	}

	public String getL29_patient_rating_DisplayName() {
		return l29_patient_rating_DisplayName;
	}

	public void setL29_patient_rating_DisplayName(String l29_patient_rating_DisplayName) {
		this.l29_patient_rating_DisplayName = l29_patient_rating_DisplayName;
	}

	public Integer getE17_income_illegal() {
		return e17_income_illegal;
	}

	public void setE17_income_illegal(Integer e17_income_illegal) {
		this.e17_income_illegal = e17_income_illegal;
	}

	public String getRecommendationXml() {
		return recommendationXml;
	}

	public void setRecommendationXml(String recommendationXml) {
		this.recommendationXml = recommendationXml;
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	public String getNote1() {
		return note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNote2() {
		return note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

}
