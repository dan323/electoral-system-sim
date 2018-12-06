package danieldlc.elections.systems;

import danieldlc.elections.systems.div.Divisors;
import danieldlc.elections.systems.quo.Quotas;

import java.util.Map;

public final class NamedMethods {

    private NamedMethods(){
    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return
     */
    public static Map<String,Integer> methodWebster(Map<String,Integer> votes, int esc) {
        return Divisors.methodDivisor(votes,esc,Divisors::divisorsSaint);
    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return
     */
    public static Map<String,Integer> methodJefferson(Map<String,Integer> votes, int esc) {
        return Divisors.methodDivisor(votes,esc,Divisors::divisorsDHont);
    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return
     * @throws Exception
     */
    public static Map<String,Integer> methodDanes(Map<String,Integer> votes, int esc) {
        return Divisors.methodDivisor(votes,esc,Divisors::divisorsDin);
    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return
     */
    public static Map<String,Integer> methodHamilton(Map<String,Integer> votes,int esc) {

        return Quotas.methodQuota(votes,esc,Quotas::quotaStandard,Quotas::remaindersLargestRemainder);

    }

    /**
     * @param votes: represents the votes given to each party involved.
     * @param esc: number of pieces to divide among the parties.
     * @return
     */
    public static Map<String,Integer> methodHamiltonRel(Map<String,Integer> votes, int esc) {

        return Quotas.methodQuota(votes,esc,Quotas::quotaStandard,Quotas::remaindersLargestRemainderRelative);

    }
}
