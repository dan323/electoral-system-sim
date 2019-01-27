package danieldlc.elections.systems.quo;

import java.util.Map;

@FunctionalInterface
public interface Remainder<T> {

	void apply(int esc,Map<T, Double> aux,Map<T, Integer> res);
}
