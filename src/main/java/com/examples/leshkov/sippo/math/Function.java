package com.examples.leshkov.sippo.math;

public interface Function<X, Y> {
    Y getValue(X x);
    boolean isInDomain(X x);
}
