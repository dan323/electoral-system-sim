package com.dan323.utils.math.structure;

/**
 * @author danco
 */
public final class LongSemigroup implements Semigroup<Long> {

    public static final LongSemigroup LONG_SEMIGROUP = new LongSemigroup();

    private LongSemigroup(){}

    @Override
    public Long add(Long a, Long b) {
        return a+b;
    }

    @Override
    public Long getUnit() {
        return 1L;
    }
}
