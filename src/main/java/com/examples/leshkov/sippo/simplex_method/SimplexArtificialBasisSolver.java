/*
package com.examples.leshkov.sippo.simplex_method;

import com.examples.leshkov.sippo.simplex_method.table.SimplexTable;

import java.util.ArrayList;
import java.util.BitSet;

public class SimplexArtificialBasisSolver {

	public static SimplexTable solve(final SimplexTable table) {
		SimplexTable result = buildTableWithBasis(table);

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
			} else {
				return null;
			}
		}

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


	private static SimplexTable buildTableWithBasis(final SimplexTable table) {
		ArrayList<Integer> indices = new ArrayList<>();
		SimplexTable auxiliary = solveAuxiliary(table);

		if (auxiliary == null) {
            return null;
        }

		for (int i = 0; i < auxiliary.getFunction().length; i++) {
			if (auxiliary.getFunction()[i].isZero()) {
				indices.add(i);
			}
		}

		Fraction[] function = buildFunction(table, auxiliary, indices);
		Fraction[] constants = buildConstants(auxiliary);
		Fraction[][] limits = buildLimits(auxiliary, indices);

		return new SimplexTable(function, constants, limits);
	}

	private static Fraction[] buildFunction(
		final SimplexTable table,
		final SimplexTable auxiliary,
		final ArrayList<Integer> indices
	) {

		ArrayList<Integer> basis = new ArrayList<>();
		for (int i = 1; i < auxiliary.getFunction().length; i++) {
			if (!indices.contains(i)) {
				basis.add(i);
			}
		}


		Fraction[] function = new Fraction[indices.size()];
		for (int i = 0; i < function.length; i++) {
			function[i] = new Fraction(table.getFunction()[indices.get(i)].getNegative());
		}

		for (int i = 0; i < auxiliary.getLimits().length; i++) {
			ArrayList<Fraction> adder = new ArrayList<>();
			adder.add(auxiliary.getConstants()[i]);

			for (int j = 1; j < auxiliary.getFunction().length; j++) {
				if (!basis.contains(j)) {
					adder.add(new Fraction(auxiliary.getLimits()[i][j - 1].getNegative()));
				}
			}

			function[0].
			increaseBy(adder.get(0).multiply(table.getFunction()[i + 1].getNegative()));
			for (int j = 1; j < function.length; j++) {
				function[j].increaseBy(
					adder.get(j).multiply(table.getFunction()[i + 1].getNegative())
				);
			}
		}

		return function;
	}

	private static Fraction[] buildConstants(final SimplexTable auxiliary) {
		Fraction[] constants = new Fraction[auxiliary.getConstants().length];

		for (int i = 0; i < constants.length; i++) {
			constants[i] = new Fraction(auxiliary.getConstants()[i]);
		}

		return constants;
	}

	private static Fraction[][] buildLimits(
		final SimplexTable auxiliary,
		final ArrayList<Integer> indices
	) {
		Fraction[][] limits = new Fraction[auxiliary.getLimits().length][indices.size() - 1];

		for (int i = 0; i < limits.length; i++) {
			for (int j = 0; j < limits[i].length; j++) {
				limits[i][j] = auxiliary.getLimits()[i][indices.get(j + 1) - 1];
			}
		}

		return limits;
	}


	public static SimplexTable solveAuxiliary(final SimplexTable table) {
        if (table == null) {
            return null;
		}

		SimplexTable auxiliary = table.getAuxiliaryTable();

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

        Fraction[] constants = table.getConstants();
        Fraction[][] limits = table.getLimits();

		int result = 0;
		while (!flags.get(result) && limits[result][givenCol].isPositive()) {
			result++;
		}

		for (int i = 0; i < constants.length; i++) {
			Fraction a = constants[i];
			Fraction b = limits[i][givenCol];

			if (
				flags.get(i) &&
				b.isPositive() &&

				a.divide(b).abs().lessThan(
					constants[result].divide(limits[result][givenCol]).abs()
				)
			) {
				result = i;
			}
        }

		flags.flip(result);

		return result;
	}
}
*/