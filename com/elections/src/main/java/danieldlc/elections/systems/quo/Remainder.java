package danieldlc.elections.systems.quo;

import java.util.Map;

@FunctionalInterface
public interface Remainder {

	void apply(int esc,Map<String, Double> aux,Map<String, Integer> res);
}
