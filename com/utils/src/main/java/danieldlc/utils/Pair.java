package danieldlc.utils;

import java.util.Map.Entry;

/**
 * An implementation of {@link java.util.Map.Entry} where the values have an order and
 * this order is extended to the whole class.
 * @param <T> Type of keys
 * @param <S> Type of values
 */
public class Pair<T,S extends Comparable<S>> implements Entry<T, S>, Comparable<Pair<T,S>>{
	
	private final T cad;
	private S num;

	public Pair(T st,S d) {
		cad=st;
		num=d;
	}

	@Override
	public T getKey() {
		return cad;
	}

	@Override
	public S getValue() {
		return num;
	}

	@Override
	public S setValue(S value) {
		S dob=this.num;
		num=value;
		return dob;
	}

	public String toString(){
		return getKey()+" -> "+getValue();
	}

	@Override
	public int compareTo(Pair<T,S> o) {
		return o.num.compareTo(num);
	}

	@Override
	public boolean equals(Object obj){
		return (obj instanceof Pair) && ((Pair)obj).getKey().equals(getKey()) && ((Pair)obj).getValue().equals(getValue());
	}

	@Override
	public int hashCode(){
		return 10*cad.hashCode()+11*num.hashCode();
	}

}
