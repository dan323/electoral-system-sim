package com.dan323.elections.systems.stv;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author danco
 */
public interface STVTransfer<T> {

    void transfer(T candidate, long transfer, Map<T, Double> votes, Map<List<T>, Long> originalVotes, Set<T> hopefuls);

}
