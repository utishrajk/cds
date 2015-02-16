
package com.feisystems.bham.domain.rules;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class RuleExecutionResponse.
 */
@XmlRootElement(name="ruleExecutionResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleExecutionResponse {
	
	private String description;
	
	@XmlElementWrapper(name="recommendations")
	@XmlElement(name="recommendationItem")
	private List<RecommendationItem> recommendationList ;
	
	@XmlElementWrapper(name="Treatment")
	@XmlElement(name="treatmentItem")
	private List<TreatmentItem> treatmentList ;
	
	@XmlElementWrapper(name="Services")
	@XmlElement(name="serviceItem")
	private List<ServiceItem> serviceList ;
	
	
	/**
	 * Instantiates a new rule execution response.
	 */
	public RuleExecutionResponse() {
		this.recommendationList  = new ArrayList<RecommendationItem>();
		this.treatmentList  = new ArrayList<TreatmentItem>();
		this.serviceList  = new ArrayList<ServiceItem>();
	}

		
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<RecommendationItem> getRecommendationList() {
		return recommendationList;
	}


	public void setRecommendationList(List<RecommendationItem> recommendationList) {
		this.recommendationList = recommendationList;
	}


	public List<TreatmentItem> getTreatmentList() {
		return treatmentList;
	}


	public void setTreatmentList(List<TreatmentItem> treatmentList) {
		this.treatmentList = treatmentList;
	}


	public List<ServiceItem> getServiceList() {
		return serviceList;
	}


	public void setServiceList(List<ServiceItem> serviceList) {
		this.serviceList = serviceList;
	}


	/**
	 * Adds the clinical recommendation to the list of clinical recommendation.
	 *
	 * @param clinicalRecommendation the clinical recommendation
	 */
	public void addRecommendationItem(RecommendationItem recommendationItem) {
		getRecommendationList().add(recommendationItem);
	}

	public void addTreatmentItem(TreatmentItem treatmentItment) {
		getTreatmentList().add(treatmentItment);
	}

	public void addServiceItem(ServiceItem serviceItem) {
		getServiceList().add(serviceItem);
	}

}
