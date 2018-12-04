package danieldlc.elections.div;

import danieldlc.elections.utils.CustomCollectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * This test is for divisor methods
 */
public class DivisorsTest {

    private static final Random random = new Random();
    private static final int numberOfRepetitions = 10;
    private static final int numOfMaxVotes = 10000;
    private static final Logger LOG = Logger.getLogger("TEST DIVISORS");
    private static List<Divisor> methods;

    /**
     * List all the methods to be tested
     */
    @Before
    public void init() {
        methods = List.of(Divisors::divisorsImperialii,Divisors::divisorsDHont, Divisors::divisorsSaint,
                Divisors::divisorsHill, Divisors::divisorsDean,Divisors::divisorsEstonia,
                Divisors::divisorsDin, Divisors::divisorsSaintMod,Divisors::divisorsMacau);
    }

    /**
     * Procedure followed:
     * 1. Random number of seats from 10 to 100
     * 2. Random number of parties from 2 to numberSeats/5
     * 3. Random number of votes to each party up to numberOfMaxVotes
     * 4. Apportionment by a method
     * 5. Assert apportionment result
     * 6. All repeated numberOfMethods, for each method
     */
    @Test
    public void methodDivisorAllTest(){
        for (Divisor divisorUsed:methods) {
            LOG.log(Level.INFO, "Divisor method := 1->{0}", divisorUsed.apply(1));
            for (int j = 0; j < numberOfRepetitions; j++) {
                oneRandomTest(divisorUsed);
            }
        }
    }

    private void oneRandomTest(Divisor divisorUsed) {
        int numOfSeats = random.nextInt(90) + 10;
        int numOfParties = (numOfSeats / 5 - 2 > 0 ? random.nextInt(numOfSeats / 5 - 2) : 0) + 2;

        Map<String, Integer> votes = getVotes(numOfParties);
        Map<String, Integer> solution = Divisors.methodDivisor(votes, numOfSeats, divisorUsed);

        LOG.log(Level.INFO, "Number of seats := {0}", numOfSeats);
        LOG.log(Level.INFO, "Number of parties := {0}", numOfParties);
        LOG.log(Level.INFO, "Votes := {0}", votes);
        LOG.log(Level.INFO, "Apportionment := {0}", solution);
        checkAssertions(divisorUsed, votes, solution);
    }

    private void checkAssertions(Divisor divisorUsed, Map<String, Integer> votes, Map<String, Integer> solution) {
        double modifiedDivisor = (nextDivisor(votes, solution, divisorUsed) + previousDivisor(votes, solution, divisorUsed)) / 2;
        LOG.log(Level.INFO, "Modified divisor := {0}", modifiedDivisor);
        for (Map.Entry<String, Integer> party : solution.entrySet()) {
            double partition = votes.get(party.getKey()) / modifiedDivisor;
            int i=0;
            while(divisorUsed.apply(i)<partition && !(divisorUsed.apply(i)-partition>-0.0001)){
                i++;
            }
            Assert.assertEquals(i, (int) party.getValue());
        }
    }

    private double nextDivisor(Map<String, Integer> votes, Map<String, Integer> solution, Divisor divisorUsed) {
        double maximum = 0;
        for (String party : votes.keySet()) {
            double step = votes.get(party) / divisorUsed.apply(solution.getOrDefault(party, 0));

            if (maximum < step) {
                maximum = step;
            }
        }
        return maximum;
    }

    private double previousDivisor(Map<String, Integer> votes, Map<String, Integer> solution, Divisor divisorUsed) {
        double minimum = 0;
        for (String party : votes.keySet()) {
            if (solution.containsKey(party)) {
                double step = votes.get(party) / divisorUsed.apply(solution.get(party) - 1);

                if (minimum == 0 || minimum > step) {
                    minimum = step;
                }
            }
        }
        return minimum;
    }

    /**
     * Creates a random set of votes for the parties
     *
     * @param numOfParties size of the apportionment
     * @return a map with the simulation of votes
     */
    private Map<String, Integer> getVotes(int numOfParties) {

        //Repeting votes depend on a random choice to be solved, so that case has to be eliminated
        //TODO - different test needed for that. Check that the sum of seats is correct and the parites in tie differ in at most one seat

        return IntStream.range(0, numOfParties)
                .mapToObj(i -> "Party " + i)
                .collect(CustomCollectors.toMap((i, m) -> i, (i, map) ->
                        getRandomNonRepetingInteger(map)));
    }

    private Integer getRandomNonRepetingInteger(Map<String, Integer> map) {
        int k = random.nextInt(numOfMaxVotes);
        while (map.containsValue(k)) {
            k = random.nextInt(numOfMaxVotes);
        }
        return k;
    }

}
