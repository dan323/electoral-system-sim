package com.dan323.utils.math.structure;

/**
 * @author danco
 */
public final class IntSemigroup implements Semigroup<Integer> {

    public static final IntSemigroup INT_SEMIGROUP = new IntSemigroup();

    private IntSemigroup(){
    }

    @Override
    public Integer add(Integer a, Integer b) {
        return a+b;
    }

    @Override
    public Integer getUnit() {
        return 1;
    }
}
