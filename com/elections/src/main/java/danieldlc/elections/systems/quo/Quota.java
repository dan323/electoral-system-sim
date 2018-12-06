package danieldlc.elections.systems.quo;

import java.util.Map;
import java.util.function.BiFunction;

@FunctionalInterface
public interface Quota extends BiFunction<Map<String,Integer>, Integer, Double> {

}
