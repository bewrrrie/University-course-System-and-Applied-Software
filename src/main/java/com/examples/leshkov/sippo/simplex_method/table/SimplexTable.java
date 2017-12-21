package com.examples.leshkov.sippo.simplex_method.table;

import com.examples.leshkov.sippo.simplex_method.Fraction;

import java.util.Arrays;
import java.util.StringJoiner;

public class SimplexTable {
	private FunctionLine function;
	private LimitLine[] limits;

	private boolean isAuxiliary = false;

	public SimplexTable(
		final FunctionLine function,
		final LimitLine[] limits
	) {
	    for (int i = 1; i < limits.length; i++) {
	        if (limits[i].getLength() != limits[i - 1].getLength()) {
	            throw new IllegalArgumentException("All limit rows must have the same size!");
            }
        }

		if (function.getLength() != limits[0].getLength()) {
			throw new IllegalArgumentException("Linear function must have the same size as all limits!");
		}

		this.function = function;
		this.limits = limits;
	}

	public FunctionLine getFunction() {
		return function;
	}

	public LimitLine[] getLimits() {
	    return limits;
    }

	public LimitLine getLimit(final int i) {
		return limits[i];
	}


	public void transform(
		final int mainElementI,
		final int mainElementJ
	) {
		if (
			mainElementI <= 0 || mainElementJ <= 0 ||
			mainElementI >= limits.length || mainElementJ >= function.getLength()
		) {
			throw new IllegalArgumentException("Indices is out of bounds!");
		}

	    FunctionLine newFunction = transformFunction(mainElementI, mainElementJ);
	    LimitLine[] newLimits = transformLimits(mainElementI, mainElementJ);

	    function = newFunction;
	    limits = newLimits;
	}


	private FunctionLine transformFunction(
		final int mainElementI,
		final int mainElementJ
	) {
		FunctionLine result = new FunctionLine(function);

		Fraction main = limits[mainElementI - 1].getCoefficient(mainElementJ);
		Fraction minusMain = new Fraction(main);
		minusMain.invertSign();

		for (int j = 0; j < function.getLength(); j++) {
			if (j == mainElementJ) {
				result.getCoefficient(j).divideBy(minusMain);
			} else {
				Fraction subtrahend = new Fraction(
					limits[mainElementI - 1].getCoefficient(j)
					.multiply(function.getCoefficient(mainElementJ))
					.divide(main)
				);

				result.getCoefficient(j).decreaseBy(subtrahend);
			}
		}

		return result;
	}


	private LimitLine[] transformLimits(
		final int mainElementI,
		final int mainElementJ
	) {
		LimitLine[] result = new LimitLine[limits.length];
		for (int i = 0; i < limits.length; i++) {
			result[i] = new LimitLine(limits[i]);
		}

		Fraction main = limits[mainElementI - 1].getCoefficient(mainElementJ);
		Fraction minusMain = new Fraction(main);
		minusMain.invertSign();

		for (int i = 0; i < limits.length; i++) {
			if (i == mainElementI - 1) {
				for (int j = 0; j < limits[i].getLength(); j++) {
					if (j != mainElementJ) {
						result[i].getCoefficient(j).divideBy(main);
					}
				}
			} else {
				for (int j = 0; j < limits[i].getLength(); j++) {
					if (j != mainElementJ) {
						Fraction subtrahend = new Fraction(
							limits[mainElementI - 1].getCoefficient(j)
							.multiply(limits[i].getCoefficient(mainElementJ))
							.divide(main)
						);

						result[i].getCoefficient(j).decreaseBy(subtrahend);
					} else {
						result[i].getCoefficient(j).divideBy(minusMain);
					}
				}
			}
		}

		result[mainElementI - 1].getCoefficient(mainElementJ).invert();

		return result;
	}


	public SimplexTable getAuxiliaryTable() {


		SimplexTable table = null;
		table.isAuxiliary = true;

        return table;
	}

	public boolean isAuxiliary() {
		return isAuxiliary;
	}

	public boolean isOptimal() {
		for (int i = 1; i < function.getLength(); i++) {
			if (function.getCoefficient(i).compareTo(0) < 0) {
				return false;
			}
		}

		return true;
	}



	@Override
	public String toString() {
		StringJoiner result = new StringJoiner(" ");

		for (int i = 0; i < function.getLength(); i++) {
			result.add(function.getCoefficient(i).toString());
		}

		result.setEmptyValue("");
		result.add("\n");
		result.setEmptyValue(" ");

		for (int i = 0; i < limits.length; i++) {
			for (int j = 0; j < limits[i].getLength(); j++) {
				result.add(limits[i].getCoefficient(j).toString());
			}

			result.setEmptyValue("");
			result.add("\n");
			result.setEmptyValue(" ");
		}

		return result.toString();
	}

	@Override
	public boolean equals(Object o) {
		SimplexTable table = (SimplexTable) o;

		return 	Arrays.equals(table.limits, this.limits) &&
				table.function.equals(this.function);
	}
}
