package com.feisystems.bham.service;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.springframework.stereotype.Component;

@Component
public class ASIService {

	static final Apfloat ONE = new Apfloat(1);

	static final Apfloat[] pi11 = { new Apfloat(0.366), new Apfloat(0.164), new Apfloat(0.469) };

	// pi21 =c(0.568)
	static final Apfloat pi21 = new Apfloat(0.568);

	// pi31 = c(0.182)
	static final Apfloat pi31 = new Apfloat(0.182);

	// pi41 = c(0.274, 0.159 ,0.265, 0.303 )
	static final Apfloat[] pi41 = { new Apfloat(0.274), new Apfloat(0.159), new Apfloat(0.265), new Apfloat(0.303) };

	// pi51 = c( 0.688)
	static final Apfloat pi51 = new Apfloat(0.688);

	// pi61 = c( 0.251)
	static final Apfloat pi61 = new Apfloat(0.251);

	// pi71 = c( 0.17)
	static final Apfloat pi71 = new Apfloat(0.17);

	// pi81 = c(0.544, 0.077 ,0.17, 0.208 )
	static final Apfloat[] pi81 = { new Apfloat(0.544), new Apfloat(0.077), new Apfloat(0.17), new Apfloat(0.208) };

	// pi91 = c( 0.539)
	static final Apfloat pi91 = new Apfloat(0.539);

	// pi101 = c( 0.349)
	static final Apfloat pi101 = new Apfloat(0.349);

	// pi111 = c( 0.971)
	static final Apfloat pi111 = new Apfloat(0.971);

	// pi12<-c(0.576, 0.19 , 0.234 )
	static final Apfloat[] pi12 = { new Apfloat(0.576), new Apfloat(0.19), new Apfloat(0.234) };

	// pi22 =c( 0.745)
	static final Apfloat pi22 = new Apfloat(0.745);

	// pi32 = c( 0.411)
	static final Apfloat pi32 = new Apfloat(0.411);

	// pi42 = c(0.116, 0.151 ,0.297, 0.435 )
	static final Apfloat[] pi42 = { new Apfloat(0.116), new Apfloat(0.151), new Apfloat(0.297), new Apfloat(0.435) };

	// pi52 = c( 0.579)
	static final Apfloat pi52 = new Apfloat(0.579);

	// pi62 = c( 0.291)
	static final Apfloat pi62 = new Apfloat(0.291);

	// pi72 = c( 0.333)
	static final Apfloat pi72 = new Apfloat(0.333);

	// pi82 = c(0.251, 0.093, 0.301, 0.356 )
	static final Apfloat[] pi82 = { new Apfloat(0.251), new Apfloat(0.093), new Apfloat(0.301), new Apfloat(0.356) };

	// pi92 = c( 0.234)
	static final Apfloat pi92 = new Apfloat(0.234);

	// pi102 = c( 0.84)
	static final Apfloat pi102 = new Apfloat(0.84);

	// pi112 = c( 0.965)
	static final Apfloat pi112 = new Apfloat(0.965);

	// pi13<-c(0.72, 0.151 , 0.128 )
	static final Apfloat[] pi13 = { new Apfloat(0.72), new Apfloat(0.151), new Apfloat(0.128) };

	// pi23 =c( 0.63)
	static final Apfloat pi23 = new Apfloat(0.63);

	// pi33 = c( 0.186)
	static final Apfloat pi33 = new Apfloat(0.186);

	// pi43 = c(0.5, 0.22 ,0.211, 0.07 )
	static final Apfloat[] pi43 = { new Apfloat(0.5), new Apfloat(0.22), new Apfloat(0.211), new Apfloat(0.07) };

	// pi53 = c( 0.22)
	static final Apfloat pi53 = new Apfloat(0.22);

	// pi63 = c( 0.018)
	static final Apfloat pi63 = new Apfloat(0.018);

	// pi73 = c( 0.029)
	static final Apfloat pi73 = new Apfloat(0.029);

	// pi83 = c(0.434, 0.104, 0.258,0.204 )
	static final Apfloat[] pi83 = { new Apfloat(0.434), new Apfloat(0.104), new Apfloat(0.258), new Apfloat(0.204) };

