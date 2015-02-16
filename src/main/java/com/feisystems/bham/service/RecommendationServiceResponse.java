package com.feisystems.bham.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RecommendationServiceResponse")
public class RecommendationServiceResponse {
	
	@XmlElement
	public String requestId;
	
	@XmlElement
	public String staffId;

	@XmlElement
	public String message;

	@XmlElement
	public String status;

	@XmlElement
	public String uniqueClientNumber;

}
