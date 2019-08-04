package com.dan323.elections.simulations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author danco
 */
public class PartyListSimulationTest {

    @Test
    public void testSimulationNumberParties() {
        PartyListVotingSimulation partyListVotingSimulation = new PartyListVotingSimulation(8);
        Assertions.assertEquals(8, partyListVotingSimulation.getNumOfParties());
        partyListVotingSimulation.setNumOfParties(4);
        Assertions.assertEquals(4, partyListVotingSimulation.getNumOfParties());
        partyListVotingSimulation.setNumOfParties(2, 4);
        Assertions.assertTrue(2 <= partyListVotingSimulation.getNumOfParties());
        Assertions.assertTrue(4 >= partyListVotingSimulation.getNumOfParties());
        partyListVotingSimulation = new PartyListVotingSimulation(6, 8);
        Assertions.assertTrue(6 <= partyListVotingSimulation.getNumOfParties());
        Assertions.assertTrue(8 >= partyListVotingSimulation.getNumOfParties());
    }

    @Test
    public void testSimulation() {
        PartyListVotingSimulation partyListVotingSimulation = new PartyListVotingSimulation(3);
        Map<String, Long> map = partyListVotingSimulation.getVotes();
        map.forEach((st, x) -> Assertions.assertTrue(x < partyListVotingSimulation.getMaxVotes()));
    }

}
