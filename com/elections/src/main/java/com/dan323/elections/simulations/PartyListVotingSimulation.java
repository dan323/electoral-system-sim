package com.dan323.elections.simulations;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Simulations about voting for political parties
 *
 * @author danco
 */
public class PartyListVotingSimulation implements VotingSimulation<String> {

    private static final long DEFAULT_MAX_VOTES = 5000000L;
    private static final Random RANDOM = new Random();

    private long maxVotes = DEFAULT_MAX_VOTES;
    private int numOfParties;

    /**
     * Set a simulation by number of parties
     *
     * @param numOfParties number of parties in the simulation
     */
    public PartyListVotingSimulation(int numOfParties) {
        this.numOfParties = numOfParties;
    }

    /**
     * Set a simulation by a random number of parties
     *
     * @param minParties minimum number of parties
     * @param maxParties maximum number of parties
     */
    public PartyListVotingSimulation(int minParties, int maxParties) {
        setNumOfParties(minParties, maxParties);
    }

    /**
     * Set a simulation by number of parties and a limit of number of votes
     *
     * @param numOfParties number of parties
     * @param maxVotes     number of maximum votes per party
     */
    public PartyListVotingSimulation(int numOfParties, long maxVotes) {
        this.numOfParties = numOfParties;
        this.maxVotes = maxVotes;
    }

    /**
     * Set a simulation by a random number of parties and a limit of number of votes
     *
     * @param minParties minimum number of parties
     * @param maxParties maximum number of parties
     * @param maxVotes   number of maximum votes per party
     */
    public PartyListVotingSimulation(int minParties, int maxParties, long maxVotes) {
        setNumOfParties(minParties, maxParties);
        this.maxVotes = maxVotes;
    }

    /**
     * Set a random number of parties
     *
     * @param minParties minimum number of parties
     * @param maxParties maximum number of parties
     */
    public void setNumOfParties(int minParties, int maxParties) {
        this.numOfParties = RANDOM.nextInt(maxParties - minParties + 1) + minParties;
    }

    /**
     * @return number of parties in the simulation
     */
    public int getNumOfParties() {
        return this.numOfParties;
    }

    /**
     * Set the number of parties
     *
     * @param numParties number of parties
     */
    public void setNumOfParties(int numParties) {
        this.numOfParties = numParties;
    }

    /**
     * Generate a voting map
     *
     * @param numOfParties number of parties
     * @return a randomly generated map of votes
     */
    private Map<String, Long> getVotes(int numOfParties) {

        long[] votes = RANDOM.longs(numOfParties, 0L, maxVotes)
                .toArray();

        return IntStream.range(0, numOfParties).boxed()
                .collect(Collectors.toMap(i -> "Party " + i, i -> votes[i]));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Generates a map of votes for the number of parties indicated by {@link #getNumOfParties()}
     * where each party has no more than {@link #getMaxVotes()}.
     *
     * @return a randomly generated map of votes
     */
    @Override
    public Map<String, Long> getVotes() {
        return getVotes(numOfParties);
    }

    /**
     * @return maximum number of votes allowed per party
     */
    public long getMaxVotes() {
        return maxVotes;
    }
}
