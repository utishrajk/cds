package com.feisystems.bham.domain.rules;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/*
 * The Membership Probabilities classes
 * 
 * */
@XmlAccessorType(XmlAccessType.FIELD)
public class MembershipClass {
	
	/** The latentClass. */
	@XmlElement(name="latentClass")
	private LatentClass latentClass;
	
	/** The latentClass. */
	@XmlElement(name="assessment")
	private Assessment assessment;
	
	public void setLatentClass(LatentClass latentClass) {
		this.latentClass = latentClass;
	}
	
	public LatentClass getLatentClass() {
		return latentClass;
	}
	
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}
	
	public Assessment getAssessment() {
		return assessment;
	}

}
