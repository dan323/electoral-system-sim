package com.dan323.elections.systems;

import com.dan323.elections.systems.quo.Quota;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author danco
 */
public class QuotaStaticTest {

    @Test
    public void convertToLong() {
        Quota<String, Long> quot = Quota.convertToLong((map, i) -> {
            if (map != null && !map.isEmpty()) {
                return map.values().stream().findAny().get();
            } else {
                return 0.0;
            }
        });
        Map<String,Long> map = new HashMap<>();
        Assertions.assertEquals(0.0, quot.apply(map, 5), 10E-3);
        map.put("Q",1L);
        Assertions.assertEquals(1.0, quot.apply(map, 5), 10E-3);
    }


    @Test
    public void convertToDouble() {
        Quota<String, Double> quot = Quota.convertToDouble((map, i) -> {
            if (map != null && !map.isEmpty()) {
                return map.values().stream().findAny().get().doubleValue();
            } else {
                return 0.0;
            }
        });
        Map<String,Double> map = new HashMap<>();
        Assertions.assertEquals(0.0, quot.apply(map, 5), 10E-3);
        map.put("Q",1.0);
        Assertions.assertEquals(1.0, quot.apply(map, 5), 10E-3);
    }
}
