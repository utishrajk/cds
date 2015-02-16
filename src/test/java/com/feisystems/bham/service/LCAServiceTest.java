package com.feisystems.bham.service;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.feisystems.bham.service.LCAService;

@RunWith(MockitoJUnitRunner.class)
public class LCAServiceTest {

	@Test
	public void testCase1() {
		LCAService service = new LCAService();

		Integer[] inputs = new Integer[10];

		// overall_health_indicator
		inputs[1] = 1;

		// gpra_route_indicator
		inputs[2] = 0;

		// psych_any_indicator
		inputs[3] = 1;
		
		// violent_behavior_indicator
		inputs[4] = 0;

		// psych_med_indicator
		inputs[5] = 1;
		
		// age_covariate
		inputs[6] = 24;
		
		// gender_covariate
		inputs[7] = 1;
		
		// Rx_or_meth_no_heroin_covariate
		inputs[8] = 1;
		
		// heroin_only_covariate
		inputs[9] = 1;

		String probClass = service.calculate(inputs);

		assertEquals(probClass, "CLASS2");
	}

	@Test
	@Ignore
	public void testCase2() {
		LCAService service = new LCAService();

		Integer[] inputs = new Integer[10];

		inputs[1] = 0;
		inputs[2] = 0;
		inputs[3] = 1;
		inputs[4] = 0;
		inputs[5] = 0;

		inputs[6] = 23;
		inputs[7] = 1;
		inputs[8] = 1;
		inputs[9] = 1;

		String probClass = service.calculate(inputs);

		// 0.292367796689354
		// 0.647779593162822
		// 0.0327278311126406
		// 0.027124779035182
		// Probability Class : 2
		assertEquals(probClass, "CLASS2");
	}

}
