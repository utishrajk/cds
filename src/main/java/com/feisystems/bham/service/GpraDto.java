package com.feisystems.bham.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feisystems.bham.util.Constant;

public class GpraDto implements DataElementsDto {

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

	private String genderCode;

	private String genderDisplayName;

	private String otherDescription;

	private Integer heroinDayCount;

	private String heroinRouteCode;

	private String heroinRouteDisplayName;

	private Integer morphineCount;

	private String morphineRouteCode;

	private String morphineRouteDisplayName;

	private Integer diluadidCount;

	private String diluadidRouteCode;

	private String diluadidRouteDisplayName;

	private Integer demerolCount;

	private String demerolRouteCode;

	private String demerolRouteDisplayName;

	private Integer percocetCount;

	private String percocetRouteCode;

	private String percocetRouteDisplayName;

	private Integer darvonCount;

	private String darvonRouteCode;

	private String darvonRouteDisplayName;

	private Integer codeineCount;

	private String codeineRouteCode;

	private String codeineRouteDisplayName;

	private Integer tylenolCount;

	private String tylenolRouteCode;

	private String tylenolRouteDisplayName;

	private Integer oxycontinCount;

	private String oxycontinRouteCode;

	private String oxycontinRouteDisplayName;

	private Integer methadoneCount;

	private String methadoneRouteCode;

	private String methadoneRouteDisplayName;

	private String healthIndicatorCode;

	private String healthIndicatorCodeDisplayName;

	private Integer depressionCount;

	private Integer anxietyCount;

	private Integer violentCount;

	private Integer psychMedicationCount;

	private String emotionalProblemsCode;

	private String emotionalProblemsDisplayName;

	private Integer age;

	private String recommendationXml;

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

	public String getHeroinRouteCode() {
		return heroinRouteCode;
	}

	public void setHeroinRouteCode(String heroinRouteCode) {
		this.heroinRouteCode = heroinRouteCode;
	}

	public String getHeroinRouteDisplayName() {
		return heroinRouteDisplayName;
	}

	public void setHeroinRouteDisplayName(String heroinRouteDisplayName) {
		this.heroinRouteDisplayName = heroinRouteDisplayName;
	}

	public Integer getMorphineCount() {
		return morphineCount;
	}

	public void setMorphineCount(Integer morphineCount) {
		this.morphineCount = morphineCount;
	}

	public String getMorphineRouteCode() {
		return morphineRouteCode;
	}

	public void setMorphineRouteCode(String morphineRouteCode) {
		this.morphineRouteCode = morphineRouteCode;
	}

	public String getMorphineRouteDisplayName() {
		return morphineRouteDisplayName;
	}

	public void setMorphineRouteDisplayName(String morphineRouteDisplayName) {
		this.morphineRouteDisplayName = morphineRouteDisplayName;
	}

	public Integer getDiluadidCount() {
		return diluadidCount;
	}

	public void setDiluadidCount(Integer diluadidCount) {
		this.diluadidCount = diluadidCount;
	}

	public String getDiluadidRouteCode() {
		return diluadidRouteCode;
	}

	public void setDiluadidRouteCode(String diluadidRouteCode) {
		this.diluadidRouteCode = diluadidRouteCode;
	}

	public String getDiluadidRouteDisplayName() {
		return diluadidRouteDisplayName;
	}

	public void setDiluadidRouteDisplayName(String diluadidRouteDisplayName) {
		this.diluadidRouteDisplayName = diluadidRouteDisplayName;
	}

	public Integer getDemerolCount() {
		return demerolCount;
	}

	public void setDemerolCount(Integer demerolCount) {
		this.demerolCount = demerolCount;
	}

	public String getDemerolRouteCode() {
		return demerolRouteCode;
	}

	public void setDemerolRouteCode(String demerolRouteCode) {
		this.demerolRouteCode = demerolRouteCode;
	}

	public String getDemerolRouteDisplayName() {
		return demerolRouteDisplayName;
	}

	public void setDemerolRouteDisplayName(String demerolRouteDisplayName) {
		this.demerolRouteDisplayName = demerolRouteDisplayName;
	}

	public Integer getPercocetCount() {
		return percocetCount;
	}

	public void setPercocetCount(Integer percocetCount) {
		this.percocetCount = percocetCount;
	}

	public String getPercocetRouteCode() {
		return percocetRouteCode;
	}

	public void setPercocetRouteCode(String percocetRouteCode) {
		this.percocetRouteCode = percocetRouteCode;
	}

	public String getPercocetRouteDisplayName() {
		return percocetRouteDisplayName;
	}

