package com.examples.leshkov.sippo.simplex_method.table;

import com.examples.leshkov.sippo.simplex_method.Fraction;

import java.util.Arrays;
import java.util.StringJoiner;

import static com.examples.leshkov.sippo.simplex_method.table.FunctionLine.Objective.*;

public class FunctionLine {
    public enum Objective {
        MAX ("max"),
        MIN ("min");

        private final String name;

        Objective(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private Fraction[] coefficients;
    private Objective obj;


    public FunctionLine(final FunctionLine f) {
        this.coefficients = new Fraction[f.getLength()];
        for (int i = 0; i < f.getLength(); i++) {
            this.coefficients[i] = new Fraction(f.getCoefficient(i));
        }

        this.obj = f.obj;
    }

    public FunctionLine(final Fraction[] coefficients, final Objective obj) {
        this.coefficients = coefficients;
        this.obj = obj;
    }

    public FunctionLine(final Fraction[] coefficients, final String obj) {
        this.coefficients = coefficients;

        if (obj.toLowerCase().equals("max")) {
            this.obj = MAX;
        } else if (obj.toLowerCase().equals("min")) {
            this.obj = Objective.MIN;
        } else {
            throw new IllegalArgumentException("Objective string must be 'max' or 'min'!");
        }
    }

    public void multiplyBy(final Fraction f) {
        if (f.isNegative() && obj == MAX) {
            obj = MIN;
        } else if (f.isNegative() && obj == MIN) {
            obj = MAX;
        }

        for (int j = 0; j < coefficients.length; j++) {
            coefficients[j].multiplyBy(f);
        }
    }

    public int getLength() {
        return coefficients.length;
    }

    public Fraction getCoefficient(final int i) {
        return coefficients[i];
    }

    public Objective getObj() {
        return obj;
    }

    @Override
    public String toString() {
        final StringJoiner joiner = new StringJoiner(" ");

        for (Fraction coefficient : coefficients) {
            joiner.add(coefficient.toString());
        }

        return joiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        FunctionLine f = (FunctionLine) o;

        return  Arrays.equals(f.coefficients, this.coefficients) &&
                f.obj.equals(this.obj);
    }
}
