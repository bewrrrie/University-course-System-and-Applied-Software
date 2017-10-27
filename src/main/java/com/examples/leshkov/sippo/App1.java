package com.examples.leshkov.sippo;

import com.examples.leshkov.sippo.simplex_method.SimplexArtificialBasisSolver;

public class App1 {

	public static void main(String[] args) {
		double[][] eq = new double[][] {
			new double[] {1, 1, 1, 0, 0, 0, 0, 0, 0},
			new double[] {0, 0, 0, 1, 1, 1, 0, 0, 0},
			new double[] {0, 0, 0, 0, 0, 0, 1, 1, 1},
			new double[] {1, 0, 0, 1, 0, 0, 1, 0, 0},
			new double[] {0, 1, 0, 0, 1, 0, 0, 1, 0},
			new double[] {0, 0, 1, 0, 0, 1, 0, 0, 1}
		};

		double[] b = new double[] {0, 10, 20, 30, 15, 20, 25};

		double[] f = new double[] {5, 3, 1, 3, 2, 4, 4, 1, 2};

		double[] result = SimplexArtificialBasisSolver.solve(eq, f, b);

		double fStar = 0;
		for (int i = 0; i < f.length; i++) {
			fStar += f[i] * result[i];
		}

		System.out.println(fStar);
	}
}