	public void setPercocetRouteDisplayName(String percocetRouteDisplayName) {
		this.percocetRouteDisplayName = percocetRouteDisplayName;
	}

	public Integer getDarvonCount() {
		return darvonCount;
	}

	public void setDarvonCount(Integer darvonCount) {
		this.darvonCount = darvonCount;
	}

	public String getDarvonRouteCode() {
		return darvonRouteCode;
	}

	public void setDarvonRouteCode(String darvonRouteCode) {
		this.darvonRouteCode = darvonRouteCode;
	}

	public String getDarvonRouteDisplayName() {
		return darvonRouteDisplayName;
	}

	public void setDarvonRouteDisplayName(String darvonRouteDisplayName) {
		this.darvonRouteDisplayName = darvonRouteDisplayName;
	}

	public Integer getCodeineCount() {
		return codeineCount;
	}

	public void setCodeineCount(Integer codeineCount) {
		this.codeineCount = codeineCount;
	}

	public String getCodeineRouteCode() {
		return codeineRouteCode;
	}

	public void setCodeineRouteCode(String codeineRouteCode) {
		this.codeineRouteCode = codeineRouteCode;
	}

	public String getCodeineRouteDisplayName() {
		return codeineRouteDisplayName;
	}

	public void setCodeineRouteDisplayName(String codeineRouteDisplayName) {
		this.codeineRouteDisplayName = codeineRouteDisplayName;
	}

	public Integer getTylenolCount() {
		return tylenolCount;
	}

	public void setTylenolCount(Integer tylenolCount) {
		this.tylenolCount = tylenolCount;
	}

	public String getTylenolRouteCode() {
		return tylenolRouteCode;
	}

	public void setTylenolRouteCode(String tylenolRouteCode) {
		this.tylenolRouteCode = tylenolRouteCode;
	}

	public String getTylenolRouteDisplayName() {
		return tylenolRouteDisplayName;
	}

	public void setTylenolRouteDisplayName(String tylenolRouteDisplayName) {
		this.tylenolRouteDisplayName = tylenolRouteDisplayName;
	}

	public Integer getOxycontinCount() {
		return oxycontinCount;
	}

	public void setOxycontinCount(Integer oxycontinCount) {
		this.oxycontinCount = oxycontinCount;
	}

	public String getOxycontinRouteCode() {
		return oxycontinRouteCode;
	}

	public void setOxycontinRouteCode(String oxycontinRouteCode) {
		this.oxycontinRouteCode = oxycontinRouteCode;
	}

	public String getOxycontinRouteDisplayName() {
		return oxycontinRouteDisplayName;
	}

	public void setOxycontinRouteDisplayName(String oxycontinRouteDisplayName) {
		this.oxycontinRouteDisplayName = oxycontinRouteDisplayName;
	}

	public Integer getMethadoneCount() {
		return methadoneCount;
	}

	public void setMethadoneCount(Integer methadoneCount) {
		this.methadoneCount = methadoneCount;
	}

	public String getMethadoneRouteCode() {
		return methadoneRouteCode;
	}

	public void setMethadoneRouteCode(String methadoneRouteCode) {
		this.methadoneRouteCode = methadoneRouteCode;
	}

	public String getMethadoneRouteDisplayName() {
		return methadoneRouteDisplayName;
	}

	public void setMethadoneRouteDisplayName(String methadoneRouteDisplayName) {
		this.methadoneRouteDisplayName = methadoneRouteDisplayName;
	}

	public String getHealthIndicatorCode() {
		return healthIndicatorCode;
	}

	public void setHealthIndicatorCode(String healthIndicatorCode) {
		this.healthIndicatorCode = healthIndicatorCode;
	}

	public String getHealthIndicatorCodeDisplayName() {
		return healthIndicatorCodeDisplayName;
	}

	public void setHealthIndicatorCodeDisplayName(String healthIndicatorCodeDisplayName) {
		this.healthIndicatorCodeDisplayName = healthIndicatorCodeDisplayName;
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

	public String getEmotionalProblemsCode() {
		return emotionalProblemsCode;
	}

	public void setEmotionalProblemsCode(String emotionalProblemsCode) {
		this.emotionalProblemsCode = emotionalProblemsCode;
	}

	public String getEmotionalProblemsDisplayName() {
		return emotionalProblemsDisplayName;
	}

	public void setEmotionalProblemsDisplayName(String emotionalProblemsDisplayName) {
		this.emotionalProblemsDisplayName = emotionalProblemsDisplayName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getRecommendationXml() {
		return recommendationXml;
	}

	public void setRecommendationXml(String recommendationXml) {
		this.recommendationXml = recommendationXml;
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
