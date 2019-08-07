package com.dan323.elections.systems.stv;

import com.dan323.elections.systems.Testing;
import com.dan323.utils.collections.CollectionUtils;
import com.dan323.utils.collectors.main.CustomCollectors;

import java.util.*;
import java.util.stream.Collectors;

public final class VoteTransfers {

    private VoteTransfers() {
        throw new UnsupportedOperationException();
    }

    @Testing(type = Testing.Type.TRANSFER)
    public static <T> void transferPure(T candidate, double transfer, Map<T, Double> votes, Map<List<T>, Map<T, Long>> originalVotesDistribution, Set<T> hopefuls) {

        double total = votes.get(candidate);
        //In this transfer, we are only interested in the votes by list
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

    @Testing(type = Testing.Type.TRANSFER)
    public static <T> void transferRandom(T candidate, double transfer, Map<T, Double> votes, Map<List<T>, Map<T, Long>> originalVotes, Set<T> hopefuls) {

        Map<List<T>, Long> candidateVotes = originalVotes.entrySet().stream()
                .map(entry -> (Map.Entry<List<T>, Long>) new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().getOrDefault(candidate, 0L)))
                .filter(entry -> entry.getValue() != 0L)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        long longTransfer = Double.doubleToLongBits(transfer);
        for (long l = 0; l < longTransfer; l++) {
            List<T> choice = CollectionUtils.someElement(candidateVotes.keySet());
            Optional<T> nextValid = getNextValidCandidate(choice, choice.indexOf(candidate), hopefuls);
            if (nextValid.isPresent()) {
                T transferTo = nextValid.get();

                Map<T, Long> votesDistribution = originalVotes.get(choice);
                votesDistribution.put(candidate, votesDistribution.get(candidate) - 1);
                votesDistribution.put(transferTo, votesDistribution.get(transferTo) + 1);
                originalVotes.put(choice, votesDistribution);

                votes.put(transferTo, votes.get(transferTo) + 1);
            }
            candidateVotes.put(choice, candidateVotes.get(choice) - 1);
        }


    }

    private static <T> Optional<T> getNextValidCandidate(List<T> choice, int indexOf, Set<T> hopefuls) {
        return CollectionUtils.findFirstElement(choice.subList(indexOf, choice.size()), hopefuls);
    }

    private static <T> boolean checkNoValidCandidateBefore(List<T> key, int indexOf, Set<T> collect) {
        return CollectionUtils.findNoElementsInList(key.subList(indexOf, key.size()), collect);
    }

    private static double percentage(long value, double transfer, double total) {
        return (value / total) * transfer;
    }

}
