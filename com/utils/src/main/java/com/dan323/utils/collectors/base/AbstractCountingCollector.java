package com.dan323.utils.collectors.base;

import com.dan323.utils.math.structure.Semigroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author danco
 */
public abstract class AbstractCountingCollector<N, R, S> implements Collector<S, Map<R, N>, Map<R, N>> {

    private Semigroup<N> semigroup;
    private Function<S,R> fun;

    public AbstractCountingCollector(Function<S,R> function, Semigroup<N> semigroup){
        this.fun = function;
        this.semigroup = semigroup;
    }

    protected Map<R, N> combine(Map<R, N> m1, Map<R, N> m2) {
        Map<R, N> m3 = new HashMap<>(m1);
        m2.forEach((key, value) -> updateMap(m3, key, value));
        return m3;
    }

    protected void updateMap(Map<R, N> m3, R st, N value) {
        if (m3.containsKey(st)) {
            m3.put(st, semigroup.add(m3.get(st), value));
        } else {
            m3.put(st, value);
        }
    }

    @Override
    public Supplier<Map<R, N>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<R, N>, S> accumulator() {
        return (map, t) -> updateMap(map, fun.apply(t), semigroup.getUnit());
    }

    @Override
    public BinaryOperator<Map<R, N>> combiner() {
        return this::combine;
    }

    @Override
    public Function<Map<R, N>, Map<R, N>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
