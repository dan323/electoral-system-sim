package com.dan323.elections.systems.quo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * {@link Quota} defines the price for one unit in the apportionment from the votes and the total.
 * Good Quotas do not give more units than the whole apportionment.
 * <p>
 * Our class have some extra methods to convert {@link Quota} from and to {@link Long} and {@link Double}.
 *
 * @param <T> type of element receiving voting
 * @param <N> number type of the vote count
 * @author danco
 */
@FunctionalInterface
public interface Quota<T, N extends Number> extends BiFunction<Map<T, N>, Integer, Double> {

    static <T> Quota<T, Double> convertToDouble(Quota<T, Long> quota) {
        return (map, x) -> quota.apply(convertToDouble(map), x);
    }

    static <T> Quota<T, Long> convertToLong(Quota<T, Double> quota) {
        return (map, x) -> quota.apply(convertToLong(map), x);
    }

    private static <T> Map<T, Double> convertToLong(Map<T, Long> map) {
        Map<T, Double> solution = new HashMap<>();
        for (Map.Entry<T, Long> entry : map.entrySet()) {
            solution.put(entry.getKey(), entry.getValue().doubleValue());
        }
        return solution;
    }

    private static <T> Map<T, Long> convertToDouble(Map<T, Double> map) {
        Map<T, Long> solution = new HashMap<>();
        for (Map.Entry<T, Double> entry : map.entrySet()) {
            solution.put(entry.getKey(), entry.getValue().longValue());
        }
        return solution;
    }

}
