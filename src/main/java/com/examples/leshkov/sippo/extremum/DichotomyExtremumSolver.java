package com.examples.leshkov.sippo.extremum;

import com.examples.leshkov.sippo.extremum.data.DataUnit;
import com.examples.leshkov.sippo.extremum.exception.OutOfFunctionDomainException;
import com.examples.leshkov.sippo.math.NumericFunction;
import com.examples.leshkov.sippo.math.Point2D;

import static com.examples.leshkov.sippo.extremum.SolverConstants.DICHOTOMY_MAX_ITER;

public class DichotomyExtremumSolver implements RealFunctionExtremumSolver {

    @Override
    public Point2D min(
        NumericFunction<Double, Double> f,
        double a,
        double b,
        double epsilon,
        int maxIterations
    ) throws OutOfFunctionDomainException {

        if (maxIterations <= 0 || maxIterations > DICHOTOMY_MAX_ITER) {
            throw new IllegalArgumentException(
                "Iterations quantity of dichotomy extremum solver " +
				"must be a positive long integer less than " +
				DICHOTOMY_MAX_ITER + "!"
            );
        }

        if (!(f.isInDomain(a) && f.isInDomain(b))) {
            throw new OutOfFunctionDomainException(
                "Given function domain does not contain segment [" + a + ", " + b + "]!"
            );
        }

		if (b < a) {
			throw new IllegalArgumentException("b < a");
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
        	double delta = (right - left) / 4; // (0, (b-a)/2)

            double x1 = 0.5 * (left + right) - delta;
            double x2 = 0.5 * (left + right) + delta;

            double y1 = f.getValue(x1);
            double y2 = f.getValue(x2);

            if (y1 > y2) {
                left = x1;
            } else {
                right = x2;
            }
		}

        double xMin = 0.5 * (left + right);
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

		if (maxIterations <= 0 || maxIterations > DICHOTOMY_MAX_ITER) {
			throw new IllegalArgumentException(
				"Iterations quantity of dichotomy extremum solver " +
				"must be a positive long integer less than " +
				DICHOTOMY_MAX_ITER + "!"
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
			double delta = (right - left) / 4; // (0, (b-a)/2)

            double x1 = 0.5 * (left + right) - delta;
            double x2 = 0.5 * (left + right) + delta;

            double y1 = f.getValue(x1);
            double y2 = f.getValue(x2);

            if (y1 < y2) {
                left = x1;
            } else {
                right = x2;
            }
        }

        double xMax = 0.5 * (left + right);
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
