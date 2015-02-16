package com.feisystems.bham.domain.rules;

public class ClinicalRecommendation {
    /*A clinical recommendation is essentially a common procedural terminology (CPT) code. Some examples of CPT code and their displayName
    H0018   Behavioral health; short-term residential (non-hospital residential treatment program)
    H2034   Alcohol and/or drug abuse halfway house services, per diem 
    H0004   Behavioral health counseling and therapy, per 15 minutes 
    H0009   Alcohol and/or drug services; acute detoxification (hospital inpatient) 
    H0006   Alcohol and/or drug services; case management 
    */
    private String code;

    //the codeSystem for CPT is 2.16.840.1.113883.6.12
    private String codeSystem;

    //CPT
    private String codeSystemName;

    //For example, displayName for code H0009 is Alcohol and/or drug services; acute detoxification (hospital inpatient) 
    private String displayName;
    
    public ClinicalRecommendation() {
	}    
    
    public ClinicalRecommendation(String code, String codeSystem, String codeSystemName, String displayName) {
    	this.code = code;
    	this.codeSystem = codeSystem;
    	this.codeSystemName = codeSystemName;
    	this.displayName = displayName;
    }
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeSystem() {
		return codeSystem;
	}

	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

	public String getCodeSystemName() {
		return codeSystemName;
	}

	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
