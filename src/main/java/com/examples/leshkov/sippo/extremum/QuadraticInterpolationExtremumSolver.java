package com.examples.leshkov.sippo.extremum;

import com.examples.leshkov.sippo.extremum.data.DataUnit;
import com.examples.leshkov.sippo.extremum.exception.OutOfFunctionDomainException;
import com.examples.leshkov.sippo.math.NumericFunction;
import com.examples.leshkov.sippo.math.Point2D;

public class QuadraticInterpolationExtremumSolver implements RealFunctionExtremumSolver {

    @Override
    public Point2D min(
        NumericFunction<Double, Double> f,
        double a,
        double b,
        double epsilon,
        int maxIterations
    ) throws OutOfFunctionDomainException {
		double[] c = getParabolaCoefficients(f, a, b);
		double x;

		if (c[0] == 0) {
			if (c[1] > 0) {
				x = a;
			} else {
				x = b;
			}
		} else {
			double vertex = -c[1] / (2 * c[0]);

			if (vertex < a) {
				x = c[0] > 0 ? a : b;
			} else if (b < vertex) {
				x = c[0] > 0 ? b : a;
			} else if (c[0] > 0) {
				x = vertex;
			} else {
				x = f.getValue(a) > f.getValue(b) ? b : a;
			}
		}

		return new Point2D(x, f.getValue(x));
    }

    @Override
    public Point2D max(
        NumericFunction<Double, Double> f,
        double a,
        double b,
        double epsilon,
        int maxIterations
    ) throws OutOfFunctionDomainException {
        double[] c = getParabolaCoefficients(f, a, b);
        double x;

		if (c[0] == 0) {
			if (c[1] > 0) {
				x = b;
			} else {
				x = b;
			}
		} else {
			double vertex = -c[1] / (2 * c[0]);

			if (vertex < a) {
				x = c[0] < 0 ? a : b;
			} else if (b < vertex) {
				x = c[0] < 0 ? b : a;
			} else if (c[0] < 0) {
				x = vertex;
			} else {
				x = f.getValue(a) > f.getValue(b) ? a : b;
			}
		}

		return new Point2D(x, f.getValue(x));
	}


    private double[] getParabolaCoefficients(NumericFunction<Double, Double> f, double a, double b) {
        double alpha = a;
		double beta = 0.5d * (a + b);
		double gamma = b;

        double delta = ((alpha - beta) * (beta - gamma) * (gamma - alpha));

        double fAlpha = f.getValue(alpha);
        double fBeta = f.getValue(beta);
        double fGamma = f.getValue(gamma);

        return new double[] {
			(
                fAlpha * (gamma - beta) +
                fBeta * (alpha - gamma) +
                fGamma * (beta - alpha)
            ) / delta,
            (
                fAlpha * (beta * beta - gamma * gamma) +
                fBeta * (gamma * gamma - alpha * alpha) +
                fGamma * (alpha * alpha - beta * beta)
            ) / delta
        };
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
