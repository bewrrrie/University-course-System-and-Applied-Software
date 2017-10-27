package com.examples.leshkov.sippo.extremum;

import com.examples.leshkov.sippo.extremum.exception.OutOfFunctionDomainException;
import com.examples.leshkov.sippo.math.NumericFunction;
import com.examples.leshkov.sippo.math.Point2D;
import org.junit.Before;
import org.junit.Test;

import static com.examples.leshkov.sippo.math.Constants.EPSILON;
import static org.junit.Assert.*;

public class DichotomyExtremumSolverTest {

	private double epsilon;
	private DichotomyExtremumSolver solver;

	@Before
	public void setUp() throws Exception {
		epsilon = EPSILON;
		solver = new DichotomyExtremumSolver();
	}


	@Test
	public void testIdMin() throws OutOfFunctionDomainException {
		NumericFunction<Double, Double> linear = new NumericFunction<Double, Double>() {
			@Override
			public Double getValue(Double x) {
				return x;
			}

			@Override
			public boolean isInDomain(Double x) {
				return -1 <= x && x <= 1;
			}
		};

		assertEquals(
			new Point2D(-1, -1),
			solver.min(linear, -1, 1, epsilon, 10000)
		);
	}

	@Test
	public void testIdMax() throws OutOfFunctionDomainException {
		NumericFunction<Double, Double> linear = new NumericFunction<Double, Double>() {
			@Override
			public Double getValue(Double x) {
				return x;
			}

			@Override
			public boolean isInDomain(Double x) {
				return -1 <= x && x <= 1;
			}
		};

		assertEquals(
			new Point2D(1, 1),
			solver.max(linear, -1, 1, epsilon, 10000)
		);
	}


	@Test
	public void testLinearMin() throws OutOfFunctionDomainException {
		NumericFunction<Double, Double> linear = new NumericFunction<Double, Double>() {
			@Override
			public Double getValue(Double x) {
				return 3 * x + 1;
			}

			@Override
			public boolean isInDomain(Double x) {
				return 0 <= x && x <= 4;
			}
		};

		assertEquals(
			new Point2D(0, 1),
			solver.min(linear, 0, 4, epsilon, 10000)
		);
	}

	@Test
	public void testLinearMax() throws OutOfFunctionDomainException {
		NumericFunction<Double, Double> linear = new NumericFunction<Double, Double>() {
			@Override
			public Double getValue(Double x) {
				return 3 * x + 1;
			}

			@Override
			public boolean isInDomain(Double x) {
				return 0 <= x && x <= 4;
			}
		};

		assertEquals(
			new Point2D(4, 13),
			solver.max(linear, 0, 4, epsilon, 10000)
		);
	}


	@Test
	public void testCosChMin() throws OutOfFunctionDomainException {
		NumericFunction<Double, Double> cosch = new NumericFunction<Double, Double>() {
			@Override
			public Double getValue(Double x) {
				return Math.cos(x) + 10 * Math.cosh(x);
			}

			@Override
			public boolean isInDomain(Double x) {
				return -1 <= x && x <= 1;
			}
		};

		assertEquals(
			new Point2D(0, 11),
			solver.min(cosch, -1, 1, epsilon, 50)
		);
	}

	@Test
	public void testCosChMax() throws OutOfFunctionDomainException {
		NumericFunction<Double, Double> cosch = new NumericFunction<Double, Double>() {
			@Override
			public Double getValue(Double x) {
				return Math.cos(x) + 10 * Math.cosh(x);
			}

			@Override
			public boolean isInDomain(Double x) {
				return -1 <= x && x <= 5;
			}
		};

		assertEquals(
			new Point2D(5, cosch.getValue(5d)),
			solver.max(cosch, -1, 5, epsilon, 10000)
		);
	}


	@Test
	public void testParabolaMin() throws OutOfFunctionDomainException {
		NumericFunction<Double, Double> parabola = new NumericFunction<Double, Double>() {
			@Override
			public Double getValue(Double x) {
				return 2 + x - x * x;
			}

			@Override
			public boolean isInDomain(Double x) {
				return 0 <= x && x <= 1;
			}
		};

		assertEquals(
			new Point2D(0, 2),
			solver.min(parabola, 0, 1, epsilon, 10000)
		);
	}

	@Test
	public void testParabolaMax() throws OutOfFunctionDomainException {
		NumericFunction<Double, Double> parabola = new NumericFunction<Double, Double>() {
			@Override
			public Double getValue(Double x) {
				return 2 + x - x * x;
			}

			@Override
			public boolean isInDomain(Double x) {
				return 0 <= x && x <= 1;
			}
		};

		assertEquals(
			new Point2D(0.5, 2.25),
			solver.max(parabola, 0, 1, epsilon, 10000)
		);
	}
}