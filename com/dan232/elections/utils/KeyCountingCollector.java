package dan232.elections.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Este Collector sirve para contar el número de veces que aparece cada clave en un Stream<P>.
 * @author daconcep
 *
 * @param <T> Tipo de las claves
 */
public class KeyCountingCollector<T> implements Collector<Entry<T,?>, Map<T,Integer>, Map<T,Integer>> {
	
	@Override
	public Supplier<Map<T, Integer>> supplier() {
		return ()->new HashMap<>();
	}

	@Override
	public BiConsumer<Map<T, Integer>, Entry<T,?>> accumulator() {
		return (Map<T, Integer> map, Entry<T,?> pair)->{
			if (map.containsKey(pair.getKey())) {
				map.put(pair.getKey(), map.get(pair.getKey())+1);
			}
			else {
				map.put(pair.getKey(), 1);
			}
		};
	}

	@Override
	public BinaryOperator<Map<T, Integer>> combiner() {
		// TODO Auto-generated method stub
		return (Map<T, Integer> m1, Map<T, Integer> m2)-> {
			Map<T,Integer> m3=new HashMap<>();
			m3.putAll(m1);
			m2.entrySet().parallelStream().forEach(e->{
				T st=e.getKey();
				if (m3.containsKey(st)) {
					m3.put(st, m3.get(st)+1);
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