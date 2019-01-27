package danieldlc.elections.simulations;

import danieldlc.utils.Pair;
import danieldlc.utils.comparators.RandomComparator;

/**
 * Implementation of {@link danieldlc.utils.Pair} where the key is the name of the political
 * party and its ordering is random.
 *
 * This is to choose a random winner when in tie.
 * @param <S>
 */
public class PartyListPair<S extends Comparable<S>> extends Pair<String,S> {

    private static RandomComparator<String> randomComparator;

    public PartyListPair(String st, S d) {
        super(st, d,getOrCreateRandomComparator());
    }

    public static RandomComparator<String> getOrCreateRandomComparator() {
        if (randomComparator==null){
            randomComparator = new RandomComparator<>();
        }
        return randomComparator;
    }

}
