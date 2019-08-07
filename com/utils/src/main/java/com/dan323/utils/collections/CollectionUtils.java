package com.dan323.utils.collections;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author danco
 */
public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if an element of the {@link Collection} {@param collect} is in the {@link List}
     * {@param list}
     *
     * @param list    list to be check
     * @param collect collection of element to check
     * @param <T>     type of elements
     * @return true iff no element of {@param collect} is in {@param list}
     */
    public static <T> boolean findNoElementsInList(List<T> list, Collection<T> collect) {
        boolean b = true;
        for (T validCandidate : collect) {
            if (list.contains(validCandidate)) {
                b = false;
                break;
            }
        }
        return b;
    }

    public static <T> Optional<T> findFirstElement(List<T> list, Set<T> valid) {
        Optional<T> result = Optional.empty();
        for (T element : list) {
            if (valid.contains(element)) {
                result = Optional.of(element);
                break;
            }
        }
        return result;
    }

    /**
     * Get some element from the {@param collection}
     *
     * @param collection {@link Collection} to choose element from
     * @param <T>        type of the elements
     * @return some element in the collection
     */
    public static <T> T someElement(Collection<T> collection) {
        return (new RandomSet<>(collection)).getRandomElement();
    }

}
