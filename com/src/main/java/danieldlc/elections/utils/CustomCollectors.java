package danieldlc.elections.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

public final class CustomCollectors {

    private CustomCollectors() {
    }

    /**
     * Collects to a Map following the functions keyFunc and valueFunc. Make sure
     * that keyFunc returns values not in the map already; otherwise the behaviour
     * of this method is not assured. Beware that the map entering the Biconsumer
     * is not the whole map if on a parallel stream.
     *
     * @param keyFunc
     * @param valueFunc
     * @param <S>
     * @param <R>
     * @param <T>
     * @return
     */
    public static <S, R, T> Collector<S, Map<R, T>, Map<R, T>> toMap(BiFunction<S, Map<R, T>, R> keyFunc, BiFunction<S, Map<R, T>, T> valueFunc) {

        return Collector.of(HashMap::new,
                (Map<R, T> map, S s) -> map.put(keyFunc.apply(s, map), valueFunc.apply(s, map)),
                CustomCollectors::mergeMaps,
                Collector.Characteristics.IDENTITY_FINISH,
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.CONCURRENT);
    }

    /**
     * The same as {@link CustomCollectors#toMap}, but with a custom merging function to be able to take care
     * of concurrent or parallel execution problems.
     *
     * @param keyFunc
     * @param valueFunc
     * @param mergeMappings
     * @param <S>
     * @param <R>
     * @param <T>
     * @return
     */
    public static <S, R, T> Collector<S, Map<R, T>, Map<R, T>> toMap(BiFunction<S, Map<R, T>, R> keyFunc, BiFunction<S, Map<R, T>, T> valueFunc,
                                                                     BinaryOperator<Map<R, T>> mergeMappings) {

        return Collector.of(HashMap::new,
                (Map<R, T> map, S s) -> map.put(keyFunc.apply(s, map), valueFunc.apply(s, map)),
                mergeMappings,
                Collector.Characteristics.IDENTITY_FINISH,
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.CONCURRENT);
    }

    private static <R, T> Map<R, T> mergeMaps(Map<R, T> map1, Map<R, T> map2) {
        map1.putAll(map2);
        return map1;
    }

    public static <S, R> Collector<S, Map<R, Integer>, Map<R, Integer>> toCountingMap(Function<S, R> function) {
        return new CountingCollector<>(function, false);
    }

    public static <R, S> Collector<Entry<R, S>, Map<R, Integer>, Map<R, Integer>> toKeyCountingMap() {
        return toCountingMap(Entry::getKey);
    }

    public static <R, S> Collector<Entry<R, S>, Map<S, Integer>, Map<S, Integer>> toValueCountingMap() {
        return toCountingMap(Entry::getValue);
    }
}
