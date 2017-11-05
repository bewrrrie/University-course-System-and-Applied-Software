package com.examples.leshkov.sippo.simplex_method.parser;

import com.examples.leshkov.sippo.simplex_method.Fraction;

public class SimplexTable {
	private Fraction[] function, basis;
	private Fraction[][] limits;


	public SimplexTable(
		final Fraction[] function,
		final Fraction[] basis,
		final Fraction[][] limits
	) {
		for (int i = 1; i < limits.length; i++) {
			if (limits[i].length != limits[0].length) {
				throw new IllegalArgumentException(
					"All rows in limits matrix must have the same length!"
				);
			}
		}

		if (function.length != limits[0].length + 1) {
			throw new IllegalArgumentException(
				"Function coefficients vector must have the same length as limits matrix + 1!"
			);
		}

		if (basis.length != limits.length) {
			throw new IllegalArgumentException(
				"Basis variables vector must have the same length as limits matrix height!"
			);
		}

		this.function = function;
		for (int i = 1; i < function.length; i++) {
			this.function[i].invertSign();
		}

		this.basis = basis;
		this.limits = limits;
	}


	public void transform(
		final int mainElementI,
		final int mainElementJ
	) {
		Fraction[] newFunction = transformFunction(mainElementI, mainElementJ);
		Fraction[] newBasis = transformBasis(mainElementI, mainElementJ);
		Fraction[][] newLimits = transformLimits(mainElementI, mainElementJ);


		function = newFunction;
		basis = newBasis;
		limits = newLimits;
	}


	private Fraction[] transformFunction(
		final int mainElementI,
		final int mainElementJ
	) {
		Fraction[] newFunction = new Fraction[function.length];

		Fraction main = limits[mainElementI][mainElementJ];
		Fraction minusMain = new Fraction(main);
		minusMain.invertSign();

		// Transform free coefficient:
		Fraction tmp = new Fraction(function[mainElementJ + 1]);
		tmp.multiplyBy(basis[mainElementI]);
		tmp.divideBy(main);

		newFunction[0] = function[0].subtract(tmp);

		// Transform other.
		for (int j = 1; j < function.length; j++) {
			if (j - 1 == mainElementJ) {
				newFunction[j] = function[j].divide(minusMain);
			} else {
				tmp = new Fraction(function[mainElementJ + 1]);
				tmp.multiplyBy(limits[mainElementI][j - 1]);
				tmp.divideBy(main);

				newFunction[j] = function[j].subtract(tmp);
			}
		}

		return newFunction;
	}


	private Fraction[] transformBasis(
		final int mainElementI,
		final int mainElementJ
	) {
		Fraction[] newBasis = new Fraction[limits.length];
		Fraction main = limits[mainElementI][mainElementJ];

		for (int i = 0; i < limits.length; i++) {
			if (i == mainElementI) {
				newBasis[i] = basis[i].divide(main);
			} else {
				Fraction tmp = new Fraction(basis[mainElementI]);
				tmp.multiplyBy(limits[i][mainElementJ]);
				tmp.divideBy(main);

				newBasis[i] = basis[i].subtract(tmp);
			}
		}

		return newBasis;
	}


	private Fraction[][] transformLimits(
	final int mainElementI,
	final int mainElementJ
	) {
		Fraction[][] newLimits = new Fraction[limits.length][limits[0].length];

		Fraction main = limits[mainElementI][mainElementJ];
		Fraction minusMain = new Fraction(main);
		minusMain.invertSign();

		for (int i = 0; i < limits.length; i++) {
			for (int j = 0; j < limits[i].length; j++) {

				if (i != mainElementI && j != mainElementJ) {
					Fraction tmp = new Fraction(limits[i][mainElementJ]);
					tmp.multiplyBy(limits[mainElementI][j]);
					tmp.divideBy(main);

					newLimits[i][j] = limits[i][j].subtract(tmp);

				} else if (i == mainElementI && j != mainElementJ) {
					newLimits[i][j] = limits[i][j].divide(main);

				} else if (i != mainElementI) {
					newLimits[i][j] = limits[i][j].divide(minusMain);
				}
			}
		}

		main.invert();
		newLimits[mainElementI][mainElementJ] = main;

		return newLimits;
	}


	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(function[0]);
		for (int i = 1; i < function.length; i++) {
			result.append(" ");
			result.append(function[i]);
		}

		for (int i = 0; i < limits.length; i++) {
			result.append('\n');
			result.append(basis[i]);

			for (int j = 0; j < limits[0].length; j++) {
				result.append(" ");
				result.append(limits[i][j]);
			}
		}

		return result.toString();
	}
}
