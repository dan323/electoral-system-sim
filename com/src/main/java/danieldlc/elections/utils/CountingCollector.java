package danieldlc.elections.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CountingCollector<S, T> implements Collector<S, Map<T, Integer>, Map<T, Integer>> {

    private final Function<S, T> fun;
    private final boolean parallel;

    public CountingCollector(Function<S, T> function, boolean parallel) {
        this.fun = function;
        this.parallel = parallel;
    }

    private static <R> Map<R, Integer> combineParallel(Map<R, Integer> m1, Map<R, Integer> m2) {
        Map<R, Integer> m3 = new ConcurrentHashMap<>(m1);
        m2.entrySet().parallelStream().forEach(e -> updateMap(m3, e.getKey(), e.getValue()));
        return m3;
    }

    private static <R> Map<R, Integer> combine(Map<R, Integer> m1, Map<R, Integer> m2) {
        Map<R, Integer> m3 = new HashMap<>(m1);
        m2.forEach((key, value) -> updateMap(m3, key, value));
        return m3;
    }

    private static <U> void updateMap(Map<U, Integer> m3, U st, int value) {
        if (m3.containsKey(st)) {
            m3.put(st, m3.get(st) + value);
        } else {
            m3.put(st, value);
        }
    }

    @Override
    public Supplier<Map<T, Integer>> supplier() {
        return HashMap::new;
    }

    private void accumulate(Map<T, Integer> map, S t) {
        T key = fun.apply(t);
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
    }

    @Override
    public BiConsumer<Map<T, Integer>, S> accumulator() {
        return this::accumulate;
    }

    @Override
    public BinaryOperator<Map<T, Integer>> combiner() {
        return parallel ? CountingCollector::combineParallel : CountingCollector::combine;
    }

    @Override
    public Function<Map<T, Integer>, Map<T, Integer>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.CONCURRENT, Characteristics.IDENTITY_FINISH);
    }

}
