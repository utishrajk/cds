package com.feisystems.bham.service;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.springframework.stereotype.Component;

@Component
public class LCAService {

	public String calculate(final Integer[] inputs) {
		Apfloat[] x = new Apfloat[6];
		Apfloat[] w = new Apfloat[5];

		x[1] = new Apfloat(inputs[1]);
		x[2] = new Apfloat(inputs[2]);
		x[3] = new Apfloat(inputs[3]);
		x[4] = new Apfloat(inputs[4]);
		x[5] = new Apfloat(inputs[5]);

		w[1] = new Apfloat(inputs[6]);
		w[2] = new Apfloat(inputs[7]);
		w[3] = new Apfloat(inputs[8]);
		w[4] = new Apfloat(inputs[9]);

		Apfloat[][] piArray = new Apfloat[6][5];

		piArray[1][1] = new Apfloat(.5858962089440934);
		piArray[2][1] = new Apfloat(.1385396166681793);
		piArray[3][1] = new Apfloat(.9766028487916568);
		piArray[4][1] = new Apfloat(.3398506808891903);
		piArray[5][1] = new Apfloat(.4688894624415912);
		piArray[1][2] = new Apfloat(.2994820607417876);
		piArray[2][2] = new Apfloat(.1503355790966336);
		piArray[3][2] = new Apfloat(.5236840631774571);
		piArray[4][2] = new Apfloat(.0489536497338276);
		piArray[5][2] = new Apfloat(.0283645162598447);
		piArray[1][3] = new Apfloat(.5525264045666120);
		piArray[2][3] = new Apfloat(.8901026207780050);
		piArray[3][3] = new Apfloat(.9687511435724040);
		piArray[4][3] = new Apfloat(.2073883105919727);
		piArray[5][3] = new Apfloat(.4265424528948224);
		piArray[1][4] = new Apfloat(.3622075770696210);
		piArray[2][4] = new Apfloat(.9319458514698140);
		piArray[3][4] = new Apfloat(.2825211934306242);
		piArray[4][4] = new Apfloat(.0185960489614365);
		piArray[5][4] = new Apfloat(.0322436821706636);

		Apfloat[] alpha = new Apfloat[4];

		alpha[1] = new Apfloat(-11.261712153130000);
		alpha[2] = new Apfloat(-8.554395185878001);
		alpha[3] = new Apfloat(1.356895420029000);

		Apfloat[][] gamma = new Apfloat[5][4];

		gamma[1][1] = new Apfloat(0.098205865005800);
		gamma[2][1] = new Apfloat(-1.138536786113000);
		gamma[3][1] = new Apfloat(0.094899486393010);
		gamma[4][1] = new Apfloat(10.073952078190000);

		gamma[1][2] = new Apfloat(0.053111949222880);
		gamma[2][2] = new Apfloat(-0.019051290860040);
		gamma[3][2] = new Apfloat(-1.166632723085000);
		gamma[4][2] = new Apfloat(8.483548273287999);

		gamma[1][3] = new Apfloat(-0.003151275135590);
		gamma[2][3] = new Apfloat(-1.058111770093000);
		gamma[3][3] = new Apfloat(-0.410235018921500);
		gamma[4][3] = new Apfloat(-0.248446192532500);

		final Apfloat ONE = new Apfloat(1);

		Apfloat prod[][] = new Apfloat[6][5];

		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 4; j++) {
				prod[i][j] = (ApfloatMath.pow(piArray[i][j], x[i])).multiply(ApfloatMath.pow(ONE.subtract(piArray[i][j]), ONE.subtract(x[i])));

			}
		}

		Apfloat f[] = new Apfloat[4];

		for (int j = 1; j <= 3; j++) {
			f[j] = ApfloatMath.exp(alpha[j].add(gamma[1][j].multiply(w[1])).add(gamma[2][j].multiply(w[2])).add(gamma[3][j].multiply(w[3]))
					.add(gamma[4][j].multiply(w[4])));

		}

		Apfloat eta[] = new Apfloat[5];

		eta[1] = f[1].divide(ONE.add(f[1]).add(f[2]).add(f[3]));

		eta[2] = f[2].divide(ONE.add(f[1]).add(f[2]).add(f[3]));

		eta[3] = f[3].divide(ONE.add(f[1]).add(f[2]).add(f[3]));

		eta[4] = ONE.divide(ONE.add(f[1]).add(f[2]).add(f[3]));

		Apfloat[] prob_LC = new Apfloat[5];

		for (int j = 1; j < prob_LC.length; j++) {
			Apfloat d1 = eta[1].multiply(prod[1][1]).multiply(prod[2][1]).multiply(prod[3][1]).multiply(prod[4][1]).multiply(prod[5][1]);
			Apfloat d2 = eta[2].multiply(prod[1][2]).multiply(prod[2][2]).multiply(prod[3][2]).multiply(prod[4][2]).multiply(prod[5][2]);
			Apfloat d3 = eta[3].multiply(prod[1][3]).multiply(prod[2][3]).multiply(prod[3][3]).multiply(prod[4][3]).multiply(prod[5][3]);
			Apfloat d4 = eta[4].multiply(prod[1][4]).multiply(prod[2][4]).multiply(prod[3][4]).multiply(prod[4][4]).multiply(prod[5][4]);

			prob_LC[j] = (eta[j].multiply(prod[1][j]).multiply(prod[2][j]).multiply(prod[3][j]).multiply(prod[4][j]).multiply(prod[5][j])).divide(d1
					.add(d2).add(d3).add(d4));

			 System.out.println(String.format("1: %#s", prob_LC[j]));
		}

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
}
