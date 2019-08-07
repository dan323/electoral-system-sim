package com.dan323.utils.collections;

import java.util.Collection;

/**
 * @author danco
 */
public interface RandomCollection<E> extends Collection<E> {

    E getRandomElement();

}
