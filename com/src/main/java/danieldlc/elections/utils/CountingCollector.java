package danieldlc.elections.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountingCollector<S,T> implements Collector<S, Map<T,Integer>, Map<T,Integer>> {

    private final Function<S,T> fun;

    public CountingCollector(Function<S,T> function){
        this.fun=function;
    }

    @Override
    public Supplier<Map<T, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<T, Integer>, S> accumulator() {
        return (Map<T, Integer> map, S t)->{
            if (map.containsKey(fun.apply(t))) {
                map.put(fun.apply(t), map.get(fun.apply(t))+1);
            }
            else {
                map.put(fun.apply(t), 1);
            }
        };
    }

    @Override
    public BinaryOperator<Map<T, Integer>> combiner() {
        return (Map<T, Integer> m1, Map<T, Integer> m2)-> {
            Map<T, Integer> m3 = new HashMap<>(m1);
            m2.entrySet().parallelStream().forEach(e->{
                T st=e.getKey();
                if (m3.containsKey(st)) {
                    m3.put(st, m3.get(st)+e.getValue());
                }
                else {
                    m3.put(st, 1);
                }
            });
            return m3;
        };
    }

    @Override
    public Function<Map<T, Integer>, Map<T, Integer>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Stream.of(Characteristics.CONCURRENT,Characteristics.IDENTITY_FINISH).collect(Collectors.toSet());
    }

}
