package com.dan323.utils.collectors;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;

public final class CustomCollectors {

    private CustomCollectors() {
    }

    /**
     * <p>
     * Collects to a Map following the functions keyFunc and valueFunc. Make sure
     * that keyFunc returns values not in the map already.
     * </p>
     * Not recommended for parallel streams. It is safe, but costly.
     *
     * @param keyFunc function for key type
     * @param valueFunc function for value type
     * @param <S> type elements of the stream
     * @param <R> type of keys
     * @param <T> type of values
     * @return a collector that counts keys and values
     */
    public static <S, R, T> Collector<S, Map<R, T>, Map<R, T>> toMap(BiFunction<S, Map<R, T>, R> keyFunc, BiFunction<S, Map<R, T>, T> valueFunc) {
        return new MapKeyValueCheckingCollector<>(keyFunc, valueFunc);
    }

    public static <S, R> Collector<S, Map<R, Integer>, Map<R, Integer>> toCountingMap(Function<S, R> function) {
        return new CountingCollector<>(function);
    }

    public static <R, S> Collector<Entry<R, S>, Map<R, Integer>, Map<R, Integer>> toKeyCountingMap() {
        return toCountingMap(Entry::getKey);
    }

    public static <R, S> Collector<Entry<R, S>, Map<S, Integer>, Map<S, Integer>> toValueCountingMap() {
        return toCountingMap(Entry::getValue);
    }
}