	// pi93 = c( 0.049)
	static final Apfloat pi93 = new Apfloat(0.049);

	// pi103 = c( 0.599)
	static final Apfloat pi103 = new Apfloat(0.599);

	// pi113 = c( 0.49)
	static final Apfloat pi113 = new Apfloat(0.49);

	static final Apfloat alpha1 = new Apfloat(-8.006);
	static final Apfloat alpha2 = new Apfloat(0.98);

	static final Apfloat gamma11 = new Apfloat(0.102);
	static final Apfloat gamma21 = new Apfloat(1.999);
	static final Apfloat gamma31 = new Apfloat(0.159);
	static final Apfloat gamma41 = new Apfloat(0.944);
	static final Apfloat gamma51 = new Apfloat(3.027);

	static final Apfloat gamma12 = new Apfloat(-0.027);
	static final Apfloat gamma22 = new Apfloat(0.584);
	static final Apfloat gamma32 = new Apfloat(0.414);
	static final Apfloat gamma42 = new Apfloat(-0.968);
	static final Apfloat gamma52 = new Apfloat(-1.062);

	public String calculate(final Integer[] inputs) {

		// medical_score (1, 2, 3)
		int x1 = inputs[0];

		// experienced employment problems (0, 1)
		int x2 = inputs[1];

		// alcohol intoxication (0, 1)
		int x3 = inputs[2];

		// family_score (1, 2, 3, 4)
		int x4 = inputs[3];

		// abuse (0, 1)
		int x5 = inputs[4];

		// suicide (0, 1)
		int x6 = inputs[5];

		// violent (0, 1)
		int x7 = inputs[6];

		// legal_score(1, 2, 3, 4)
		int x8 = inputs[7];

		// meds for psychological problems
		int x9 = inputs[8];

		// route (0, 1)
		int x10 = inputs[9];

		// depression (0, 1)
		int x11 = inputs[10];

		// age
		int w1 = inputs[11];

		// gender (0, 1)
		int w2 = inputs[12];

		// priorepiso( 0 - 5)
		int w3 = inputs[13];

		// OPICOVD1
		int w4 = inputs[14];

		// OPICOVD2
		int w5 = inputs[15];

		Apfloat[] pyc = probq(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11);

		Apfloat[] eta = prom(w1, w2, w3, w4, w5);

		Apfloat den = eta[0].multiply(pyc[0]).add(eta[1].multiply(pyc[1])).add(eta[2].multiply(pyc[2]));

		Apfloat[] prob_LC = new Apfloat[4];

		prob_LC[1] = eta[0].multiply(pyc[0]).divide(den);
		prob_LC[2] = eta[1].multiply(pyc[1]).divide(den);
		prob_LC[3] = eta[2].multiply(pyc[2]).divide(den);

		Apfloat max = prob_LC[1];

		int probClass = 1;

		for (int i = 2; i < prob_LC.length; i++) {
			if (prob_LC[i].compareTo(max) > 0) {
				max = prob_LC[i];
				probClass = i;
			}
		}

		return "CLASS" + probClass;
	}

