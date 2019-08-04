package com.dan323.elections.systems.quo;

import java.util.Map;

/**
 * Class to abstract the remainders assignation methods.
 * <p>
 * Such a method, from the total amount, the map of remainders and the provisional results
 * changes this result map considering the remainders.
 *
 * @param <T> type of element receiving votes
 */
@FunctionalInterface
public interface Remainder<T> {
    void apply(int esc, Map<T, Double> remainders, Map<T, Integer> result);
}
