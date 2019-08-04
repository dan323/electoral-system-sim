package com.dan323.elections.systems.stv;

import java.util.Map;
import java.util.Optional;

/**
 * @author danco
 */
@FunctionalInterface
public interface STVChoice<T> {

    Optional<T> choice(Map<T, Double> votes, int esc);

}
