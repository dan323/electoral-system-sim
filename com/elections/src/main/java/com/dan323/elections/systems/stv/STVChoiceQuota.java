package com.dan323.elections.systems.stv;

import com.dan323.elections.systems.quo.Quota;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * @author danco
 */
public class STVChoiceQuota<T> implements STVChoice<T> {

    private Quota<T, Double> quota;

    public STVChoiceQuota(Quota<T, Double> quota) {
        this.quota = quota;
    }

    @Override
    public Optional<T> choice(Map<T, Double> votes, int esc) {
        double q = quota.apply(votes, esc);
        return votes.entrySet().stream()
                .filter(entry -> entry.getValue() > q)
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
