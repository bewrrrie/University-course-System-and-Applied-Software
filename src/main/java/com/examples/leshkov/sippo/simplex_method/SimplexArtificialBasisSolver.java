package com.examples.leshkov.sippo.simplex_method;

import com.examples.leshkov.sippo.simplex_method.table.SimplexTable;

import java.util.ArrayList;
import java.util.BitSet;

public class SimplexArtificialBasisSolver {

	public static SimplexTable solve(final SimplexTable table) {
		System.out.println("Auxiliary:");
		SimplexTable result = buildTableWithBasis(table, solveAuxiliary(table));

		System.out.println("\n" + result);

		/* TODO
		while (!result.isOptimal()) {
			boolean solvable = false;

			for (int i = 1; i < table.getFunction().length; i++) {
				if (table.getFunction()[i].isPositive()) {
					solvable = true;
					break;
				}
			}

			if (solvable) {
				int j = findCol(result);

				if (j == -1) {
					return null;
				}

				int i = findRow(result, j);

				if (i == -1) {
					return null;
				}

				result.transform(i, j);
				System.out.println("\n" + result);

			} else {
				return null;
			}
		}
		*/

		return result;
	}

	private static int findCol(final SimplexTable table) {
		for (int i = 1; i < table.getFunction().length; i++) {
			if (table.getFunction()[i].isNegative()) {
				return i - 1;
			}
		}

		return -1;
	}

	private static int findRow(
		final SimplexTable table,
		final int givenCol
	) {
		int result = 0;

		Fraction[] constants = table.getConstants();
		Fraction[][] limits = table.getLimits();

		for (int i = 0; i < constants.length; i++) {
			Fraction a = constants[i];
			Fraction b = limits[i][givenCol];

			if (
				a.isPositive() &&
				b.isPositive() &&

				a.divide(b).lessThan(
					constants[result].divide(limits[result][givenCol])
				)
			) {
				result = i;
			}
		}

		if (table.getConstants()[result].isNegative()) {
			return -1;
		}

		return result;
	}


	private static SimplexTable buildTableWithBasis(
		final SimplexTable table,
		final SimplexTable auxiliary
	) {
		ArrayList<Integer> indices = new ArrayList<>();

		for (int i = 0; i < auxiliary.getFunction().length; i++) {
			if (auxiliary.getFunction()[i].isZero()) {
				indices.add(i);
			}
		}

		Fraction[] function = new Fraction[indices.size()];
		Fraction[] constants = new Fraction[auxiliary.getConstants().length];
		Fraction[][] limits = new Fraction[auxiliary.getLimits().length][indices.size() - 1];

		for (int i = 0; i < indices.size(); i++) {
			function[i] = new Fraction(table.getFunction()[i]);
		}

		for (int i = 0; i < constants.length; i++) {
			constants[i] = new Fraction(auxiliary.getConstants()[i]);
		}

		for (int i = 0; i < limits.length; i++) {
			for (int j = 0; j < indices.size(); j++) {
				//TODO
			}
		}


		return new SimplexTable(function, constants, limits);
	}


	public static SimplexTable solveAuxiliary(final SimplexTable table) {
		if (table == null) {
			return null;
		}

		SimplexTable auxiliary = table.getAuxiliaryTable();
		System.out.println(auxiliary);

		BitSet canBeSelectedCol = new BitSet(auxiliary.getFunction().length);
		canBeSelectedCol.set(0, auxiliary.getFunction().length, true);

		BitSet canBeSelectedRow = new BitSet(auxiliary.getLimits().length);
		canBeSelectedRow.set(0, auxiliary.getLimits().length, true);


		while (!auxiliary.isOptimal() && !auxiliary.getFunction()[0].isZero()) {
			boolean solvable = false;

			for (int i = 1; i < auxiliary.getFunction().length; i++) {
				if (auxiliary.getFunction()[i].isPositive()) {
					solvable = true;
					break;
				}
			}

			if (solvable) {
				int j = findCol(auxiliary, canBeSelectedCol);

				if (j == -1) {
					return null;
				}

				int i = findRow(auxiliary, j, canBeSelectedRow);

				if (i == -1) {
					return null;
				}

				auxiliary.transform(i, j);
				System.out.println("\n" + auxiliary);

			} else {
				return null;
			}
		}

		if (!auxiliary.isOptimal()) {
			return null;
		}

		return auxiliary;
	}


	private static int findCol(
		final SimplexTable table,
		final BitSet flags
	) {
		for (int i = 1; i < table.getFunction().length; i++) {
			if (
				flags.get(i) &&
				table.getFunction()[i].isNegative()
			) {
				flags.flip(i);
				return i - 1;
			}
		}

		return -1;
	}

	private static int findRow(
		final SimplexTable table,
		final int givenCol,
		final BitSet flags
	) {
		int result = 0;
		while (!flags.get(result)) {
			result++;
		}

		Fraction[] constants = table.getConstants();
		Fraction[][] limits = table.getLimits();

		for (int i = 0; i < constants.length; i++) {
			Fraction a = constants[i];
			Fraction b = limits[i][givenCol];

			if (
				flags.get(i) &&
				a.isPositive() &&
				b.isPositive() &&

				a.divide(b).lessThan(
					constants[result].divide(limits[result][givenCol])
				)
			) {
				result = i;
			}
		}

		if (table.getConstants()[result].isNegative()) {
			return -1;
		}

		flags.flip(result);

		return result;
	}
}
