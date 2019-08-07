package com.dan323.utils.collections;

import java.util.Collection;

/**
 * @author danco
 */
public final class RandomHeap<E> extends RandomSet<E> {

    public RandomHeap(Collection<E> collection) {
        super();
        addAll(collection);
    }

    @Override
    public E getRandomElement() {
        E element = super.getRandomElement();
        remove(element);
        return element;
    }

}
