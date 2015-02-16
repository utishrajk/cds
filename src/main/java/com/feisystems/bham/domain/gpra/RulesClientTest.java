package com.feisystems.bham.domain.gpra;

import com.feisystems.bham.service.GpraDto;
import com.feisystems.bham.service.recommendation.RecommendationServiceImpl;

/**
 * The Class RuleExecutionWebServiceClient.
 */
public class RulesClientTest {

	/** The clinical facts. */
	private static String clinicalFacts = "<FactModel><membershipClass><latentClass>CLASS1</latentClass></membershipClass></FactModel>";

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{
		// RulesClientImpl impl = new RulesClientImpl();
		//
		// String recommendations =impl.getExecutionResponseList("CLASS2",
		// "ASI");
		//
		// System.out.println(recommendations);
		//
		// System.exit(0);

		// Testing - Begin

		GpraDto dto = new GpraDto();
		dto.setAge(31);
		dto.setAnxietyCount(10);
		dto.setEmotionalProblemsCode("5");
		dto.setEmotionalProblemsDisplayName("5");
		dto.setCodeineCount(0);
		dto.setCodeineRouteCode("1");
		dto.setCodeineRouteDisplayName("1");
		dto.setDarvonCount(1);
		dto.setDarvonRouteCode("1");
		dto.setDarvonRouteDisplayName("1");
		dto.setDemerolCount(1);
		dto.setDemerolRouteCode("1");
		dto.setDemerolRouteDisplayName("1");
		dto.setDepressionCount(1);
		dto.setDiluadidCount(1);
		dto.setDiluadidRouteCode("1");
		dto.setDiluadidRouteDisplayName("1");
		dto.setGenderCode("MA");
		dto.setGpra(true);
		dto.setHealthIndicatorCode("1");
		dto.setHealthIndicatorCodeDisplayName("1");
		dto.setHeroinDayCount(1);
		dto.setHeroinRouteCode("1");
		dto.setHeroinRouteDisplayName("1");
		dto.setMethadoneCount(1);
		dto.setMethadoneRouteCode("1");
		dto.setMethadoneRouteDisplayName("1");
		dto.setMorphineCount(1);
		dto.setMorphineRouteCode("1");
		dto.setMorphineRouteDisplayName("1");
		dto.setOtherDescription("1");
		dto.setOxycontinCount(1);
		dto.setOxycontinRouteCode("1");
		dto.setOxycontinRouteDisplayName("1");
		dto.setPercocetCount(1);
		dto.setPercocetRouteCode("1");
		dto.setPercocetRouteDisplayName("1");
		dto.setPsychMedicationCount(1);
		dto.setRequestId("abcdefgh");
		dto.setStaffId("staffId");
		dto.setTylenolCount(1);
		dto.setTylenolRouteCode("1");
		dto.setTylenolRouteDisplayName("1");
		dto.setViolentCount(1);

		RecommendationServiceImpl impl = new RecommendationServiceImpl();

		try {

			impl.saveGpraRecommendations(dto);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Testing - End

	}
}
