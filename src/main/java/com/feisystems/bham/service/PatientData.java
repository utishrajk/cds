package com.feisystems.bham.service;

import com.feisystems.bham.util.Constant;

public class PatientData {

	// GPRA
	Integer overall_health_indicator;
	Integer age_covariate;
	Integer heroin_indicator;
	Integer morphine_indicator;
	Integer diluadid_indicator;
	Integer demerol_indicator;
	Integer percocet_indicator;
	Integer darvon_indicator;
	Integer codeine_indicator;
	Integer tylenol_indicator;
	Integer oxyco_indicator;
	Integer methadone_indicator;
	Integer other_opioid_indicator;
	Integer rx_or_meth_no_heroin_covariate;
	Integer heroin_only_covariate;

	Integer gpra_route_indicator;
	Integer psych_indicator;
	Integer anxiety_indicator;
	Integer depression_indicator;
	Integer violent_behavior_indicator;
	Integer psych_med_indicator;
	Integer psych_any_indicator;

	// ASI
	Integer employmentprob;
	Integer intoxication30;
	Integer legal_4c;
	Integer medical_3c;
	Integer family_4c;
	Integer phyabuse;
	Integer sexabuse;
	Integer abuse;
	Integer suicidal;
	Integer anxiety;
	Integer depression;
	Integer anxdep;
	Integer violent;
	Integer prescribed;
	Integer heroin30any;
	Integer methadone30any;
	Integer opiates30any;
	Integer opicovd1;
	Integer opicovd2;
	Integer opicovd3;
	Integer heroin30r;
	Integer methadone30r;
	Integer opiates30r;
	Integer route;
	Integer age;
	Integer zip3;
	String state;
	Integer priorEpisode;

	// Common
	Integer gender_covariate;

	public PatientData() {
		super();
		// gpra
		overall_health_indicator = Constant.NOT_AVAILABLE;
		age_covariate = Constant.NOT_AVAILABLE;
		heroin_indicator = Constant.NOT_AVAILABLE;
		morphine_indicator = Constant.NOT_AVAILABLE;
		diluadid_indicator = Constant.NOT_AVAILABLE;
		demerol_indicator = Constant.NOT_AVAILABLE;
		percocet_indicator = Constant.NOT_AVAILABLE;
		darvon_indicator = Constant.NOT_AVAILABLE;
		codeine_indicator = Constant.NOT_AVAILABLE;
		tylenol_indicator = Constant.NOT_AVAILABLE;
		oxyco_indicator = Constant.NOT_AVAILABLE;
		methadone_indicator = Constant.NOT_AVAILABLE;
		other_opioid_indicator = Constant.NOT_AVAILABLE;
		rx_or_meth_no_heroin_covariate = Constant.NOT_AVAILABLE;
		heroin_only_covariate = Constant.NOT_AVAILABLE;
		gpra_route_indicator = Constant.NOT_AVAILABLE;
		psych_indicator = Constant.NOT_AVAILABLE;
		anxiety_indicator = Constant.NOT_AVAILABLE;
		depression_indicator = Constant.NOT_AVAILABLE;
		violent_behavior_indicator = Constant.NOT_AVAILABLE;
		psych_med_indicator = Constant.NOT_AVAILABLE;
		psych_any_indicator = Constant.NOT_AVAILABLE;

		// asi
		employmentprob = Constant.NOT_AVAILABLE;
		intoxication30 = Constant.NOT_AVAILABLE;
		legal_4c = Constant.NOT_AVAILABLE;
		medical_3c = Constant.NOT_AVAILABLE;
		family_4c = Constant.NOT_AVAILABLE;
		phyabuse = Constant.NOT_AVAILABLE;
		sexabuse = Constant.NOT_AVAILABLE;
		abuse = Constant.NOT_AVAILABLE;
		suicidal = Constant.NOT_AVAILABLE;
		anxiety = Constant.NOT_AVAILABLE;
		depression = Constant.NOT_AVAILABLE;
		anxdep = Constant.NOT_AVAILABLE;
		violent = Constant.NOT_AVAILABLE;
		prescribed = Constant.NOT_AVAILABLE;
		heroin30any = Constant.NOT_AVAILABLE;
		methadone30any = Constant.NOT_AVAILABLE;
		opiates30any = Constant.NOT_AVAILABLE;
		opicovd1 = Constant.NOT_AVAILABLE;
		opicovd2 = Constant.NOT_AVAILABLE;
		opicovd3 = Constant.NOT_AVAILABLE;
		heroin30r = Constant.NOT_AVAILABLE;
		methadone30r = Constant.NOT_AVAILABLE;
		opiates30r = Constant.NOT_AVAILABLE;
		route = Constant.NOT_AVAILABLE;
		age = Constant.NOT_AVAILABLE;
		zip3 = Constant.NOT_AVAILABLE;
		state = null;
		priorEpisode = Constant.NOT_AVAILABLE;

		// common
		gender_covariate = Constant.NOT_AVAILABLE;
	}

