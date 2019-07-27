package com.dan323.utils;

import com.dan323.utils.comparators.RandomComparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PairTest {

    private static final Pair<String, String> pair = new Pair<>("Key", "Value", new RandomComparator<>());

    @Test
    public void pairTest() {
        Assertions.assertEquals("Key", pair.getKey());
        Assertions.assertEquals("Value", pair.getValue());
        pair.setValue("Value2");
        Assertions.assertEquals("Value2", pair.getValue());
    }

    @Test
    public void comparePairTest() {
        Pair<String, String> pair2 = new Pair<>("Key", "Valua", pair.getRandomComparator());
        Pair<String, String> pair3 = new Pair<>("Key", "Valuz", pair.getRandomComparator());
        Assertions.assertTrue(pair.compareTo(pair2) < 0);
        Assertions.assertTrue(pair.compareTo(pair3) > 0);
        Pair<String, String> pair4 = new Pair<>("Sus", "Value", pair.getRandomComparator());
        int x = pair.compareTo(pair4);
        Assertions.assertTrue(x * pair.compareTo(pair4) > 0);
    }

    @Test
    public void toStringPairTest() {
        Assertions.assertEquals("Key -> Value", pair.toString());
    }

    @Test
    public void equalsPairTest() {
        Assertions.assertEquals(pair, new Pair<>(pair.getKey(), pair.getValue(), pair.getRandomComparator()));
        Assertions.assertNotEquals(pair, new Pair<>("A", "B", pair.getRandomComparator()));
        Assertions.assertNotEquals("Testing", new Pair<>("A", "B", pair.getRandomComparator()));
        Assertions.assertNotEquals(pair, new Pair<>(pair.getKey(), "B", pair.getRandomComparator()));
        Assertions.assertNotEquals(pair, new Pair<>("A", pair.getValue(), pair.getRandomComparator()));
        class MyPair {
            public String getKey() {
                return pair.getKey();
            }
            public String getValue() {
                return pair.getValue();
            }
        }
        Assertions.assertNotEquals(pair, new MyPair());
    }

    @Test
    public void hashCodePairTest() {
        Pair<String, String> pair2 = new Pair<>(pair.getKey(), pair.getValue(), pair.getRandomComparator());
        Assertions.assertEquals(pair2.hashCode(), pair.hashCode());
    }
}
