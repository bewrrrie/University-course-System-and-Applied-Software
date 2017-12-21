package com.examples.leshkov.sippo.simplex_method.table;

import com.examples.leshkov.sippo.simplex_method.Fraction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.examples.leshkov.sippo.simplex_method.table.FunctionLine.Objective.*;
import static com.examples.leshkov.sippo.simplex_method.table.LimitLine.Sign.*;
import static org.junit.Assert.*;

public class SimplexTableParserTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testParseLimit() {
        LimitLine l = new LimitLine(new Fraction[]{
            new Fraction(),
            new Fraction(1),
            new Fraction(-2),
            new Fraction(3),
            new Fraction(-9, 8),
            new Fraction(1)
        }, LESS_OR_EQUAL);

        assertEquals(l, SimplexTableParser.parseLimitLine("1 -2 3 -9/8 9/9 <= 0"));
    }

    @Test
    public void testParseLimit1() {
        LimitLine l = new LimitLine(new Fraction[]{
            new Fraction(12, 13),
            new Fraction(90/3),
            new Fraction(222),
            new Fraction(-1, 3),
            new Fraction(9, 8),
            new Fraction()
        }, EQUAL);

        assertEquals(l, SimplexTableParser.parseLimitLine("30 222    -1/3 9/8 0 = 12/13"));
    }

    @Test
    public void testParseFunction() {
        FunctionLine f = new FunctionLine(new Fraction[]{
            new Fraction(12, 13),
            new Fraction(90/3),
            new Fraction(222),
            new Fraction(-1, 3),
            new Fraction(9, 8),
            new Fraction()
        }, MAX);

        assertEquals(f, SimplexTableParser.parseFunctionLine("12/13 30 222 -1/3 9/8    0 max"));
    }


    @Test
    public void testParseTable() throws IOException {
        File f = folder.newFile("tmp");

        try (BufferedWriter out = new BufferedWriter(new FileWriter(f))) {
            out.write(
                "0   -3    1 2 0 0    max\n" +
                "-1   1 2    	 0  0   >= 2\n" +
                "1    0   1 1 0  = 4\n" +
                "1   1   1  0   -1   <=  	 6\n"
            );
        }


        FunctionLine expFunction = new FunctionLine(
            new Fraction[] {
                new Fraction(),
                new Fraction(-3),
                new Fraction(1),
                new Fraction(2),
                new Fraction(),
                new Fraction()
            }, MAX
        );


        LimitLine l1 = new LimitLine(
            new Fraction[] {
                new Fraction(2),
                new Fraction(-1),
                new Fraction(1),
                new Fraction(2),
                new Fraction(),
                new Fraction(),
            }, GREATER_OR_EQUAL
        );

        LimitLine l2 = new LimitLine(
            new Fraction[] {
                new Fraction(4),
                new Fraction(1),
                new Fraction(),
                new Fraction(1),
                new Fraction(1),
                new Fraction(),
            }, EQUAL
        );

        LimitLine l3 = new LimitLine(
            new Fraction[] {
                new Fraction(6),
                new Fraction(1),
                new Fraction(1),
                new Fraction(1),
                new Fraction(),
                new Fraction(-1),
            }, LESS_OR_EQUAL
        );

        SimplexTable expected = new SimplexTable(expFunction, new LimitLine[] {l1, l2, l3});

        assertEquals(expected, SimplexTableParser.parse(f));
    }
}