	// step 1
	private Apfloat[] probq(int x1, int x2, int x3, int x4, int x5, int x6, int x7, int x8, int x9, int x10, int x11) {
		Apfloat prod11 = pi11[x1 - 1];

		Apfloat prod12 = pi12[x1 - 1];

		Apfloat prod13 = pi13[x1 - 1];

		Apfloat x2f = new Apfloat(x2);
		Apfloat prod21 = calculateProd(pi21, x2f);
		Apfloat prod22 = calculateProd(pi22, x2f);
		Apfloat prod23 = calculateProd(pi23, x2f);

		Apfloat x3f = new Apfloat(x3);
		Apfloat prod31 = calculateProd(pi31, x3f);
		Apfloat prod32 = calculateProd(pi32, x3f);
		Apfloat prod33 = calculateProd(pi33, x3f);

		Apfloat prod41 = pi41[x4 - 1];
		Apfloat prod42 = pi42[x4 - 1];
		Apfloat prod43 = pi43[x4 - 1];

		Apfloat x5f = new Apfloat(x5);
		Apfloat prod51 = calculateProd(pi51, x5f);
		Apfloat prod52 = calculateProd(pi52, x5f);
		Apfloat prod53 = calculateProd(pi53, x5f);

		Apfloat x6f = new Apfloat(x6);
		Apfloat prod61 = calculateProd(pi61, x6f);
		Apfloat prod62 = calculateProd(pi62, x6f);
		Apfloat prod63 = calculateProd(pi63, x6f);

		Apfloat x7f = new Apfloat(x7);
		Apfloat prod71 = calculateProd(pi71, x7f);
		Apfloat prod72 = calculateProd(pi72, x7f);
		Apfloat prod73 = calculateProd(pi73, x7f);

		Apfloat prod81 = pi81[x8 - 1];
		Apfloat prod82 = pi82[x8 - 1];
		Apfloat prod83 = pi83[x8 - 1];

		Apfloat x9f = new Apfloat(x9);
		Apfloat prod91 = calculateProd(pi91, x9f);
		Apfloat prod92 = calculateProd(pi92, x9f);
		Apfloat prod93 = calculateProd(pi93, x9f);

		Apfloat x10f = new Apfloat(x10);
		Apfloat prod101 = calculateProd(pi101, x10f);
		Apfloat prod102 = calculateProd(pi102, x10f);
		Apfloat prod103 = calculateProd(pi103, x10f);

		Apfloat x11f = new Apfloat(x11);
		Apfloat prod111 = calculateProd(pi111, x11f);
		Apfloat prod112 = calculateProd(pi112, x11f);
		Apfloat prod113 = calculateProd(pi113, x11f);

		Apfloat ret1 = prod11.multiply(prod21).multiply(prod31).multiply(prod41).multiply(prod51).multiply(prod61).multiply(prod71).multiply(prod81)
				.multiply(prod91).multiply(prod101).multiply(prod111);

		Apfloat ret2 = prod12.multiply(prod22).multiply(prod32).multiply(prod42).multiply(prod52).multiply(prod62).multiply(prod72).multiply(prod82)
				.multiply(prod92).multiply(prod102).multiply(prod112);

		Apfloat ret3 = prod13.multiply(prod23).multiply(prod33).multiply(prod43).multiply(prod53).multiply(prod63).multiply(prod73).multiply(prod83)
				.multiply(prod93).multiply(prod103).multiply(prod113);

		return new Apfloat[] { ret1, ret2, ret3 };
	}

	private Apfloat calculateProd(Apfloat pixy, Apfloat xi) {
		return ApfloatMath.pow(pixy, xi).multiply(ApfloatMath.pow(ONE.subtract(pixy), ONE.subtract(xi)));
	}

	// step 2
	private Apfloat[] prom(int w1, int w2, int w3, int w4, int w5) {

		Apfloat w1f = new Apfloat(w1);
		Apfloat w2f = new Apfloat(w2);
		Apfloat w3f = new Apfloat(w3);
		Apfloat w4f = new Apfloat(w4);
		Apfloat w5f = new Apfloat(w5);

		Apfloat f1 = ApfloatMath.exp(alpha1.add(gamma11.multiply(w1f)).add(gamma21.multiply(w2f)).add(gamma31.multiply(w3f))
				.add(gamma41.multiply(w4f)).add(gamma51.multiply(w5f)));

		Apfloat f2 = ApfloatMath.exp(alpha2.add(gamma12.multiply(w1f)).add(gamma22.multiply(w2f)).add(gamma32.multiply(w3f))
				.add(gamma42.multiply(w4f)).add(gamma52.multiply(w5f)));

		Apfloat eta1 = f1.divide(ONE.add(f1).add(f2));
		Apfloat eta2 = f2.divide(ONE.add(f1).add(f2));
		Apfloat eta3 = ONE.divide(ONE.add(f1).add(f2));

		return new Apfloat[] { eta1, eta2, eta3 };
	}

}
