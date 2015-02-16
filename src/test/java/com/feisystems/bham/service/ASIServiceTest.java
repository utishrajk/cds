package com.feisystems.bham.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ASIServiceTest {

	@Test
	public void testCase1() {
		ASIService service = new ASIService();

		Integer[] inputs = new Integer[16];

		// x1 (medical score)
		inputs[0] = 3;

		// x2 (employment)
		inputs[1] = 0;

		// x3 (alcohol)
		inputs[2] = 0;

		// x4 (family score)
		inputs[3] = 1;

		// x5 (abuse)
		inputs[4] = 0;

		// x6 (suicide)
		inputs[5] = 0;

		// x7 (violent)
		inputs[6] = 0;

		// x8 (legal score)
		inputs[7] = 4;

		// x9 (meds)
		inputs[8] = 0;

		// x10 (route)
		inputs[9] = 0;

		// x11 (depression)
		inputs[10] = 0;

		// w1 (age)
		inputs[11] = 35;

		// w2 (gender)
		inputs[12] = 1;

		// w3 (prior episodes)
		inputs[13] = 5;

		// w4 (opicovd1)
		inputs[14] = 0;

		// w5 (opicovd2)
		inputs[15] = 1;

		String probClass = service.calculate(inputs);

		assertEquals(probClass, "CLASS3");
	}

}
