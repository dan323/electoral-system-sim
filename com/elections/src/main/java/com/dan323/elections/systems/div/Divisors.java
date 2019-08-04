package com.dan323.elections.systems.div;

import com.dan323.elections.simulations.PartyListPair;
import com.dan323.elections.systems.Testing;
import com.dan323.utils.collectors.main.CustomCollectors;

import java.util.Map;
import java.util.stream.IntStream;

/**
 * This class is a library of divisor methods of apportionment. There is a list of divisor functions, which start their names with 'divisors'
 * and a function that implements the divisor apportionment.
 *
 * @author danco
 */
public final class Divisors {

    private Divisors() {
        throw new UnsupportedOperationException();
    }

    /**
     * Solves the apportionment problem with a divisor.
     * <p>
     * Steps of the algorithm:
     * <lu>
     * <li>We stipulate a random order of the parties</>
     * <li>For each party, we compute votes/div(0), ..., votes/div(esc-1)</li>
     * <li>We order those values, and in case of tie, we order by the previous random order</li>
     * <li>The first {@param esc} in the list are the winners</li>
     * </lu>
     *
     * @param votes represents the votes given to each party involved.
     * @param esc   number of pieces to divide among the parties.
     * @param div   method that represent the list of divisors; i.e. a strictly growing function from int to double.
     * @return a partition of esc among the parties involved with respect to the votes.
     */
    public static Map<String, Integer> methodDivisor(Map<String, Long> votes, int esc, Divisor div) {
        return votes.entrySet().stream()
                .flatMap(p -> IntStream.range(0, esc)
                        .mapToObj(n -> (new PartyListPair<>(p.getKey(), p.getValue() / div.apply(n)))))
                .sorted(PartyListPair::compareTo)
                .limit(esc)
                .collect(CustomCollectors.toKeyCountingMapInt());
    }

    /**
     * @param n step
     * @return Sqrt(n * ( n + 1))
     */
    @Testing(type = Testing.Type.DIVISOR)
    public static double divisorsHill(int n) {
        double solution = 10E-3;
        if (n != 0) {
            solution = Math.sqrt(n * (n + 1.0));
        }
        return solution;
    }

    /**
     * @param n step
     * @return 2/(1/n+1/(n+1))
     */
    @Testing(type = Testing.Type.DIVISOR)
    public static double divisorsDean(int n) {
        double solution = 10E-3;
        if (n != 0) {
            solution = n * (n + 1) / (n + 0.5);
        }
        return solution;
    }

    /**
     * @param n step
     * @return n+1/2
     */
    @Testing(type = Testing.Type.DIVISOR)
    public static double divisorsSaint(int n) {
        return n + 1.0 / 2;
    }

    /**
     * @param n step
     * @return n+1/2
     */
    @Testing(type = Testing.Type.DIVISOR)
    public static double divisorsSaintMod(int n) {
        double solution = 0.7;
        if (n != 0) {
            solution = divisorsSaint(n);
        }
        return solution;
    }

    /**
     * @param n step
     * @return n+2
     */
    @Testing(type = Testing.Type.DIVISOR, toBeTested = false)
    public static double divisorsImperialii(int n) {
        return 2.0 + n;
    }

    /**
     * @param n step
     * @return 2^n
     */
    @Testing(type = Testing.Type.DIVISOR)
    public static double divisorsMacau(int n) {
        return Math.pow(2, n + 1.0);
    }

    /**
     * @param n step
     * @return (n + 1) ^ 0.9
     */
    @Testing(type = Testing.Type.DIVISOR, toBeTested = false)
    public static double divisorsEstonia(int n) {
        return Math.pow(n + 1.0, 0.9);
    }

    /**
     * @param n step
     * @return n+1
     */
    @Testing(type = Testing.Type.DIVISOR)
    public static double divisorsDHont(int n) {
        return n + 1.0;
    }

    /**
     * @param n step
     * @return n+1/3
     */
    @Testing(type = Testing.Type.DIVISOR)
    public static double divisorsDin(int n) {
        return n + 1.0 / 3;
    }

}
