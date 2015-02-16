package com.feisystems.bham.service;

import java.util.ArrayList;
import java.util.List;

public class RecommendationsDto {
	
	private List<String> recommendations = new ArrayList<String>();

	private List<String> services = new ArrayList<String>();
	
	private List<String> treatments = new ArrayList<String>();
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<String> recommendations) {
		this.recommendations = recommendations;
	}

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	public List<String> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<String> treatments) {
		this.treatments = treatments;
	}

}
