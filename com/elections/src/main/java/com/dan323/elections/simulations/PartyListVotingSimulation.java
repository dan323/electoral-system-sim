package com.dan323.elections.simulations;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PartyListVotingSimulation implements VotingSimulation {

    private static final long DEFAULT_MAX_VOTES = 5000000L;
    private static final Random RANDOM = new Random();

    private long maxVotes = DEFAULT_MAX_VOTES;
    private int numOfParties;

    public PartyListVotingSimulation(int numOfParties) {
        this.numOfParties = numOfParties;
    }

    public PartyListVotingSimulation(int minParties, int maxParties) {
        setNumOfParties(minParties, maxParties);
    }

    public PartyListVotingSimulation(int numOfParties, long maxVotes) {
        this.numOfParties = numOfParties;
        this.maxVotes = maxVotes;
    }

    public PartyListVotingSimulation(int minParties, int maxParties, long maxVotes) {
        setNumOfParties(minParties, maxParties);
        this.maxVotes = maxVotes;
    }

    public void setNumOfParties(int minParties, int maxParties) {
        this.numOfParties = RANDOM.nextInt(maxParties - minParties + 1) + minParties;
    }

    public int getNumOfParties() {
        return this.numOfParties;
    }

    public void setNumOfParties(int numParties) {
        this.numOfParties = numParties;
    }

    private Map<String, Long> getVotes(int numOfParties) {

        long[] votes = RANDOM.longs(numOfParties, 0L, maxVotes)
                .toArray();

        return IntStream.range(0, numOfParties).boxed()
                .collect(Collectors.toMap(i -> "Party " + i, i -> votes[i]));
    }

    @Override
    public Map<String, Long> getVotes() {
        return getVotes(numOfParties);
    }

    public Long getMaxVotes() {
        return maxVotes;
    }
}
