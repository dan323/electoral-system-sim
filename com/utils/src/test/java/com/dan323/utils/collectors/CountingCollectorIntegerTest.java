package com.dan323.utils.collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CountingCollectorIntegerTest {

    private static final Logger LOG = Logger.getLogger("COUNTING TEST");
    private static final List<String> list = new ArrayList<>();
    private static final Random random = new Random();


    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < random.nextInt(1000000) + 100000; i++) {
            list.add("var" + random.nextInt(100));
        }
        LOG.log(Level.INFO, "The list has size {0}", list.size());
    }


    @Test
    public void countingCollectorTesting() {
        Map<String, Integer> solution = list.stream().collect(new CountingCollectorInteger<>(Function.identity()));

        Map<String, Integer> map = list.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));

        Assertions.assertEquals(solution, map);

        solution = list.parallelStream().collect(new CountingCollectorInteger<>(Function.identity()));

        Assertions.assertEquals(solution, map);
    }

    @Test
    public void finisherIdentity(){
        Assertions.assertEquals((new CountingCollectorInteger<String,String>(Function.identity())).finisher(),Function.identity());
    }
}
