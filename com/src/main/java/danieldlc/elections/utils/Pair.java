package danieldlc.elections.utils;

import java.util.Map.Entry;

public class Pair<T> implements Entry<T, Double>, Comparable<Pair<T>>{
	
	private final T cad;
	private double num;

	public Pair(T st,double d) {
		cad=st;
		num=d;
	}

	@Override
	public T getKey() {
		return cad;
	}

	@Override
	public Double getValue() {
		return num;
	}

	@Override
	public Double setValue(Double value) {
		double dob=this.num;
		num=value;
		return dob;
	}

	public String toString(){
		return getKey().toString()+" -> "+getValue();
	}

	@Override
	public int compareTo(Pair<T> o) {
		return Double.compare(o.num, num);
	}

}
