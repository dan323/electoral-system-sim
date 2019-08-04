package com.dan323.utils.collectors;

import com.dan323.utils.Pair;
import com.dan323.utils.collectors.main.CustomCollectors;
import com.dan323.utils.comparators.RandomComparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Test of the key counting collector.
 */
public class KeyCountingCollectorIntegerTest {

    private static final Logger LOG = Logger.getLogger("KEY COUNTING TEST");
    private static final List<Pair<String, Double>> list = new ArrayList<>();
    private static final Random random = new Random();
    private static final RandomComparator<String> randomComparator = new RandomComparator<>();

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < random.nextInt(1000000) + 100000; i++) {
            list.add(new Pair<>("var" + random.nextInt(100), random.nextDouble(), randomComparator));
        }
        LOG.log(Level.INFO, "The list has size {0}", list.size());
    }

    /**
     * We test collecting on a sequential stream.
     */
    @Test
    public void sequentialKeyCountingCollectorTesting() {
        Map<String, Integer> solution = list.stream().collect(CustomCollectors.toKeyCountingMapInt());

        Map<String, Integer> map = list.stream()
                .collect(Collectors.groupingBy(Pair::getKey))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));

        Assertions.assertEquals(solution, map);
    }


    /**
     * We test collection on a parallel stream.
     */
    @Test
    public void parallelKeyCountingCollectorTesting() {
        Map<String, Integer> solution = list.parallelStream().collect(CustomCollectors.toKeyCountingMapInt());

        Map<String, Integer> map = list.stream()
                .collect(Collectors.groupingBy(Pair::getKey))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));

        Assertions.assertEquals(solution, map);
    }
}