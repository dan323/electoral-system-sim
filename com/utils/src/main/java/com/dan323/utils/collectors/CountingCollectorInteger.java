package com.dan323.utils.collectors;

import com.dan323.utils.collectors.base.AbstractCountingCollector;
import com.dan323.utils.math.structure.IntSemigroup;

import java.util.function.Function;

public class CountingCollectorInteger<S, T> extends AbstractCountingCollector<Integer, T, S> {

    public CountingCollectorInteger(Function<S, T> function) {
        super(function, IntSemigroup.INT_SEMIGROUP);
    }

}
