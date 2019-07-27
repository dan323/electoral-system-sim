package com.dan323.utils.math.structure;

/**
 * @author danco
 */
public interface Semigroup<T> {

    T add(T a, T b);
    T getUnit();

}
