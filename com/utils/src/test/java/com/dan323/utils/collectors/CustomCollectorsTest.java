package com.dan323.utils.collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CustomCollectorsTest {
    private static final Logger LOG = Logger.getLogger("COUNTING TEST");
    private static final List<String> list = new ArrayList<>();
    private static final Random random = new Random();

    private static String keyMapping(String s, Map<String, String> map) {
        int i = 0;
        while (map.containsKey(s + i)) {
            i++;
        }
        return s + i;
    }

    @BeforeAll
    public static void setUp() {
        for (int i = 0; i < random.nextInt(10000) + 100; i++) {
            list.add("var" + random.nextInt(10) + "N");
        }
        LOG.log(Level.INFO, "The list has size {0}", list.size());
    }

    @Test
    public void toMapTesting() {
        Map<String, String> sol = list.stream()
                .collect(CustomCollectors.toMap(CustomCollectorsTest::keyMapping, (s, m) -> s));

        LOG.log(Level.INFO, "The list is " + list.toString());
        Map<String, String> test = getTestingMap();

        LOG.log(Level.INFO, "The solution is " + sol);
        LOG.log(Level.INFO, "The test is " + test);
        test.forEach((key, value) -> assertEntry(key, value, sol));

        Map<String, String> sol2 = list.parallelStream()
                .collect(CustomCollectors.toMap(CustomCollectorsTest::keyMapping, (s, m) -> s));

        LOG.log(Level.INFO, "The solution is " + sol2);
        Assertions.assertEquals(test.size(), sol2.size());

        test.forEach((key, value) -> assertEntry(key, value, sol2));
    }

    @Test
    public void toMapFinisherTesting() {
        Assertions.assertEquals((CustomCollectors.toMap(CustomCollectorsTest::keyMapping, (s, m) -> s)).finisher(), Function.identity());
    }

    private Map<String, String> getTestingMap() {
        Map<String, String> test = new ConcurrentHashMap<>();
        list.parallelStream()
                .collect(Collectors.groupingBy(Function.identity()))
                .forEach((st, list) -> IntStream.range(0, list.size()).parallel()
                        .mapToObj(i -> st + i)
                        .forEach(e -> test.put(e, st)));
        return test;
    }

    private void assertEntry(String key, String value, Map<String, String> sol) {
        LOG.log(Level.INFO, "The key is " + key);
        Assertions.assertEquals(value, sol.get(key));
    }

    @Test
    public void valueCountingMapTesting() {
        Map<String, String> test = getTestingMap();

        Map<String, Integer> countingMap = test.entrySet().stream()
                .collect(CustomCollectors.toValueCountingMapInt());

        Assertions.assertEquals(countingMap.values().stream().mapToInt(z -> z).sum(), list.size());
    }


    @Test
    public void valueCountingMapTestingLong() {
        Map<String, String> test = getTestingMap();

        Map<String, Long> countingMap = test.entrySet().stream()
                .collect(CustomCollectors.toValueCountingMapLong());

        Assertions.assertEquals(countingMap.values().stream().mapToLong(z -> z).sum(), list.size());
    }
}
