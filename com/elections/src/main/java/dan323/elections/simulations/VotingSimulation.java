package dan323.elections.simulations;

import java.util.Map;

@FunctionalInterface
public interface VotingSimulation {

    Map<String,Integer> getVotes();
}
