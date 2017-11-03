package com.examples.leshkov.sippo;

import com.examples.leshkov.sippo.simplex_method.Fraction;
import com.examples.leshkov.sippo.simplex_method.parser.SimplexTable;

public class App1 {

	public static void main(String[] args) {
		SimplexTable table = new SimplexTable(
			new Fraction[] {new Fraction(), new Fraction(8), new Fraction(7)},

			new Fraction[] {
				new Fraction(30), new Fraction(40), new Fraction(50), new Fraction(50)
			},

			new Fraction[][] {
				{new Fraction(1), new Fraction(-2)},
				{new Fraction(3), new Fraction(2)},
				{new Fraction(2), new Fraction(1)},
				{new Fraction(2), new Fraction(2)}
			}
		);

		System.out.println(table);
		System.out.println();

		table.transform(1, 0);

		System.out.println(table);

		/*
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
		*/
	}
}
