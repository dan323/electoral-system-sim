package com.dan323.utils.collectors;

import com.dan323.utils.collectors.base.AbstractCountingCollector;
import com.dan323.utils.math.structure.LongSemigroup;

import java.util.function.Function;

public class CountingCollectorLong<S, T> extends AbstractCountingCollector<Long, T, S> {

    public CountingCollectorLong(Function<S, T> function) {
        super(function, LongSemigroup.LONG_SEMIGROUP);
    }

}
