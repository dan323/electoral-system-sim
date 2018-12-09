package danieldlc.elections.simulations;

import danieldlc.utils.Pair;
import danieldlc.utils.comparators.RandomComparator;

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
