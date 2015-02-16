package com.feisystems.bham.service.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feisystems.bham.util.SimpleMarshaller;
import com.inflexxion.bhcds.contract.ruleexecutionservice.RuleExecutionService;
import com.inflexxion.bhcds.contract.ruleexecutionservice.RuleExecutionServicePortType;
import com.inflexxion.bhcds.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsRequest;
import com.inflexxion.bhcds.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

@Service
public class RulesClientImpl implements RulesClient {

	@Autowired
	private SimpleMarshaller marshaller;

	@Override
	public String getExecutionResponseList(String latentClass, String assessment) {
		
		System.out.println("Latent Class: " + latentClass);
	    String clinicalFacts = "<FactModel><membershipClass><latentClass>" + latentClass + "</latentClass><assessment>" + assessment + "</assessment></membershipClass></FactModel>";
		
		RuleExecutionService ruleExecutionService = new RuleExecutionService();
		RuleExecutionServicePortType port = ruleExecutionService.getRuleExecutionServicePort();
		
		AssertAndExecuteClinicalFactsRequest parameters =  new AssertAndExecuteClinicalFactsRequest();
		parameters.setClinicalFactXmlString(clinicalFacts);
		
		AssertAndExecuteClinicalFactsResponse response = port.assertAndExecuteClinicalFacts(parameters);
		
		String executionResponseContainer = response.getRuleExecutionResponseContainer();
		return executionResponseContainer;
		

	}

}
