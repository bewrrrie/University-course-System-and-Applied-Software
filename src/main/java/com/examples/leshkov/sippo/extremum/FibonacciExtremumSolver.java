package com.examples.leshkov.sippo.extremum;

import com.examples.leshkov.sippo.extremum.data.DataUnit;
import com.examples.leshkov.sippo.extremum.exception.OutOfFunctionDomainException;
import com.examples.leshkov.sippo.math.NumericFunction;
import com.examples.leshkov.sippo.math.Point2D;

import static com.examples.leshkov.sippo.extremum.SolverConstants.FIBONACCI_MAX_ITER;

public class FibonacciExtremumSolver implements RealFunctionExtremumSolver {

    @Override
    public Point2D min(
        NumericFunction<Double, Double> f,
        double a,
        double b,
        double epsilon,
        int maxIterations
    ) throws OutOfFunctionDomainException {

		if (maxIterations <= 0 || maxIterations > FIBONACCI_MAX_ITER) {
			throw new IllegalArgumentException(
				"Iterations quantity of Fibonacci extremum solver " +
				"must be a positive long integer less than " +
				FIBONACCI_MAX_ITER + "!"
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

        int n = maxIterations;
        long[] fib = generateFibonacci(n);

        double x1 = a + (b - a) * ((double) fib[n - 3]) / ((double) fib[n - 1]);
        double x2 = a + (b - a) * ((double) fib[n - 2]) / ((double) fib[n - 1]);

        double y1 = f.getValue(x1);
		double y2 = f.getValue(x2);

        while (--n > 1) {
			if (y1 > y2) {
				left = x1;

				x1 = x2;
				x2 = right - (x1 - left);

				y1 = y2;
				y2 = f.getValue(x2);
			} else {
				right = x2;

				x2 = x1;
				x1 = left + (right - x2);

				y2 = y1;
				y1 = f.getValue(x1);
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

		if (maxIterations <= 0 || maxIterations > FIBONACCI_MAX_ITER) {
			throw new IllegalArgumentException(
				"Iterations quantity of Fibonacci extremum solver " +
				"must be a positive long integer less than " +
				FIBONACCI_MAX_ITER + "!"
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

		int n = maxIterations;
		long[] fib = generateFibonacci(n);

		double x1 = a + (b - a) * ((double) fib[n - 3]) / ((double) fib[n - 1]);
		double x2 = a + (b - a) * ((double) fib[n - 2]) / ((double) fib[n - 1]);

		double y1 = f.getValue(x1);
		double y2 = f.getValue(x2);

		while (--n > 1) {
			if (y1 < y2) {
				left = x1;

				x1 = x2;
				x2 = right - (x1 - left);

				y1 = y2;
				y2 = f.getValue(x2);
			} else {
				right = x2;

				x2 = x1;
				x1 = left + (right - x2);

				y2 = y1;
				y1 = f.getValue(x1);
			}
		}

		double xMax = 0.5 * (left + right);
		return new Point2D(xMax, f.getValue(xMax));
    }


    private long[] generateFibonacci(int n) {
    	long[] result = new long[n + 1];

    	result[0] = 0;
    	result[1] = 1;

    	for (int i = 2; i <= n; i++) {
			result[i] = result[i - 1] + result[i - 2];
		}

		return result;
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
