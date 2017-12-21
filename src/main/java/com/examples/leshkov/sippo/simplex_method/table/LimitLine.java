package com.examples.leshkov.sippo.simplex_method.table;

import com.examples.leshkov.sippo.simplex_method.Fraction;

import java.util.Arrays;
import java.util.StringJoiner;

import static com.examples.leshkov.sippo.simplex_method.table.LimitLine.Sign.*;

public class LimitLine {
    public enum Sign {
        EQUAL ("="),
        LESS_OR_EQUAL ("<="),
        GREATER_OR_EQUAL (">=");

        private final String name;

        Sign(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private Fraction[] coefficients;
    private Sign sign;

    public LimitLine(final LimitLine line) {
        this.coefficients = new Fraction[line.coefficients.length];
        for (int j = 0; j < coefficients.length; j++) {
            this.coefficients[j] = new Fraction(line.coefficients[j]);
        }

        this.sign = line.sign;
    }

    public LimitLine(final Fraction[] coefficients, final Sign sign) {
        this.coefficients = coefficients;
        this.sign = sign;
    }

    public LimitLine(final Fraction[] coefficients, final String sign) {
        this.coefficients = coefficients;

        if (sign.equals("=")) {
            this.sign = Sign.EQUAL;
        } else if (sign.equals(">=")) {
            this.sign = GREATER_OR_EQUAL;
        } else if (sign.equals("<=")) {
            this.sign = Sign.LESS_OR_EQUAL;
        } else {
            throw new IllegalArgumentException("Sign string must be '=' or '<=' or '>=' !");
        }
    }

    public void multiplyBy(final Fraction f) {
        if (f.isNegative() && sign == GREATER_OR_EQUAL) {
            sign = LESS_OR_EQUAL;
        } else if (f.isNegative() && sign == LESS_OR_EQUAL) {
            sign = GREATER_OR_EQUAL;
        }

        for (int j = 0; j < coefficients.length; j++) {
            coefficients[j].multiplyBy(f);
        }
    }

    public Fraction getCoefficient(final int i) {
        return coefficients[i];
    }

    public int getLength() {
        return coefficients.length;
    }

    @Override
    public String toString() {
        final StringJoiner joiner = new StringJoiner(" ");

        for (int i = 1; i < coefficients.length; i++) {
            joiner.add(coefficients[i].toString());
        }

        joiner.add(sign.toString());
        joiner.add(coefficients[0].toString());

        return joiner.toString();
    }

    @Override
    public boolean equals(final Object o) {
        LimitLine l = (LimitLine) o;

        return  Arrays.equals(l.coefficients, this.coefficients) &&
                l.sign.equals(this.sign);
    }
}
