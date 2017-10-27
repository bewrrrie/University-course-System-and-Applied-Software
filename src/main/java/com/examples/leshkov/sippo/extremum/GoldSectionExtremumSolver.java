package com.examples.leshkov.sippo.extremum;

import com.examples.leshkov.sippo.extremum.data.DataUnit;
import com.examples.leshkov.sippo.extremum.exception.OutOfFunctionDomainException;
import com.examples.leshkov.sippo.math.NumericFunction;
import com.examples.leshkov.sippo.math.Point2D;

import static com.examples.leshkov.sippo.extremum.SolverConstants.GOLD_SECTION_MAX_ITER;
import static com.examples.leshkov.sippo.math.Constants.PHI;

public class GoldSectionExtremumSolver implements RealFunctionExtremumSolver {

    @Override
    public Point2D min(
        NumericFunction<Double, Double> f,
        double a,
        double b,
        double epsilon,
        int maxIterations
    ) throws OutOfFunctionDomainException {

        if (maxIterations <= 0 || maxIterations > GOLD_SECTION_MAX_ITER) {
            throw new IllegalArgumentException(
                "Iterations quantity of gold section extremum solver " +
				"must be a positive long integer less than " +
                GOLD_SECTION_MAX_ITER + "!"
            );
        }

        if (!(f.isInDomain(a) && f.isInDomain(b))) {
            throw new OutOfFunctionDomainException(
                "Given function domain does not contain segment [" + a + ", " + b + "]!"
            );
        }

        double left, right;

        if (a > b) {
            left = b;
            right = a;
        } else {
            left = a;
            right = b;
        }

        long iteration = -1;
        while (Math.abs(right - left) > epsilon && ++iteration < maxIterations) {
            double len = right - left;

            double x1 = right - len / PHI;
            double x2 = left + len / PHI;

            double y1 = f.getValue(x1);
            double y2 = f.getValue(x2);

            if (y1 >= y2) {
                left = x1;
            } else {
                right = x2;
            }
        }

        double xMin = 0.5 * (right + left);
        return new Point2D(xMin, f.getValue(xMin));
    }


    @Override
    public Point2D max(
        NumericFunction<Double, Double> f,
        double a,
        double b,
        double epsilon,
        int maxIterations
    ) throws OutOfFunctionDomainException {

		if (maxIterations <= 0 || maxIterations > GOLD_SECTION_MAX_ITER) {
			throw new IllegalArgumentException(
				"Iterations quantity of gold section extremum solver " +
				"must be a positive long integer less than " +
				GOLD_SECTION_MAX_ITER + "!"
			);
		}

        if (!(f.isInDomain(a) && f.isInDomain(b))) {
            throw new OutOfFunctionDomainException(
                "Given function domain does not contain segment [" + a + ", " + b + "]!"
            );
        }

        double left, right;

        if (a > b) {
            left = b;
            right = a;
        } else {
            left = a;
            right = b;
        }

        long iteration = -1;
        while (Math.abs(right - left) > epsilon && ++iteration < maxIterations) {
            double len = right - left;

            double x1 = right - len / PHI;
            double x2 = left + len / PHI;

            double y1 = f.getValue(x1);
            double y2 = f.getValue(x2);

            if (y1 <= y2) {
                left = x1;
            } else {
                right = x2;
            }
        }

        double xMax = 0.5 * (right + left);
        return new Point2D(xMax, f.getValue(xMax));
    }


	@Override
	public Point2D min(
		NumericFunction<Double, Double> f,
		DataUnit unit
	) throws OutOfFunctionDomainException {
		return min(f, unit.getA(), unit.getB(), unit.getEpsilon(), unit.getIterations());
	}

	@Override
	public Point2D max(
		NumericFunction<Double, Double> f,
		DataUnit unit
	) throws OutOfFunctionDomainException {
		return max(f, unit.getA(), unit.getB(), unit.getEpsilon(), unit.getIterations());
	}
}
