package com.examples.leshkov.sippo;

import com.examples.leshkov.sippo.extremum.DichotomyExtremumSolver;
import com.examples.leshkov.sippo.extremum.FibonacciExtremumSolver;
import com.examples.leshkov.sippo.extremum.GoldSectionExtremumSolver;
import com.examples.leshkov.sippo.extremum.QuadraticInterpolationExtremumSolver;
import com.examples.leshkov.sippo.extremum.exception.OutOfFunctionDomainException;
import com.examples.leshkov.sippo.math.NumericFunction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

import static com.examples.leshkov.sippo.extremum.SolverConstants.*;

/**
 * Main class.
 */
public class Main {

	private static NumericFunction<Double, Double> createFunctionDefinedOnSegment(
		double a,
		double b
	) {

		return new NumericFunction<Double, Double>() {
			@Override
			public Double getValue(Double x) {
				// Custom function.
				// Can be changed to any other expression.
				return 9 * x - 3;
			}

			@Override
			public boolean isInDomain(Double x) {
				// Defined on [a, b].
				return a <= x && x <= b;
			}
		};

	}



    public static void main(String[] args) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int var = 0;
		while (var != 1 && var != 2) {
			System.out.println("Select source to read data.");
			System.out.println("\t1.File;");
			System.out.println("\t2.Terminal.");
			System.out.print("Enter your variant(1 or 2): ");

			var = Integer.parseInt(reader.readLine());
			System.out.println();
		}


		if (var == 1) {
			System.out.println("Enter file path:");
			String path = reader.readLine();
			reader = new BufferedReader(new FileReader(path));
		} else {
			System.out.println("Enter data:");
			System.out.println("(def_segment_min def_segment_max epsilon max_iterations_num)");
		}


	//  Example file at src/main/resources/data
		String s = reader.readLine();
		String[] words = s.split(" ");

		double a = Double.parseDouble(words[0]);
		double b = Double.parseDouble(words[1]);
		double epsilon = Double.parseDouble(words[2]);
		int maxIter = Integer.parseInt(words[3]);

		NumericFunction<Double, Double> function = createFunctionDefinedOnSegment(a, b);
		testExtremumMethods(function, a, b, epsilon, maxIter);
	}




    private static void testExtremumMethods(
		NumericFunction<Double, Double> f,
    	double a,
		double b,
		double epsilon,
		int maxIter
	) {

		DichotomyExtremumSolver dichotomy = new DichotomyExtremumSolver();
		FibonacciExtremumSolver fibonacci = new FibonacciExtremumSolver();
		GoldSectionExtremumSolver goldSection = new GoldSectionExtremumSolver();
		QuadraticInterpolationExtremumSolver quadInterp = new QuadraticInterpolationExtremumSolver();


		try {
			System.out.println("Function defined on segment [" + a + ", " + b + "]\n");

			System.out.println("Dichotomy:");
			if (maxIter <= DICHOTOMY_MAX_ITER) {
				System.out.println(
					"\tmin " + dichotomy.min(f, a, b, epsilon, maxIter) +
					"\n\tmax " + dichotomy.max(f, a, b, epsilon, maxIter) + "\n"
				);
			} else {
				System.out.println(
					"Method iteration limit exceeded. " +
					"Setting to maximal value (" + DICHOTOMY_MAX_ITER + ")."
				);

				System.out.println(
					"\tmin " + dichotomy.min(f, a, b, epsilon, DICHOTOMY_MAX_ITER) +
					"\n\tmax " + dichotomy.max(f, a, b, epsilon, DICHOTOMY_MAX_ITER) + "\n"
				);
			}


			System.out.println("Fibonacci:");
			if (maxIter <= FIBONACCI_MAX_ITER) {
				System.out.println(
					"\tmin " + fibonacci.min(f, a, b, epsilon, maxIter) +
					"\n\tmax " + fibonacci.max(f, a, b, epsilon, maxIter) + "\n"
				);
			} else {
				System.out.println(
					"Method iteration limit exceeded. " +
					"Setting to maximal value (" + FIBONACCI_MAX_ITER + ")."
				);

				System.out.println(
					"\tmin " + fibonacci.min(f, a, b, epsilon, FIBONACCI_MAX_ITER) +
					"\n\tmax " + fibonacci.max(f, a, b, epsilon, FIBONACCI_MAX_ITER) + "\n"
				);
			}


			System.out.println("Gold section:");
			if (maxIter <= GOLD_SECTION_MAX_ITER) {
				System.out.println(
					"\tmin " + goldSection.min(f, a, b, epsilon, maxIter) +
					"\n\tmax " + goldSection.max(f, a, b, epsilon, maxIter) + "\n"
				);
			} else {
				System.out.println(
					"Method iteration limit exceeded. " +
					"Setting to maximal value (" + GOLD_SECTION_MAX_ITER + ")."
				);

				System.out.println(
					"\tmin " + goldSection.min(f, a, b, epsilon, GOLD_SECTION_MAX_ITER) +
					"\n\tmax " + goldSection.max(f, a, b, epsilon, GOLD_SECTION_MAX_ITER) + "\n"
				);
			}


			System.out.println("Quadratic interpolation:");
			if (maxIter <= QUADRATIC_INTERPOLATION_MAX_ITER) {
				System.out.println(
					"\tmin " + quadInterp.min(f, a, b, epsilon, maxIter) +
					"\n\tmax " + quadInterp.max(f, a, b, epsilon, maxIter) + "\n"
				);
			} else {
				System.out.println(
					"Method iteration limit exceeded. " +
					"Setting to maximal value (" + QUADRATIC_INTERPOLATION_MAX_ITER + ")."
				);

				System.out.println(
					"\tmin " + goldSection.min(f, a, b, epsilon, QUADRATIC_INTERPOLATION_MAX_ITER) +
					"\n\tmax " + goldSection.max(f, a, b, epsilon, QUADRATIC_INTERPOLATION_MAX_ITER) + "\n"
				);
			}

			System.out.println();

		} catch (OutOfFunctionDomainException e) {
			System.err.println("Calculation segment is out of specified function domain.");
			e.printStackTrace();
		}
	}
}
