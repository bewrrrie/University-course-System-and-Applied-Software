package com.examples.leshkov.sippo;

import com.examples.leshkov.sippo.simplex_method.Fraction;
import com.examples.leshkov.sippo.simplex_method.table.SimplexTable;
import com.examples.leshkov.sippo.simplex_method.table.SimplexTableParser;

import java.io.File;
import java.io.IOException;

public class Main1 {

	public static void main(String[] args) throws IOException {
		/*
		SimplexTable table = new SimplexTable(
			new Fraction[] {new Fraction(), new Fraction(8), new Fraction(7)},

			new Fraction[] {
				new Fraction(30), new Fraction(40), new Fraction(50), new Fraction(50)
			},

			new Fraction[][] {
				{new Fraction(1), new Fraction(-2)},
				{new Fraction(3), new Fraction(2)},
				{new Fraction(2), new Fraction(1)},
				{new Fraction(2), new Fraction(2)}
			}
		);

		System.out.println(table);
		System.out.println();

		table.transform(1, 0);

		System.out.println(table);
		*/

		System.out.println(SimplexTableParser.parse("src/main/resources/linearExample1"));
	}
}
