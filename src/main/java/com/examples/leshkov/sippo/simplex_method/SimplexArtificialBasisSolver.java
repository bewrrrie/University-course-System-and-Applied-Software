package com.examples.leshkov.sippo.simplex_method;

import com.examples.leshkov.sippo.simplex_method.table.SimplexTable;

import static com.examples.leshkov.sippo.simplex_method.Fraction.*;

public class SimplexArtificialBasisSolver {

	public static SimplexTable solveAuxiliary(final SimplexTable table) {
		if (table == null) {
			return null;
		}

		SimplexTable auxiliary = table.getAuxiliaryTable();

		while (!auxiliary.isOptimal() && !auxiliary.getFunction()[0].equals(ZERO)) {
			boolean solvable = false;
			for (int i = 1; i < table.getFunction().length; i++) {
				if (table.getFunction()[i].isPositive()) {
					solvable = true;
					break;
				}
			}

			if (solvable) {
				int j = findCol(auxiliary);

				if (j == -1) {
					return null;
				}

				int i = findRow(auxiliary, j);

				if (i == -1) {
					return null;
				}

				System.out.println("(" + i + "," + j + ")");
				auxiliary.transform(i, j);

				System.out.println(auxiliary + "\n");

				System.exit(0);
			} else {
				return null;
			}
		}

		return auxiliary;
	}


	private static int findCol(final SimplexTable table) {
		for (int i = 1; i < table.getFunction().length; i++) {
			if (table.getFunction()[i].isNegative()) {
				return i - 1;
			}
		}

		return -1;
	}

	private static int findRow(final SimplexTable table, final int col) {
		int result = 0;

		Fraction[] constants = table.getConstants();
		Fraction[][] limits = table.getLimits();

		for (int i = 0; i < constants.length; i++) {
			if (
				constants[i].isPositive() && limits[i][col].isPositive() &&
				( constants[i].divide(limits[i][col]) ).greaterThan(
					constants[result].divide(limits[result][col])
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
}
