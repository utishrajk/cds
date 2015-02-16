package com.feisystems.bham.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.feisystems.bham.domain.reference.ASIRouteCode;
import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.EpisodeCode;
import com.feisystems.bham.domain.reference.PatientRatingCode;
import com.feisystems.bham.domain.reference.ResponseCode;

@Entity
public class Asi implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotNull
	private String staffId;

	@NotNull
	private String uniqueClientNumber;
	
	@NotNull
	private String requestId;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
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

	@ManyToOne
	private ResponseCode f28b_is_abused_p_lifetime_Code;

	@ManyToOne
	private ResponseCode f29b_is_abused_s_lifetime_Code;

	@ManyToOne
	private ResponseCode p9a_is_suicidal_30_Code;

	@ManyToOne
	private ResponseCode p5a_is_anxiety_30_Code;

	@ManyToOne
	private ResponseCode p4a_is_depression_30_Code;

	@ManyToOne
	private ResponseCode p8a_is_violent_30_Code;

	@ManyToOne
	private ResponseCode p11a_is_prescribed_30_Code;

	@ManyToOne
	private AdministrativeGenderCode genderCode;
	
	private String otherDescription;

	private Integer d3a_heroin_30;

	private Integer d4a_methadone_30;

	private Integer d5a_opiates_30;

	@ManyToOne
	private ASIRouteCode d3c_route_of_intake_type_Code;

	@ManyToOne
	private ASIRouteCode d4c_route_of_intake_type_Code;

	@ManyToOne
	private ASIRouteCode d5c_route_of_intake_type_Code;

	private Integer age;

	private String state;

	@ManyToOne
	private EpisodeCode episodeCode;

	// For Medical Score

	private Integer m6_days_medical_problem;

	@ManyToOne
	private PatientRatingCode m7_patient_rating_Code;

	@ManyToOne
	private PatientRatingCode m8_patient_rating_Code;

	// For Family Score

	@ManyToOne
	private ResponseCode f3_is_satisfied_marital_Code;

	private Integer f30_days_conflict_family;

	@ManyToOne
	private PatientRatingCode f32_patient_rating_Code;

	@ManyToOne
	private PatientRatingCode f34_patient_rating_Code;

	@ManyToOne
	private ResponseCode f18a_is_mother_30_Code;

	@ManyToOne
	private ResponseCode f19a_is_father_30_Code;

	@ManyToOne
	private ResponseCode f20a_is_sibling_30_Code;

	@ManyToOne
	private ResponseCode f21a_is_spouse_30_Code;

	@ManyToOne
	private ResponseCode f22a_is_children_30_Code;

	@ManyToOne
	private ResponseCode f23a_is_other_30_Code;

	@ManyToOne
	private ResponseCode f24a_is_friends_30_Code;

	@ManyToOne
	private ResponseCode f25a_is_neighbor_30_Code;

	@ManyToOne
	private ResponseCode f26a_is_co_worker_30_Code;

	// For Legal Score

	@ManyToOne
	private ResponseCode l24_is_waiting_trial_Code;

	private Integer l27_days_illegal;

	@ManyToOne
	private PatientRatingCode l28_patient_rating_Code;

	@ManyToOne
	private PatientRatingCode l29_patient_rating_Code;

	private Integer e17_income_illegal;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "asi", cascade = CascadeType.ALL)
	private Set<AuditRecommendations> auditRecommendations = new HashSet<AuditRecommendations>();

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

	public ResponseCode getF28b_is_abused_p_lifetime_Code() {
		return f28b_is_abused_p_lifetime_Code;
	}

	public void setF28b_is_abused_p_lifetime_Code(ResponseCode f28b_is_abused_p_lifetime_Code) {
		this.f28b_is_abused_p_lifetime_Code = f28b_is_abused_p_lifetime_Code;
	}

	public ResponseCode getF29b_is_abused_s_lifetime_Code() {
		return f29b_is_abused_s_lifetime_Code;
	}

	public void setF29b_is_abused_s_lifetime_Code(ResponseCode f29b_is_abused_s_lifetime_Code) {
		this.f29b_is_abused_s_lifetime_Code = f29b_is_abused_s_lifetime_Code;
	}

	public ResponseCode getP9a_is_suicidal_30_Code() {
		return p9a_is_suicidal_30_Code;
	}

	public void setP9a_is_suicidal_30_Code(ResponseCode p9a_is_suicidal_30_Code) {
		this.p9a_is_suicidal_30_Code = p9a_is_suicidal_30_Code;
	}

	public ResponseCode getP5a_is_anxiety_30_Code() {
		return p5a_is_anxiety_30_Code;
	}

	public void setP5a_is_anxiety_30_Code(ResponseCode p5a_is_anxiety_30_Code) {
		this.p5a_is_anxiety_30_Code = p5a_is_anxiety_30_Code;
	}

	public ResponseCode getP4a_is_depression_30_Code() {
		return p4a_is_depression_30_Code;
	}

	public void setP4a_is_depression_30_Code(ResponseCode p4a_is_depression_30_Code) {
		this.p4a_is_depression_30_Code = p4a_is_depression_30_Code;
	}

	public ResponseCode getP8a_is_violent_30_Code() {
		return p8a_is_violent_30_Code;
	}

	public void setP8a_is_violent_30_Code(ResponseCode p8a_is_violent_30_Code) {
		this.p8a_is_violent_30_Code = p8a_is_violent_30_Code;
	}

	public ResponseCode getP11a_is_prescribed_30_Code() {
		return p11a_is_prescribed_30_Code;
	}

	public void setP11a_is_prescribed_30_Code(ResponseCode p11a_is_prescribed_30_Code) {
		this.p11a_is_prescribed_30_Code = p11a_is_prescribed_30_Code;
	}

	public AdministrativeGenderCode getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(AdministrativeGenderCode genderCode) {
		this.genderCode = genderCode;
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

	public ASIRouteCode getD3c_route_of_intake_type_Code() {
		return d3c_route_of_intake_type_Code;
	}

	public void setD3c_route_of_intake_type_Code(ASIRouteCode d3c_route_of_intake_type_Code) {
		this.d3c_route_of_intake_type_Code = d3c_route_of_intake_type_Code;
	}

	public ASIRouteCode getD4c_route_of_intake_type_Code() {
		return d4c_route_of_intake_type_Code;
	}

	public void setD4c_route_of_intake_type_Code(ASIRouteCode d4c_route_of_intake_type_Code) {
		this.d4c_route_of_intake_type_Code = d4c_route_of_intake_type_Code;
	}

	public ASIRouteCode getD5c_route_of_intake_type_Code() {
		return d5c_route_of_intake_type_Code;
	}

	public void setD5c_route_of_intake_type_Code(ASIRouteCode d5c_route_of_intake_type_Code) {
		this.d5c_route_of_intake_type_Code = d5c_route_of_intake_type_Code;
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

	public EpisodeCode getEpisodeCode() {
		return episodeCode;
	}

	public void setEpisodeCode(EpisodeCode episodeCode) {
		this.episodeCode = episodeCode;
	}

	public Integer getM6_days_medical_problem() {
		return m6_days_medical_problem;
	}

	public void setM6_days_medical_problem(Integer m6_days_medical_problem) {
		this.m6_days_medical_problem = m6_days_medical_problem;
	}

	public PatientRatingCode getM7_patient_rating_Code() {
		return m7_patient_rating_Code;
	}

	public void setM7_patient_rating_Code(PatientRatingCode m7_patient_rating_Code) {
		this.m7_patient_rating_Code = m7_patient_rating_Code;
	}

	public PatientRatingCode getM8_patient_rating_Code() {
		return m8_patient_rating_Code;
	}

	public void setM8_patient_rating_Code(PatientRatingCode m8_patient_rating_Code) {
		this.m8_patient_rating_Code = m8_patient_rating_Code;
	}

	public ResponseCode getF3_is_satisfied_marital_Code() {
		return f3_is_satisfied_marital_Code;
	}

	public void setF3_is_satisfied_marital_Code(ResponseCode f3_is_satisfied_marital_Code) {
		this.f3_is_satisfied_marital_Code = f3_is_satisfied_marital_Code;
	}

	public Integer getF30_days_conflict_family() {
		return f30_days_conflict_family;
	}

	public void setF30_days_conflict_family(Integer f30_days_conflict_family) {
		this.f30_days_conflict_family = f30_days_conflict_family;
	}

	public PatientRatingCode getF32_patient_rating_Code() {
		return f32_patient_rating_Code;
	}

	public void setF32_patient_rating_Code(PatientRatingCode f32_patient_rating_Code) {
		this.f32_patient_rating_Code = f32_patient_rating_Code;
	}

	public PatientRatingCode getF34_patient_rating_Code() {
		return f34_patient_rating_Code;
	}

	public void setF34_patient_rating_Code(PatientRatingCode f34_patient_rating_Code) {
		this.f34_patient_rating_Code = f34_patient_rating_Code;
	}

	public ResponseCode getF18a_is_mother_30_Code() {
		return f18a_is_mother_30_Code;
	}

	public void setF18a_is_mother_30_Code(ResponseCode f18a_is_mother_30_Code) {
		this.f18a_is_mother_30_Code = f18a_is_mother_30_Code;
	}

	public ResponseCode getF19a_is_father_30_Code() {
		return f19a_is_father_30_Code;
	}

	public void setF19a_is_father_30_Code(ResponseCode f19a_is_father_30_Code) {
		this.f19a_is_father_30_Code = f19a_is_father_30_Code;
	}

	public ResponseCode getF20a_is_sibling_30_Code() {
		return f20a_is_sibling_30_Code;
	}

	public void setF20a_is_sibling_30_Code(ResponseCode f20a_is_sibling_30_Code) {
		this.f20a_is_sibling_30_Code = f20a_is_sibling_30_Code;
	}

	public ResponseCode getF21a_is_spouse_30_Code() {
		return f21a_is_spouse_30_Code;
	}

	public void setF21a_is_spouse_30_Code(ResponseCode f21a_is_spouse_30_Code) {
		this.f21a_is_spouse_30_Code = f21a_is_spouse_30_Code;
	}

	public ResponseCode getF22a_is_children_30_Code() {
		return f22a_is_children_30_Code;
	}

	public void setF22a_is_children_30_Code(ResponseCode f22a_is_children_30_Code) {
		this.f22a_is_children_30_Code = f22a_is_children_30_Code;
	}

	public ResponseCode getF23a_is_other_30_Code() {
		return f23a_is_other_30_Code;
	}

	public void setF23a_is_other_30_Code(ResponseCode f23a_is_other_30_Code) {
		this.f23a_is_other_30_Code = f23a_is_other_30_Code;
	}

	public ResponseCode getF24a_is_friends_30_Code() {
		return f24a_is_friends_30_Code;
	}

	public void setF24a_is_friends_30_Code(ResponseCode f24a_is_friends_30_Code) {
		this.f24a_is_friends_30_Code = f24a_is_friends_30_Code;
	}

	public ResponseCode getF25a_is_neighbor_30_Code() {
		return f25a_is_neighbor_30_Code;
	}

	public void setF25a_is_neighbor_30_Code(ResponseCode f25a_is_neighbor_30_Code) {
		this.f25a_is_neighbor_30_Code = f25a_is_neighbor_30_Code;
	}

	public ResponseCode getF26a_is_co_worker_30_Code() {
		return f26a_is_co_worker_30_Code;
	}

	public void setF26a_is_co_worker_30_Code(ResponseCode f26a_is_co_worker_30_Code) {
		this.f26a_is_co_worker_30_Code = f26a_is_co_worker_30_Code;
	}

	public ResponseCode getL24_is_waiting_trial_Code() {
		return l24_is_waiting_trial_Code;
	}

	public void setL24_is_waiting_trial_Code(ResponseCode l24_is_waiting_trial_Code) {
		this.l24_is_waiting_trial_Code = l24_is_waiting_trial_Code;
	}

	public Integer getL27_days_illegal() {
		return l27_days_illegal;
	}

	public void setL27_days_illegal(Integer l27_days_illegal) {
		this.l27_days_illegal = l27_days_illegal;
	}

	public PatientRatingCode getL28_patient_rating_Code() {
		return l28_patient_rating_Code;
	}

	public void setL28_patient_rating_Code(PatientRatingCode l28_patient_rating_Code) {
		this.l28_patient_rating_Code = l28_patient_rating_Code;
	}

	public PatientRatingCode getL29_patient_rating_Code() {
		return l29_patient_rating_Code;
	}

	public void setL29_patient_rating_Code(PatientRatingCode l29_patient_rating_Code) {
		this.l29_patient_rating_Code = l29_patient_rating_Code;
	}

	public Integer getE17_income_illegal() {
		return e17_income_illegal;
	}

	public void setE17_income_illegal(Integer e17_income_illegal) {
		this.e17_income_illegal = e17_income_illegal;
	}

	public Set<AuditRecommendations> getAuditRecommendations() {
		return auditRecommendations;
	}

	public void setAuditRecommendations(Set<AuditRecommendations> auditRecommendations) {
		this.auditRecommendations = auditRecommendations;
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

	public String getOtherDescription() {
		return otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

}
