package danieldlc.elections.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CustomCollectorsTest {
    private static final Logger LOG = Logger.getLogger("COUNTING TEST");
    private static List<String> list = new ArrayList<>();
    private static Random random = new Random();


    @Before
    public void setUp() {
        for (int i = 0; i < random.nextInt(10000) + 1000; i++) {
            list.add("var" + random.nextInt(100)+"N");
        }
        LOG.log(Level.INFO, "The list has size {0}", list.size());
    }

    @Test
    public void toMapTesting(){
        Map<String,String> sol=list.stream()
                .collect(CustomCollectors.toMap(CustomCollectorsTest::keyMapping,(s,m)->s));

        Map<String,String> test=new HashMap<>();
        list.parallelStream()
                .collect(Collectors.groupingBy(Function.identity()))
                .forEach((st,list)-> IntStream.range(0,list.size()).parallel()
                                .mapToObj(i->st+i)
                                .forEach(e->test.put(e,st)));

        test.forEach((key,value)-> Assert.assertEquals(value,sol.get(key)));
    }

    private static String keyMapping(String s, Map<String,String> map) {
        int i=0;
        while(map.keySet().contains(s+i)) {
            i++;
        }
        return s+i;
    }

}
