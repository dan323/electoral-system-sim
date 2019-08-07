package com.dan323.utils.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * @author danco
 */
public class RandomSet<E> extends HashSet<E> implements RandomCollection<E> {

    private static final Random random = new Random();

    public RandomSet(Collection<E> collection){
        super(collection);
    }

    public RandomSet(){
        super();
    }

    @Override
    public E getRandomElement() {
        int rand = random.nextInt(size());
        Iterator<E> iterator = iterator();
        for (int i = 0; i < rand; i++) {
            iterator.next();
        }
        return iterator.next();
    }
}
