package dan232.elections.quo;

import java.util.Map;

@FunctionalInterface
public interface Remainder {

	void apply(int esc,Map<String, Double> aux,Map<String, Integer> res);
}
