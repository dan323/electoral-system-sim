package danieldlc.elections.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CountingCollectorTest {

    private static final Logger LOG = Logger.getLogger("COUNTING TEST");
    private static List<String> list = new ArrayList<>();
    private static Random random = new Random();


    @Before
    public void setUp() {
        for (int i = 0; i < random.nextInt(1000000)+100000; i++) {
            list.add("var" + random.nextInt(100));
        }
        LOG.log(Level.INFO,"The list has size {0}",list.size());
    }


    @Test
    public void sequentialCountingCollectorTesting() {
        Map<String, Integer> solution = list.stream().collect(new CountingCollector<>(Function.identity(),false));

        Map<String, Integer> map = list.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));

        Assert.assertEquals(solution, map);

        solution = list.parallelStream().collect(new CountingCollector<>(Function.identity(),false));

        Assert.assertEquals(solution, map);
    }

    @Test
    public void parallelCountingCollectorTesting() {
        Map<String, Integer> solution = list.stream().collect(new CountingCollector<>(Function.identity(),true));

        Map<String, Integer> map = list.stream()
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));

        Assert.assertEquals(solution, map);

        solution= list.parallelStream().collect(new CountingCollector<>(Function.identity(),true));

        Assert.assertEquals(solution, map);
    }
}
