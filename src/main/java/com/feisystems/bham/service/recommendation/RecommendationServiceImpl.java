package com.feisystems.bham.service.recommendation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.AuditRecommendations;
import com.feisystems.bham.domain.AuditRecommendationsRepository;
import com.feisystems.bham.domain.asi.AsiVariableContainer;
import com.feisystems.bham.domain.gpra.AbstractVariableContainer;
import com.feisystems.bham.domain.gpra.GpraVariableContainer;
import com.feisystems.bham.domain.rules.RecommendationItem;
import com.feisystems.bham.domain.rules.RuleExecutionContainer;
import com.feisystems.bham.domain.rules.RuleExecutionResponse;
import com.feisystems.bham.domain.rules.ServiceItem;
import com.feisystems.bham.domain.rules.TreatmentItem;
import com.feisystems.bham.exceptions.ClinicalDataNotFoundException;
import com.feisystems.bham.exceptions.IncorrectInputDataException;
import com.feisystems.bham.exceptions.SimpleSoapException;
import com.feisystems.bham.exceptions.SimpleSoapExceptionBean;
import com.feisystems.bham.service.ASIMapper;
import com.feisystems.bham.service.ASIService;
import com.feisystems.bham.service.AsiDto;
import com.feisystems.bham.service.DataElementsService;
import com.feisystems.bham.service.GpraDto;
import com.feisystems.bham.service.GpraMapper;
import com.feisystems.bham.service.LCAService;
import com.feisystems.bham.service.PatientData;
import com.feisystems.bham.service.RecommendationServiceResponse;
import com.feisystems.bham.service.RecommendationsDto;
import com.feisystems.bham.service.UsernameService;
import com.feisystems.bham.util.Constant;
import com.feisystems.bham.util.SimpleMarshaller;
import com.feisystems.bham.util.SimpleMarshallerException;

@Service
@Transactional(rollbackFor = { ClinicalDataNotFoundException.class })
@WebService(
		portName = "RecommendationServiceImplPort",
		serviceName = "RecommendationServiceImplService",
		endpointInterface = "com.feisystems.bham.service.recommendation.RecommendationService",
		wsdlLocation = "WEB-INF/wsdl/RecommendationService.wsdl",
		targetNamespace = "http://recommendation.service.bham.feisystems.com/")
public class RecommendationServiceImpl implements RecommendationService {

	@Autowired
	private RulesClient rulesClient;

	@Autowired
	private DataElementsService dataElementsService;

	@Autowired
	AuditRecommendationsRepository auditRecommendationsRepository;

	@Autowired
	private SimpleMarshaller marshaller;

	public static final String ASI = "ASI";
	public static final String GPRA = "GPRA";

	@Autowired
	private GpraMapper gpraMapper;

	@Autowired
	private ASIMapper asiMapper;

	@Autowired
	private ASIService asiService;

	@Autowired
	private LCAService lcaService;
	
	@Autowired
	private UsernameService usernameService;
	
	@Override
	public RecommendationServiceResponse saveAssessments(AbstractVariableContainer container) throws SimpleSoapException {

		RecommendationServiceResponse response = new RecommendationServiceResponse();
		response.requestId = container.requestId;
		response.staffId = container.staffId;
		response.uniqueClientNumber = container.uniqueClientNumber;

		if (container.variableContainer instanceof GpraVariableContainer) {
			GpraVariableContainer gpraVariableContainer = (GpraVariableContainer) container.variableContainer;
			try {

				dataElementsService.saveGpraVariableContainer(gpraVariableContainer, container.requestId, container.staffId, container.uniqueClientNumber, null);

			} catch (Exception e) {
				e.printStackTrace();
				response.message = "Cannot Save GPRA Variables";
				response.status = "FAILURE";
				return response;
			}

			response.message = "GPRA Variables Saved";
			response.status = "SUCCESS";
			return response;

		} else if (container.variableContainer instanceof AsiVariableContainer) {
			AsiVariableContainer asiVariableContainer = (AsiVariableContainer) container.variableContainer;
			try {
				dataElementsService.saveAsiVariableContainer(asiVariableContainer, container.requestId, container.staffId, container.uniqueClientNumber, null);
			} catch (Exception e) {
				e.printStackTrace();
				response.message = "Cannot Save ASI Variables";
				response.status = "FAILURE";
				return response;
			}

			response.message = "ASI Variables Saved";
			response.status = "SUCCESS";
			return response;

		}

		throw new SimpleSoapException("Invalid Assessment", new SimpleSoapExceptionBean());
	}

	private void checkAllFieldsAvailable(Integer[] inputs) {
		for (int i = 1; i < inputs.length; i++) {
			if (inputs[i].equals(Constant.NOT_AVAILABLE)) {
				throw new IncorrectInputDataException("Incorrect inputs");
			}
		}
	}

	@Override
	@WebMethod(exclude = true)
	public void saveGpraRecommendations(GpraDto gpraDto) {

		PatientData patientData = new PatientData();

		gpraMapper.map(gpraDto, patientData);
		Integer[] inputs = new Integer[10];

		inputs[1] = patientData.getOverall_health_indicator();
		inputs[2] = patientData.getGpra_route_indicator();
		inputs[3] = patientData.getPsych_any_indicator();
		inputs[4] = patientData.getViolent_behavior_indicator();
		inputs[5] = patientData.getPsych_med_indicator();
		inputs[6] = patientData.getAge_covariate();
		
		// Reverse Code the covariates
		if (patientData.getGender_covariate() == 1) {
			inputs[7] = 0;
		} else {
			inputs[7] = 1;
		}
		
		if (patientData.getRx_or_meth_no_heroin_covariate() == 1) {
			inputs[8] = 0;
		} else {
			inputs[8] = 1;
		}

		if (patientData.getHeroin_only_covariate() == 1) {
			inputs[9] = 0;
		} else {
			inputs[9] = 1;
		}

		for (int i = 1; i < 10; i++) {
			System.out.println("-----Input" + i + ":" + inputs[i]);
		}

		checkAllFieldsAvailable(inputs);

		String probClass = lcaService.calculate(inputs);
		System.out.println("CLASS: " + probClass);

		String recommendationXml = rulesClient.getExecutionResponseList(probClass, GPRA);
		gpraDto.setRecommendationXml(recommendationXml);
		
		if(gpraDto.getRequestId() == null) {
			gpraDto.setRequestId(generateRandomID().toString());
		}

		dataElementsService.saveGpra(gpraDto);

	}
	
