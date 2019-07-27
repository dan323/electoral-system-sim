package com.dan323.elections.systems.quo;

import com.dan323.elections.ElectionMethodsTestReflectionUtils;
import com.dan323.elections.simulations.PartyListVotingSimulation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuotasTesting {

    private static final Random random = new Random();
    private static final int NUMBER_OF_REPETITIONS = 10;
    private static final Logger LOG = Logger.getLogger("TEST QUOTA METHODS");
    private static List<Quota<String>> quotas;
    private static List<Remainder<String>> remainders;

    @BeforeAll
    public static void init() {
        quotas = ElectionMethodsTestReflectionUtils.getQuotaMethods(List.of(Quotas.class));
        remainders = ElectionMethodsTestReflectionUtils.getRemainderMethods(List.of(Quotas.class));
    }

    private void oneRandomTest(Quota<String> quotaUsed, Remainder<String> remainderUsed) {
        int numOfSeats = random.nextInt(90) + 10;
        PartyListVotingSimulation plvsim = new PartyListVotingSimulation(2, numOfSeats / 5);

        Map<String, Long> votes = plvsim.getVotes();
        Map<String, Integer> solution = Quotas.methodQuota(votes, numOfSeats, quotaUsed, remainderUsed);

        LOG.log(Level.INFO, "Number of seats := {0}", numOfSeats);
        LOG.log(Level.INFO, "Number of parties := {0}", plvsim.getNumOfParties());
        LOG.log(Level.INFO, "Votes := {0}", votes);
        LOG.log(Level.INFO, "Apportionment := {0}", solution);
        checkAssertions(votes, solution, quotaUsed, remainderUsed, numOfSeats);
    }

    private void checkAssertions(Map<String, Long> votes, Map<String, Integer> solution, Quota<String> quotaUsed, Remainder remainderUsed, int numOfSeats) {
        Assertions.assertEquals(numOfSeats, solution.values().stream().mapToInt(z -> z).sum());
        double quota = quotaUsed.apply(votes, numOfSeats);
        votes.forEach((st, vote) -> Assertions.assertTrue(Math.floor(vote / quota) <= solution.get(st)));
    }

    @Test
    public void methodQuotaAllTest() {
        for (Quota<String> quotaUsed : quotas) {
            for (Remainder<String> remainderUsed : remainders) {
                for (int j = 0; j < NUMBER_OF_REPETITIONS; j++) {
                    oneRandomTest(quotaUsed, remainderUsed);
                }
            }
        }
    }
}
