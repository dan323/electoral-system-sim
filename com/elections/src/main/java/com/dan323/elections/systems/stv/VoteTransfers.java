package com.dan323.elections.systems.stv;

import com.dan323.elections.systems.Testing;
import com.dan323.utils.collectors.main.CustomCollectors;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public final class VoteTransfers {

    private static final Random RANDOM = new Random();

    private VoteTransfers() {
    }

    @Testing(type = Testing.Type.TRANSFER)
    public static <T> void transferPure(T candidate, double transfer, Map<T, Double> votes, Map<List<T>, Map<T, Long>> originalVotesDistribution, Set<T> hopefuls) {

        double total = votes.get(candidate);
        Map<List<T>, Long> originalVotes = originalVotesDistribution.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().values().stream().mapToLong(x -> x).sum()));

        Map<T, Double> map = originalVotes.entrySet().stream()
                .filter(listLongEntry -> listLongEntry.getKey().contains(candidate))
                .filter(listLongEntry -> checkNoValidCandidateBefore(listLongEntry.getKey(), listLongEntry.getKey().indexOf(candidate), hopefuls))
                .collect(CustomCollectors.toMap(
                        (listLongEntry, longMap) -> nextValid(listLongEntry.getKey(), listLongEntry.getKey().indexOf(candidate), hopefuls),
                        (listLongEntry, longMap) -> longMap.get(nextValid(listLongEntry.getKey(), listLongEntry.getKey().indexOf(candidate), hopefuls)) + percentage(listLongEntry.getValue(), transfer, total)
                ));

        votes.forEach((a, b) -> votes.put(a, map.getOrDefault(a, 0.0)));
    }

    private static <T> T nextValid(List<T> key, int indexOf, Set<T> validCandidates) {
        T valid = null;
        int i = 1;
        do {
            if (indexOf + i < key.size() && validCandidates.contains(key.get(indexOf + i))) {
                valid = key.get(indexOf + i);
            }
            i++;
        } while (valid == null && indexOf + i < key.size());
        return valid;
    }

    private static <T> boolean checkIfValidCandidateAfter(List<T> key, int candidateIndex, Set<T> validCandidates) {
        for (T validCandidate : validCandidates) {
            if (key.contains(validCandidate)) {
                int index = key.indexOf(validCandidate);
                if (index > candidateIndex) {
                    return true;
                }
            }
        }
        return false;
    }


    @Testing(type = Testing.Type.TRANSFER)
    public static <T> void transferRandom(T candidate, double transfer, Map<T, Double> votes, Map<List<T>, Map<T, Long>> originalVotes, Set<T> hopefuls) {


    }

    private static <T> boolean checkNoValidCandidateBefore(List<T> key, int indexOf, Set<T> collect) {
        for (T validCandidate : collect) {
            if (key.contains(validCandidate)) {
                int index = key.indexOf(validCandidate);
                if (index < indexOf) {
                    return false;
                }
            }
        }
        return true;
    }

    private static double percentage(long value, double transfer, double total) {
        return (value / total) * transfer;
    }

}
