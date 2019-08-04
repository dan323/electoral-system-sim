package com.dan323.elections.simulations;

import com.dan323.utils.Pair;
import com.dan323.utils.comparators.RandomComparator;

/**
 * Implementation of {@link Pair} where the key is the name of the political
 * party and its ordering is random.
 * <p>
 * This is to choose a random winner when in tie.
 *
 * @param <S>
 * @author danco
 */
public class PartyListPair<S extends Comparable<S>> extends Pair<String, S> {

    private static RandomComparator<String> randomComparator;

    /**
     * We create a new pair ({@param string},{@param s}) to be randomly comparing {@link String}.
     *
     * @param string key
     * @param s      value
     */
    public PartyListPair(String string, S s) {
        super(string, s, getOrCreateRandomComparator());
    }

    /**
     * Creates a new {@link RandomComparator} if none is already created, or returns the one created previously.
     *
     * @return the randomComparator for strings being used
     */
    private static RandomComparator<String> getOrCreateRandomComparator() {
        if (randomComparator == null) {
            randomComparator = new RandomComparator<>();
        }
        return randomComparator;
    }

}
