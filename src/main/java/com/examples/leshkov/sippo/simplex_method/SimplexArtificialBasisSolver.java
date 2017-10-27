package com.examples.leshkov.sippo.simplex_method;

import java.util.Arrays;

public class SimplexArtificialBasisSolver {

	public static double[] solve(
		final double[][] eq,
		final double[] f,
		final double[] b
	) {

		if (b.length != eq.length + 1) {
			throw new IllegalArgumentException(
				"b vector must have length = n. Where (n*k) is equations coeff. matrix size."
			);
		}

		if (f.length != eq[0].length) {
			throw new IllegalArgumentException(
				"f vector must have length = k.  Where (n*k) is equations coeff. matrix size."
			);
		}

		for (int i = 1; i < eq.length; i++) {
			if (eq[i].length != eq[i - 1].length) {
				throw new IllegalArgumentException(
					"All rows of matrix eq must have similar lengths."
				);
			}
		}


		final double[][] A = Arrays.copyOf(eq, eq.length - 1);
		final double[] F = Arrays.copyOf(f, f.length - 1);
		final double[] B = Arrays.copyOf(b, b.length - 1);




		return new double[] {};
	}

	private static void addArtificialBasis(
		final double[][] eq,
		final double[] f,
		final double[] b
	) {
		transformArtBasis(eq, f, b);
	}

	private static void transformArtBasis(
		final double[][] eq,
		final double[] f,
		final double[] b
	) {

		b[0] = 0;

		for (int j = 1; j < b.length; j++) {
			b[0] += b[j];
		}

		for (int j = 0; j < f.length; j++) {
			f[j] = 0;

			for (double[] anEq : eq) {
				f[j] += anEq[j];
			}
		}
	}
}
