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

import com.feisystems.bham.domain.reference.AdministrativeGenderCode;
import com.feisystems.bham.domain.reference.EmotionalProblemsCode;
import com.feisystems.bham.domain.reference.HealthIndicatorCode;
import com.feisystems.bham.domain.reference.RouteCode;

@Entity
public class Gpra implements Serializable {

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

	@ManyToOne
	private AdministrativeGenderCode genderCode;

	private String otherDescription;

	private Integer heroinDayCount;

	@ManyToOne
	private RouteCode heroinRoute;

	private Integer morphineCount;

	@ManyToOne
	private RouteCode morphineRoute;

	private Integer diluadidCount;

	@ManyToOne
	private RouteCode diluadidRoute;

	private Integer demerolCount;

	@ManyToOne
	private RouteCode demerolRoute;

	private Integer percocetCount;

	@ManyToOne
	private RouteCode percocetRoute;

	private Integer darvonCount;

	@ManyToOne
	private RouteCode darvonRoute;

	private Integer codeineCount;

	@ManyToOne
	private RouteCode codeineRoute;

	private Integer tylenolCount;

	@ManyToOne
	private RouteCode tylenolRoute;

	private Integer oxycontinCount;

	@ManyToOne
	private RouteCode oxycontinRoute;

	private Integer methadoneCount;

	@ManyToOne
	private RouteCode methadoneRoute;

	@ManyToOne
	private HealthIndicatorCode healthIndicator;

	private Integer depressionCount;

	private Integer anxietyCount;

	private Integer violentCount;

	private Integer psychMedicationCount;

	@ManyToOne
	private EmotionalProblemsCode emotionalProblemsCode;

	private Integer age;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "gpra", cascade = CascadeType.ALL)
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

	public AdministrativeGenderCode getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(AdministrativeGenderCode genderCode) {
		this.genderCode = genderCode;
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	public Integer getHeroinDayCount() {
		return heroinDayCount;
	}

	public void setHeroinDayCount(Integer heroinDayCount) {
		this.heroinDayCount = heroinDayCount;
	}

	public RouteCode getHeroinRoute() {
		return heroinRoute;
	}

	public void setHeroinRoute(RouteCode heroinRoute) {
		this.heroinRoute = heroinRoute;
	}

	public Integer getMorphineCount() {
		return morphineCount;
	}

	public void setMorphineCount(Integer morphineCount) {
		this.morphineCount = morphineCount;
	}

	public RouteCode getMorphineRoute() {
		return morphineRoute;
	}

	public void setMorphineRoute(RouteCode morphineRoute) {
		this.morphineRoute = morphineRoute;
	}

	public Integer getDiluadidCount() {
		return diluadidCount;
	}

	public void setDiluadidCount(Integer diluadidCount) {
		this.diluadidCount = diluadidCount;
	}

	public RouteCode getDiluadidRoute() {
		return diluadidRoute;
	}

	public void setDiluadidRoute(RouteCode diluadidRoute) {
		this.diluadidRoute = diluadidRoute;
	}

	public Integer getDemerolCount() {
		return demerolCount;
	}

	public void setDemerolCount(Integer demerolCount) {
		this.demerolCount = demerolCount;
	}

	public RouteCode getDemerolRoute() {
		return demerolRoute;
	}

	public void setDemerolRoute(RouteCode demerolRoute) {
		this.demerolRoute = demerolRoute;
	}

	public Integer getPercocetCount() {
		return percocetCount;
	}

	public void setPercocetCount(Integer percocetCount) {
		this.percocetCount = percocetCount;
	}

	public RouteCode getPercocetRoute() {
		return percocetRoute;
	}

	public void setPercocetRoute(RouteCode percocetRoute) {
		this.percocetRoute = percocetRoute;
	}

	public Integer getDarvonCount() {
		return darvonCount;
	}

	public void setDarvonCount(Integer darvonCount) {
		this.darvonCount = darvonCount;
	}

	public RouteCode getDarvonRoute() {
		return darvonRoute;
	}

	public void setDarvonRoute(RouteCode darvonRoute) {
		this.darvonRoute = darvonRoute;
	}

	public Integer getCodeineCount() {
		return codeineCount;
	}

	public void setCodeineCount(Integer codeineCount) {
		this.codeineCount = codeineCount;
	}

	public RouteCode getCodeineRoute() {
		return codeineRoute;
	}

	public void setCodeineRoute(RouteCode codeineRoute) {
		this.codeineRoute = codeineRoute;
	}

	public Integer getTylenolCount() {
		return tylenolCount;
	}

	public void setTylenolCount(Integer tylenolCount) {
		this.tylenolCount = tylenolCount;
	}

	public RouteCode getTylenolRoute() {
		return tylenolRoute;
	}

	public void setTylenolRoute(RouteCode tylenolRoute) {
		this.tylenolRoute = tylenolRoute;
	}

	public Integer getOxycontinCount() {
		return oxycontinCount;
	}

	public void setOxycontinCount(Integer oxycontinCount) {
		this.oxycontinCount = oxycontinCount;
	}

	public RouteCode getOxycontinRoute() {
		return oxycontinRoute;
	}

	public void setOxycontinRoute(RouteCode oxycontinRoute) {
		this.oxycontinRoute = oxycontinRoute;
	}

	public Integer getMethadoneCount() {
		return methadoneCount;
	}

	public void setMethadoneCount(Integer methadoneCount) {
		this.methadoneCount = methadoneCount;
	}

	public RouteCode getMethadoneRoute() {
		return methadoneRoute;
	}

	public void setMethadoneRoute(RouteCode methadoneRoute) {
		this.methadoneRoute = methadoneRoute;
	}

	public HealthIndicatorCode getHealthIndicator() {
		return healthIndicator;
	}

	public void setHealthIndicator(HealthIndicatorCode healthIndicator) {
		this.healthIndicator = healthIndicator;
	}

	public Integer getDepressionCount() {
		return depressionCount;
	}

	public void setDepressionCount(Integer depressionCount) {
		this.depressionCount = depressionCount;
	}

	public Integer getAnxietyCount() {
		return anxietyCount;
	}

	public void setAnxietyCount(Integer anxietyCount) {
		this.anxietyCount = anxietyCount;
	}

	public Integer getViolentCount() {
		return violentCount;
	}

	public void setViolentCount(Integer violentCount) {
		this.violentCount = violentCount;
	}

	public Integer getPsychMedicationCount() {
		return psychMedicationCount;
	}

	public void setPsychMedicationCount(Integer psychMedicationCount) {
		this.psychMedicationCount = psychMedicationCount;
	}

	public EmotionalProblemsCode getEmotionalProblemsCode() {
		return emotionalProblemsCode;
	}

	public void setEmotionalProblemsCode(EmotionalProblemsCode emotionalProblemsCode) {
		this.emotionalProblemsCode = emotionalProblemsCode;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

}