	private UUID generateRandomID() {
		return UUID.randomUUID();
	}

	@Override
	@WebMethod(exclude = true)
	public void saveAsiRecommendations(AsiDto asiDto) {
		PatientData patientData = new PatientData();

		asiMapper.map(asiDto, patientData);

		Integer[] inputs = new Integer[16];

		inputs[0] = patientData.getMedical_3c();
		inputs[1] = patientData.getEmploymentprob();
		inputs[2] = patientData.getIntoxication30();
		inputs[3] = patientData.getFamily_4c();
		inputs[4] = patientData.getAbuse();
		inputs[5] = patientData.getSuicidal();
		inputs[6] = patientData.getViolent();
		inputs[7] = patientData.getLegal_4c();
		inputs[8] = patientData.getPrescribed();
		inputs[9] = patientData.getRoute();
		inputs[10] = patientData.getAnxdep();
		inputs[11] = patientData.getAge();
		inputs[12] = patientData.getGender_covariate();
		inputs[13] = patientData.getPriorEpisode();
		inputs[14] = patientData.getOpicovd1();
		inputs[15] = patientData.getOpicovd2();

		for (int i = 1; i < 16; i++) {
			System.out.println("-----Input" + i + ":" + inputs[i]);
		}

		checkAllFieldsAvailable(inputs);

		String probClass = asiService.calculate(inputs);
		System.out.println("CLASS: " + probClass);

		String recommendationXml = rulesClient.getExecutionResponseList(probClass, ASI);
		asiDto.setRecommendationXml(recommendationXml);

		dataElementsService.saveAsi(asiDto);
	}

	@Override
	@WebMethod(exclude = true)
	public RecommendationsDto getRecommendations(String requestId) {

		RuleExecutionContainer ruleExecutionContainer = null;

		PageRequest pageRequest = new PageRequest(0, 1, Sort.Direction.DESC, "timestamp");
		List<AuditRecommendations> auditRecommendations = auditRecommendationsRepository.findByRequestId(requestId, pageRequest).getContent();

		String executionResponseContainer = auditRecommendations.get(0).getRecommendationXML();
		System.out.println(executionResponseContainer);

		try {
			ruleExecutionContainer = marshaller.unmarshalFromXml(
					RuleExecutionContainer.class, executionResponseContainer);
		} catch (SimpleMarshallerException e) {
			e.printStackTrace();
		}

		List<RuleExecutionResponse> executionResponses = ruleExecutionContainer.getExecutionResponseList();
		return buildResponse(executionResponses, executionResponseContainer);
	}
	
	public RecommendationsDto getRecommendations() {
		RuleExecutionContainer ruleExecutionContainer = null;

		PageRequest pageRequest = new PageRequest(0, 1, Sort.Direction.DESC, "timestamp");
		//List<AuditRecommendations> auditRecommendations = auditRecommendationsRepository.findByRequestId(requestId, pageRequest).getContent();
		//Instead of gettting by requestId, get the latest by userid
		List<AuditRecommendations> auditRecommendations = auditRecommendationsRepository.findByUsername(usernameService.retrieveUsername(), pageRequest).getContent();

		String executionResponseContainer = auditRecommendations.get(0).getRecommendationXML();
		System.out.println(executionResponseContainer);

		try {
			ruleExecutionContainer = marshaller.unmarshalFromXml(
					RuleExecutionContainer.class, executionResponseContainer);
		} catch (SimpleMarshallerException e) {
			e.printStackTrace();
		}

		List<RuleExecutionResponse> executionResponses = ruleExecutionContainer.getExecutionResponseList();
		return buildResponse(executionResponses, executionResponseContainer);
	}

	private RecommendationsDto buildResponse(List<RuleExecutionResponse> executionResponses, String recommendationXml) {

		RecommendationsDto recommendationsDto = new RecommendationsDto();

		List<String> recommendations = new ArrayList<String>();
		List<String> services = new ArrayList<String>();
		List<String> treatments = new ArrayList<String>();
		String description = "";
		for (RuleExecutionResponse executionResponse : executionResponses) {
			description = executionResponse.getDescription();
			List<RecommendationItem> recommendationList = executionResponse.getRecommendationList();
			for (RecommendationItem recommendationItem : recommendationList) {
				recommendations.add(recommendationItem.getDescritption());
			}

			List<TreatmentItem> treatmentList = executionResponse.getTreatmentList();
			for (TreatmentItem treatmentItem : treatmentList) {
				treatments.add(treatmentItem.getDescritption());
			}

			List<ServiceItem> serviceList = executionResponse.getServiceList();
			for (ServiceItem serviceItem : serviceList) {
				services.add(serviceItem.getDescritption());
			}
		}

		recommendationsDto.setRecommendations(recommendations);
		recommendationsDto.setServices(services);
		recommendationsDto.setTreatments(treatments);
		recommendationsDto.setDescription(description);

		return recommendationsDto;

	}
	
}
