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
			List<String> linesToParse = new ArrayList<>();
			String currentLine = in.readLine();

			while (currentLine != null) {
				linesToParse.add((currentLine));
				currentLine = in.readLine();
			}

			Fraction[] function = parseFirstLineToFunctionCoefficient(
				linesToParse.get(0).split(" ")
			);


			Fraction[] constants = new Fraction[linesToParse.size() - 1];
			Fraction[][] limits = new Fraction[linesToParse.size() - 1][linesToParse.get(0).split(" ").length - 2];

			for (int i = 1; i < linesToParse.size(); i++) {
				if (!containsEquatinOrInequationSign(linesToParse.get(i))) {
					throw new IllegalArgumentException(
						"Each limit line must contain exactly one of " +
						"\"=\", \"<=\", \">=\" equality or inequality signs!"
					);
				}

				Fraction[] parsedLine = parseCurrentLine(linesToParse.get(i).split(" "));
				constants[i - 1] = parsedLine[0];
				System.arraycopy(parsedLine, 1, limits[i - 1], 0, parsedLine.length - 1);
			}

			return new SimplexTable(function, constants, limits);
		}
	}


	public static SimplexTable parse(final String filePath) throws IOException {
		return parse(new File(filePath));
	}


	private static boolean containsEquatinOrInequationSign(final String s) {
		return s.contains(" = ") || s.contains(" >= ") || s.contains(" <= ");
	}


	private static Fraction[] parseFirstLineToFunctionCoefficient(final String[] terms) {
		Fraction[] function = new Fraction[terms.length - 1];

		int sign = 1;
		if (terms[terms.length - 1].equals("min")) {
			sign = -1;
		} else if (!terms[terms.length - 1].equals("max")) {
			throw new IllegalArgumentException(
				"At the end of first line \"max\"/\"min\" objective expected!"
			);
		}

		for (int j = 0; j < terms.length - 1; j++) {
			if (terms[j].contains("/")) {
				String[] frac = terms[j].split("/");

				function[j] = new Fraction(
					sign * Long.parseLong(frac[0]),
					Long.parseLong(frac[1])
				);
			} else {
				function[j] = new Fraction(
					sign * Long.parseLong(terms[j])
				);
			}
		}

		return function;
	}


	private static Fraction[] parseCurrentLine(final String[] terms) {
		Fraction[] result = new Fraction[terms.length - 1];

		int sign;
		if (terms[terms.length - 2].equals(">=")) {
			sign = -1;
		} else {
			sign = 1;
		}

		if (terms[terms.length - 1].contains("/")) {
			String[] frac = terms[terms.length - 1].split("/");

			result[0] = new Fraction(
				sign * Long.parseLong(frac[0]),
				Long.parseLong(frac[1])
			);
		} else {
			result[0] = new Fraction(
				sign * Long.parseLong(terms[terms.length - 1])
			);
		}

		for (int j = 0; j < terms.length - 2; j++) {
			if (terms[j].contains("/")) {
				String[] frac = terms[j].split("/");

				result[j + 1] = new Fraction(
					sign * Long.parseLong(frac[0]),
					Long.parseLong(frac[1])
				);
			} else {
				result[j + 1] = new Fraction(
					sign * Long.parseLong(terms[j])
				);
			}
		}

		return result;
	}
}
