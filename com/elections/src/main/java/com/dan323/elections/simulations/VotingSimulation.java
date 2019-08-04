package com.dan323.elections.simulations;

import java.util.Map;

/**
 * Interface with the main class to generate a voting simulation
 *
 * @author danco
 */
public interface VotingSimulation<T> {

    /**
     * Get the result of the simulation
     *
     * @return a map with a voting simulation
     */
    Map<T, Long> getVotes();
}
