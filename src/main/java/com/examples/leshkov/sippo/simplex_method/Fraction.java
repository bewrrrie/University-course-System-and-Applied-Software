package com.examples.leshkov.sippo.simplex_method;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Fraction implements Comparable<Fraction> {
	public static final Fraction ZERO = new Fraction();
	public static final Fraction ONE = new Fraction(1);
	public static final Fraction HALF = new Fraction(1, 2);
	public static final Fraction THIRD = new Fraction(1, 3);


	private long numerator, denominator;


	public Fraction() {
		numerator = 0;
		denominator = 1;
	}

	public Fraction(final long numerator) {
		this.numerator = numerator;
		this.denominator = 1;
	}

	public Fraction(final long numerator, final long denominator) {
		if (denominator == 0) {
			throw new IllegalArgumentException("Zero denominator.");
		}

		if (numerator < 0 && denominator < 0) {
			this.numerator = Math.abs(numerator);
			this.denominator = Math.abs(denominator);
		} else if (numerator > 0 && denominator < 0) {
			this.numerator = -numerator;
			this.denominator = -denominator;
		} else {
			this.numerator = numerator;
			this.denominator = denominator;
		}

		reduce();
	}

	public Fraction(final Fraction f) {
		this.numerator = f.numerator;
		this.denominator = f.denominator;
	}


	private void reduce() {
		if (numerator == 0) {
			denominator = 1;
		} else {
			long gcd = gcd(Math.abs(numerator), Math.abs(denominator));

			this.numerator /= gcd;
			this.denominator /= gcd;
		}
	}

	private long gcd (long a, long b) {
		while (a != 0 && b != 0) {
			if (a > b) {
				a %= b;
			} else {
				b %= a;
			}
		}

		if (a == 0) {
			return b;
		} else {
			return a;
		}
	}


	public long getNumerator() {
		return numerator;
	}

	public long getDenominator() {
		return denominator;
	}


	public Fraction add(final Fraction f) {
		Fraction result = new Fraction(
			numerator * f.denominator + f.numerator * denominator,
			denominator * f.denominator
		);

		result.reduce();
		return result;
	}

	public Fraction subtract(final Fraction f) {
		Fraction result = new Fraction(
			numerator * f.denominator - f.numerator * denominator,
			denominator * f.denominator
		);

		result.reduce();
		return result;
	}

	public Fraction multiply(final Fraction f) {
		Fraction result = new Fraction(
			numerator * f.numerator,
			denominator * f.denominator
		);

		result.reduce();
		return result;
	}

	public Fraction divide(final Fraction f) {
		Fraction result = new Fraction(
			numerator * f.denominator,
			denominator * f.numerator
		);

		result.reduce();
		return result;
	}


	public void increaseBy(final Fraction f) {
		denominator *= f.denominator;
		numerator *= f.denominator;
		numerator += f.numerator * denominator;
		reduce();
	}

	public void multiplyBy(final Fraction f) {
		numerator *= f.numerator;
		denominator *= f.denominator;
		reduce();
	}

	public void divideBy(final Fraction f) {
		numerator *= f.denominator;
		denominator *= f.numerator;
		reduce();
	}


	public void invertSign() {
		numerator *= -1;
	}

	public void invert() {
		long tmp = numerator;
		numerator = denominator;
		denominator = tmp;

		if (denominator < 0 && numerator > 0) {
			numerator *= -1;
			denominator *= -1;
		}
	}

	public Fraction getNegative() {
		return new Fraction(-numerator, denominator);
	}

	public Fraction getInverted() {
		return new Fraction(denominator, numerator);
	}

	public Fraction abs() {
		return new Fraction(Math.abs(numerator), denominator);
	}


	public boolean isPositive() {
		return numerator > 0;
	}

	public boolean isNegative() {
		return numerator < 0;
	}

	public boolean greaterThan(final Fraction f) {
		return compareTo(f) > 0;
	}

	public boolean lessThan(final Fraction f) {
		return compareTo(f) < 0;
	}

	public boolean isZero() {
		return numerator == 0;
	}


	@Override
	public String toString() {
		if (numerator == 0) {
			return "0";
		} else if (denominator == 1) {
			return Long.toString(numerator);
		}

		return numerator + "/" + denominator;
	}


	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (!(o instanceof Fraction)) return false;

		Fraction f = (Fraction) o;

		return getNumerator() * f.getDenominator() == getDenominator() * f.getNumerator();
	}

	@Override
	public int hashCode() {
		int result = (int) (getNumerator() ^ (getNumerator() >>> 32));
		result = 31 * result + (int) (getDenominator() ^ (getDenominator() >>> 32));
		return result;
	}

	@Override
	public int compareTo(final Fraction f) {
		BigInteger d = new BigInteger(
			Long.toString(numerator)
		).multiply(
			new BigInteger(Long.toString(f.denominator))
		).subtract(
			new BigInteger(Long.toString(f.numerator)).multiply(
				new BigInteger(Long.toString(denominator))
			)
		);

		return d.compareTo(BigInteger.ZERO);
	}

	public int compareTo(final double number) {
		BigDecimal d = new BigDecimal(
			Long.toString(numerator)
		).subtract(
			new BigDecimal(Double.toString(number)).multiply(
				new BigDecimal(Long.toString(denominator))
			)
		);

		return d.compareTo(BigDecimal.ZERO);
	}
}
