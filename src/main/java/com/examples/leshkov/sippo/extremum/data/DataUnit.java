package com.examples.leshkov.sippo.extremum.data;

public class DataUnit {
	private static final double CMP_ACC = 10e-9;

	private double a;
	private double b;
	private double epsilon;
	private int iterations;

	public DataUnit(double a, double b, double epsilon, int iterations) {
		this.a = a;
		this.b = b;
		this.epsilon = epsilon;
		setIter(iterations);
	}

	public DataUnit(DataUnit objectToCopy) {
		this.a = objectToCopy.a;
		this.b = objectToCopy.b;
		this.epsilon = objectToCopy.epsilon;
		setIter(objectToCopy.iterations);
	}

	private void setIter(int iter) {
		if (iter <= 0) {
			throw new IllegalArgumentException("Iteration quantity must be positive integer!");
		}

		iterations = iter;
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public int getIterations() {
		return iterations;
	}

	@Override
	public String toString() {
		return ( a + " " + b + " " + epsilon + " " ) + iterations;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DataUnit dataUnit = (DataUnit) o;

		return 	Math.abs(dataUnit.a - a) < CMP_ACC && Math.abs(dataUnit.b - b) < CMP_ACC &&
				Math.abs(dataUnit.epsilon - epsilon) < CMP_ACC && dataUnit.iterations == iterations;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(a);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(b);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(epsilon);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + iterations;
		return result;
	}
}
