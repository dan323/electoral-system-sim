package com.dan323.elections.systems.div;

import java.util.function.Function;

/**
 * Class to abstract the concept of divisor.
 * <p>
 * A divisor function is used to define rounding methods.
 * In particular to round rationals whose sum is an integer
 * in order to keep the rounded integers to add to the same
 * number as the integers.
 * <p>
 * The good divisor functions satisfy the following:
 * n <= Divisor(n) <= n+1
 *
 * @author danco
 */
@FunctionalInterface
public interface Divisor extends Function<Integer, Double> {
}
