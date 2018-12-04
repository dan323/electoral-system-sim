package danieldlc.elections.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;

public final class CustomCollectors {

    private CustomCollectors(){}

    public static <S,R,T> Collector<S,Map<R,T>, Map<R,T>> toMap(BiFunction<S,Map<R,T>,R> keyFunc, BiFunction<S,Map<R,T>,T> valueFunc){

        return Collector.of(HashMap::new,
                (Map<R,T> map, S s)->map.put(keyFunc.apply(s,map),valueFunc.apply(s,map)),
                (map1,map2)->{
                    map1.putAll(map2);
                    return map1;
                    },
                Collector.Characteristics.IDENTITY_FINISH,
                Collector.Characteristics.UNORDERED,
                Collector.Characteristics.CONCURRENT);
    }

    public static <S,R> Collector<S,Map<R,Integer>,Map<R,Integer>> toCountingMap(Function<S,R> function){
        return new CountingCollector<>(function,false);
    }

    public static <R,S> Collector<Entry<R,S>,Map<R,Integer>,Map<R,Integer>> toKeyCountingMap(){
        return toCountingMap(Entry::getKey);
    }

    public static <R,S> Collector<Entry<R,S>,Map<S,Integer>,Map<S,Integer>> toValueCountingMap(){
        return toCountingMap(Entry::getValue);
    }
}