	public Integer getOverall_health_indicator() {
		return overall_health_indicator;
	}

	public void setOverall_health_indicator(Integer overall_health_indicator) {
		this.overall_health_indicator = overall_health_indicator;
	}

	public Integer getAge_covariate() {
		return age_covariate;
	}

	public void setAge_covariate(Integer age_covariate) {
		this.age_covariate = age_covariate;
	}

	public Integer getHeroin_indicator() {
		return heroin_indicator;
	}

	public void setHeroin_indicator(Integer heroin_indicator) {
		this.heroin_indicator = heroin_indicator;
	}

	public Integer getMorphine_indicator() {
		return morphine_indicator;
	}

	public void setMorphine_indicator(Integer morphine_indicator) {
		this.morphine_indicator = morphine_indicator;
	}

	public Integer getDiluadid_indicator() {
		return diluadid_indicator;
	}

	public void setDiluadid_indicator(Integer diluadid_indicator) {
		this.diluadid_indicator = diluadid_indicator;
	}

	public Integer getDemerol_indicator() {
		return demerol_indicator;
	}

	public void setDemerol_indicator(Integer demerol_indicator) {
		this.demerol_indicator = demerol_indicator;
	}

	public Integer getPercocet_indicator() {
		return percocet_indicator;
	}

	public void setPercocet_indicator(Integer percocet_indicator) {
		this.percocet_indicator = percocet_indicator;
	}

	public Integer getDarvon_indicator() {
		return darvon_indicator;
	}

	public void setDarvon_indicator(Integer darvon_indicator) {
		this.darvon_indicator = darvon_indicator;
	}

	public Integer getCodeine_indicator() {
		return codeine_indicator;
	}

	public void setCodeine_indicator(Integer codeine_indicator) {
		this.codeine_indicator = codeine_indicator;
	}

	public Integer getTylenol_indicator() {
		return tylenol_indicator;
	}

	public void setTylenol_indicator(Integer tylenol_indicator) {
		this.tylenol_indicator = tylenol_indicator;
	}

	public Integer getOxyco_indicator() {
		return oxyco_indicator;
	}

	public void setOxyco_indicator(Integer oxyco_indicator) {
		this.oxyco_indicator = oxyco_indicator;
	}

	public Integer getMethadone_indicator() {
		return methadone_indicator;
	}

	public void setMethadone_indicator(Integer methadone_indicator) {
		this.methadone_indicator = methadone_indicator;
	}

	public Integer getOther_opioid_indicator() {
		return other_opioid_indicator;
	}

	public void setOther_opioid_indicator(Integer other_opioid_indicator) {
		this.other_opioid_indicator = other_opioid_indicator;
	}

	public Integer getRx_or_meth_no_heroin_covariate() {
		return rx_or_meth_no_heroin_covariate;
	}

	public void setRx_or_meth_no_heroin_covariate(Integer rx_or_meth_no_heroin_covariate) {
		this.rx_or_meth_no_heroin_covariate = rx_or_meth_no_heroin_covariate;
	}

	public Integer getHeroin_only_covariate() {
		return heroin_only_covariate;
	}

	public void setHeroin_only_covariate(Integer heroin_only_covariate) {
		this.heroin_only_covariate = heroin_only_covariate;
	}

	public Integer getGpra_route_indicator() {
		return gpra_route_indicator;
	}

	public void setGpra_route_indicator(Integer gpra_route_indicator) {
		this.gpra_route_indicator = gpra_route_indicator;
	}

	public Integer getPsych_indicator() {
		return psych_indicator;
	}

	public void setPsych_indicator(Integer psych_indicator) {
		this.psych_indicator = psych_indicator;
	}

	public Integer getAnxiety_indicator() {
		return anxiety_indicator;
	}

	public void setAnxiety_indicator(Integer anxiety_indicator) {
		this.anxiety_indicator = anxiety_indicator;
	}

	public Integer getDepression_indicator() {
		return depression_indicator;
	}

	public void setDepression_indicator(Integer depression_indicator) {
		this.depression_indicator = depression_indicator;
	}

	public Integer getViolent_behavior_indicator() {
		return violent_behavior_indicator;
	}

	public void setViolent_behavior_indicator(Integer violent_behavior_indicator) {
		this.violent_behavior_indicator = violent_behavior_indicator;
	}

	public Integer getPsych_med_indicator() {
		return psych_med_indicator;
	}

