package com.examples.leshkov.sippo.extremum;

import com.examples.leshkov.sippo.extremum.data.DataUnit;
import com.examples.leshkov.sippo.extremum.exception.OutOfFunctionDomainException;
import com.examples.leshkov.sippo.math.NumericFunction;
import com.examples.leshkov.sippo.math.Point2D;

public interface RealFunctionExtremumSolver {
    Point2D min(
        NumericFunction<Double, Double> f,
        double a,
        double b,
        double epsilon,
        int iterations
    ) throws OutOfFunctionDomainException;

    Point2D max(
        NumericFunction<Double, Double> f,
        double a,
        double b,
        double epsilon,
        int iterations
    ) throws OutOfFunctionDomainException;

	Point2D min(
		NumericFunction<Double, Double> f,
		DataUnit unit
	) throws OutOfFunctionDomainException;

    Point2D max(
    	NumericFunction<Double, Double> f,
		DataUnit unit
	) throws OutOfFunctionDomainException;
}
