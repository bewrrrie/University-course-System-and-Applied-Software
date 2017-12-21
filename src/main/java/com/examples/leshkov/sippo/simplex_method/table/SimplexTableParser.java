package com.examples.leshkov.sippo.simplex_method.table;

import com.examples.leshkov.sippo.simplex_method.Fraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimplexTableParser {

	public static SimplexTable parse(final File file) throws IOException {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			List<String> linesToParse = readAllLines(in);
			FunctionLine function = parseFunctionLine(linesToParse.get(0));
			LimitLine[] limits = new LimitLine[linesToParse.size() - 1];

			for (int i = 1; i < linesToParse.size(); i++) {
				limits[i - 1] = parseLimitLine(linesToParse.get(i));
			}

			return new SimplexTable(function, limits);
		}
	}


	public static SimplexTable parse(final String filePath) throws IOException {
		return parse(new File(filePath));
	}



	private static ArrayList<String> readAllLines(final BufferedReader in) throws IOException {
		ArrayList<String> result = new ArrayList<>();

		String currentLine = in.readLine();
		while (currentLine != null) {
			result.add((currentLine));
			currentLine = in.readLine();
		}

		return result;
	}



	private static String checkForMaxMin(final String[] terms) {
		for (String t : terms) {
			if (t.equals("max")) {
				return "max";
			} else if (t.equals("min")) {
				return "min";
			}
		}

		return null;
	}

	private static String checkForLimitSign(final String[] terms) {
		for (String t : terms) {
			switch (t) {
				case "<=":
					return "<=";
				case ">=":
					return ">=";
				case "=":
					return "=";
			}
		}

		return null;
	}



	public static FunctionLine parseFunctionLine(final String line) {
		String[] terms = line.split("\\s+");

		String objective = checkForMaxMin(terms);
		if (objective == null) {
			throw new IllegalArgumentException("There is no 'max' or 'min' keyword in function line!");
		}


		Fraction[] coefficients = new Fraction[terms.length - 1];

		for (int i = 0; i < terms.length - 1; i++) {
			if (terms[i].contains("/")) {
				String[] numDenom = terms[i].split("/");

				coefficients[i] = new Fraction(
					Long.parseLong(numDenom[0]),
					Long.parseLong(numDenom[1])
				);

			} else {
				coefficients[i] = new Fraction(Long.parseLong(terms[i]));
			}
		}

		return new FunctionLine(coefficients, objective);
	}

	public static LimitLine parseLimitLine(final String line) {
		String[] terms = line.split("\\s+");

		String sign = checkForLimitSign(terms);
		if (sign == null) {
			throw new IllegalArgumentException("There is no '=' or '<=' or '>=' sign in limit line!");
		}


		Fraction[] coefficients = new Fraction[terms.length - 1];
		String[] currentFraction = terms[terms.length - 1].split("/");

		coefficients[0] = currentFraction.length > 1 ? new Fraction(
			Long.parseLong(currentFraction[0]),
			Long.parseLong(currentFraction[1])
		) : new Fraction(Long.parseLong(currentFraction[0]));


		for (int i = 0; i < coefficients.length - 1; i++) {
			currentFraction = terms[i].split("/");

			if (currentFraction.length > 1) {
				coefficients[i + 1] = new Fraction(
					Long.parseLong(currentFraction[0]),
					Long.parseLong(currentFraction[1])
				);
			} else {
				coefficients[i + 1] = new Fraction(Long.parseLong(currentFraction[0]));
			}
		}

		return new LimitLine(coefficients, sign);
	}
}