package com.dan323.utils.collections;

import com.dan323.utils.comparators.RandomComparator;

import java.util.Map.Entry;

/**
 * An implementation of {@link java.util.Map.Entry} where the values have an order and
 * this order is extended to the whole class. The key is immutable, but the value
 * can be mutated.
 * <p>
 * When the values are equal, but the keys are unequal, they are ordered randomly.
 * A memory is kept to satisfy the transitive condition of orders.
 *
 * @param <T> Type of keys
 * @param <S> Type of values
 */
public class Pair<T, S extends Comparable<S>> implements Entry<T, S>, Comparable<Pair<T, S>> {

    private final T key;
    private final RandomComparator<T> randomComparator;
    private S value;

    public Pair(T st, S d, RandomComparator<T> randomComparator) {
        key = st;
        value = d;
        this.randomComparator = randomComparator;
    }

    public RandomComparator<T> getRandomComparator() {
        return randomComparator;
    }

    @Override
    public T getKey() {
        return key;
    }

    @Override
    public S getValue() {
        return value;
    }

    @Override
    public S setValue(S value) {
        S dob = this.value;
        this.value = value;
        return dob;
    }

    public String toString() {
        return getKey() + " -> " + getValue();
    }

    @Override
    public int compareTo(Pair<T, S> o) {
        int result;
        if (Entry.<T, S>comparingByValue().compare(o, this) == 0) {  // Remember that compare(a,b) == 0 does not always imply that a.equals(b)
            result = randomComparator.compare(o.key, key);
        } else {
            result = Entry.<T, S>comparingByValue().compare(o, this);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Pair) && ((Pair) obj).getKey().equals(getKey()) && ((Pair) obj).getValue().equals(getValue());
    }

    @Override
    public int hashCode() {
        return 7 * key.hashCode() + 11 * value.hashCode();
    }

}
