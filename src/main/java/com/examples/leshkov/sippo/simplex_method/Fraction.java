package com.examples.leshkov.sippo.simplex_method;

public class Fraction {
	private long numerator, denominator;

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


	private void reduce() {
		long gcd = gcd(Math.abs(numerator), Math.abs(denominator));

		this.numerator /= gcd;
		this.denominator /= gcd;
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


	public void invertSign() {
		numerator *= -1;
	}

	public void invert() {
		long tmp = numerator;
		numerator = denominator;
		denominator = tmp;
	}


	@Override
	public String toString() {
		return numerator + "/" + denominator;
	}


	@Override
	public boolean equals(Object o) {
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
}