	public void setPsych_med_indicator(Integer psych_med_indicator) {
		this.psych_med_indicator = psych_med_indicator;
	}

	public Integer getPsych_any_indicator() {
		return psych_any_indicator;
	}

	public void setPsych_any_indicator(Integer psych_any_indicator) {
		this.psych_any_indicator = psych_any_indicator;
	}

	public Integer getEmploymentprob() {
		return employmentprob;
	}

	public void setEmploymentprob(Integer employmentprob) {
		this.employmentprob = employmentprob;
	}

	public Integer getIntoxication30() {
		return intoxication30;
	}

	public void setIntoxication30(Integer intoxication30) {
		this.intoxication30 = intoxication30;
	}

	public Integer getLegal_4c() {
		return legal_4c;
	}

	public void setLegal_4c(Integer legal_4c) {
		this.legal_4c = legal_4c;
	}

	public Integer getMedical_3c() {
		return medical_3c;
	}

	public void setMedical_3c(Integer medical_3c) {
		this.medical_3c = medical_3c;
	}

	public Integer getFamily_4c() {
		return family_4c;
	}

	public void setFamily_4c(Integer family_4c) {
		this.family_4c = family_4c;
	}

	public Integer getPhyabuse() {
		return phyabuse;
	}

	public void setPhyabuse(Integer phyabuse) {
		this.phyabuse = phyabuse;
	}

	public Integer getSexabuse() {
		return sexabuse;
	}

	public void setSexabuse(Integer sexabuse) {
		this.sexabuse = sexabuse;
	}

	public Integer getAbuse() {
		return abuse;
	}

	public void setAbuse(Integer abuse) {
		this.abuse = abuse;
	}

	public Integer getSuicidal() {
		return suicidal;
	}

	public void setSuicidal(Integer suicidal) {
		this.suicidal = suicidal;
	}

	public Integer getAnxiety() {
		return anxiety;
	}

	public void setAnxiety(Integer anxiety) {
		this.anxiety = anxiety;
	}

	public Integer getDepression() {
		return depression;
	}

	public void setDepression(Integer depression) {
		this.depression = depression;
	}

	public Integer getAnxdep() {
		return anxdep;
	}

	public void setAnxdep(Integer anxdep) {
		this.anxdep = anxdep;
	}

	public Integer getViolent() {
		return violent;
	}

	public void setViolent(Integer violent) {
		this.violent = violent;
	}

	public Integer getPrescribed() {
		return prescribed;
	}

	public void setPrescribed(Integer prescribed) {
		this.prescribed = prescribed;
	}

	public Integer getHeroin30any() {
		return heroin30any;
	}

	public void setHeroin30any(Integer heroin30any) {
		this.heroin30any = heroin30any;
	}

	public Integer getMethadone30any() {
		return methadone30any;
	}

	public void setMethadone30any(Integer methadone30any) {
		this.methadone30any = methadone30any;
	}

	public Integer getOpiates30any() {
		return opiates30any;
	}

	public void setOpiates30any(Integer opiates30any) {
		this.opiates30any = opiates30any;
	}

	public Integer getOpicovd1() {
		return opicovd1;
	}

	public void setOpicovd1(Integer opicovd1) {
		this.opicovd1 = opicovd1;
	}

	public Integer getOpicovd2() {
		return opicovd2;
	}

	public void setOpicovd2(Integer opicovd2) {
		this.opicovd2 = opicovd2;
	}

	public Integer getOpicovd3() {
		return opicovd3;
	}

	public void setOpicovd3(Integer opicovd3) {
		this.opicovd3 = opicovd3;
	}

	public Integer getHeroin30r() {
		return heroin30r;
	}

	public void setHeroin30r(Integer heroin30r) {
		this.heroin30r = heroin30r;
	}

	public Integer getMethadone30r() {
		return methadone30r;
	}

	public void setMethadone30r(Integer methadone30r) {
		this.methadone30r = methadone30r;
	}

	public Integer getOpiates30r() {
		return opiates30r;
	}

	public void setOpiates30r(Integer opiates30r) {
		this.opiates30r = opiates30r;
	}

	public Integer getRoute() {
		return route;
	}

	public void setRoute(Integer route) {
		this.route = route;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getZip3() {
		return zip3;
	}

	public void setZip3(Integer zip3) {
		this.zip3 = zip3;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getPriorEpisode() {
		return priorEpisode;
	}

	public void setPriorEpisode(Integer priorEpisode) {
		this.priorEpisode = priorEpisode;
	}

	public Integer getGender_covariate() {
		return gender_covariate;
	}

	public void setGender_covariate(Integer gender_covariate) {
		this.gender_covariate = gender_covariate;
	}

}
