package com.dan323.elections.systems.quo;

import com.dan323.elections.simulations.PartyListPair;
import com.dan323.elections.systems.Testing;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is a library of quota methods of apportionment. There is a list of remainder apportionment, which start their names with 'remainders'
 * and a function that implements the quota apportionment. There is also a list of functions that compute quotas from the number of
 * seats and the number of votes.
 *
 * @author daconcep
 */
public final class Quotas {

    private Quotas() {
    }

    /**
     * Computes the result of apportionment with the given {@param quot} and {@param remainder}
     * <p>
     * The algorithm assigns the quota results first,
     * then calls the remainder method to assign the rest.
     *
     * @param votes     represents the votes given to each party involved.
     * @param esc       number of pieces to divide among the parties.
     * @param remainder method that distributes the remainder votes.
     * @param quot      quota function of the method
     * @return map with seats distributed
     */
    public static <T> Map<T, Integer> methodQuota(Map<T, Long> votes, int esc, Quota<T, Long> quot, Remainder<T> remainder) {
        Map<T, Double> remainders = new HashMap<>();
        Map<T, Integer> result = new HashMap<>();

        double d = quot.apply(votes, esc);
        votes.entrySet().stream()
                .map(e -> Map.entry(e.getKey(),
                        Map.entry((int) Math.floor(e.getValue() / d), e.getValue() / d - (int) Math.floor(e.getValue() / d))))
                .forEach(e -> {
                    result.put(e.getKey(), e.getValue().getKey());
                    remainders.put(e.getKey(), e.getValue().getValue());
                });

        remainder.apply(esc - result.values().stream().mapToInt(z -> z).sum(), remainders, result);
        return result;
    }

    /**
     * @param esc:  number of pieces to divide among the parties.
     * @param dob:  remainders of the parties involved.
     * @param mint: previous apportionment.
     */
    @Testing(type = Testing.Type.REMAINDER)
    public static void remaindersLargestRemainder(int esc, Map<String, Double> dob, Map<String, Integer> mint) {

        dob.entrySet().stream()
                .map(e -> new PartyListPair<>(e.getKey(), e.getValue()))
                .sorted()
                .limit(esc)
                .forEach(e -> mint.put(e.getKey(), mint.get(e.getKey()) + 1));

    }


    /**
     * @param esc  number of pieces to divide among the parties.
     * @param dob  remainders of the parties involved.
     * @param mint previous apportionment.
     */
    @Testing(type = Testing.Type.REMAINDER)
    public static void remaindersLargestRemainderRelative(int esc, Map<String, Double> dob, Map<String, Integer> mint) {
        dob = dob.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() / mint.get(e.getKey())));

        remaindersLargestRemainder(esc, dob, mint);
    }


    /**
     * @param esc  number of pieces to divide among the parties.
     * @param dob  remainders of the parties involved.
     * @param mint previous apportionment.
     */
    @Testing(type = Testing.Type.REMAINDER)
    public static void remaindersWinnerAll(int esc, Map<String, Double> dob, Map<String, Integer> mint) {
        dob.entrySet().stream()
                .map(e -> new PartyListPair<>(e.getKey(), e.getValue()))
                .sorted()
                .limit(1)
                .forEach(e -> mint.put(e.getKey(), mint.get(e.getKey()) + esc));
    }

    /**
     * @param esc   number of pieces to divide among the parties.
     * @param votes represents the votes given to each party involved.
     * @return standard quota
     */
    @Testing(type = Testing.Type.QUOTA)
    public static double quotaStandard(Map<String, Long> votes, int esc) {
        long totalV = votes.values().stream().mapToLong(z -> z).sum();
        return totalV / ((double) esc);
    }

    /**
     * @param esc   number of pieces to divide among the parties.
     * @param votes represents the votes given to each party involved.
     * @return droop quota
     */
    @Testing(type = Testing.Type.QUOTA)
    public static double quotaDroop(Map<String, Long> votes, int esc) {
        long totalV = votes.values().stream().mapToLong(z -> z).sum();
        return (totalV / ((double) esc + 1)) + 1;
    }

}
