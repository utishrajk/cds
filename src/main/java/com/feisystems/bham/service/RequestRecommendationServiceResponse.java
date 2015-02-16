package com.feisystems.bham.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RequestRecommendationServiceResponse")
public class RequestRecommendationServiceResponse {
	
	@XmlElement
	public String message;
	

}
