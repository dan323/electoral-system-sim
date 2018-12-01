package danieldlc.elelections.div;

import danieldlc.elelections.utils.KeyCountingCollector;
import danieldlc.elelections.utils.Pair;

import java.util.Map;
import java.util.stream.IntStream;

/**
 * This class is a library of divisor methods of apportionment. There is a list of divisor functions, which start their names with 'divisors'
 * and a function that implements the divisor apportionment.
 * 
 * @author daconcep
 *
 */
public final class Divisors {
	
	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @param div: method that represent the list of divisors; i.e. a strictly growing function from int to double.
	 * @return a partition of esc among the parties involved with respect to the votes.
	 */
	public static Map<String,Integer> MethodDivisor(Map<String,Integer> votes, int esc, Divisor div){
		return votes.entrySet().parallelStream()
				.flatMap((p)->IntStream.rangeClosed(1,esc)
						.mapToObj(n->(new Pair<>(p.getKey(),p.getValue()/div.apply(n)))))
				.sequential()
				.sorted(Pair::compareTo)
				.limit(esc)
				.collect(new KeyCountingCollector<>());
	}
	
	/**
	 * @param n step
	 * @return Sqrt(n*(n+1))
	 */
	public static double divisorsHill(int n){
		if (n==0){
			return 0.001;
		}
		return Math.sqrt(n*(n+1));
	}
	
	/**
	 * @param n step
	 * @return 2/(1/n+1/(n+1))
	 */
	public static double divisorsDean(int n){
		if (n==0){
			return 0.001;
		}
		return n*(n+1)/(n+0.5);
	}
	
	/**
	 * @param n step
	 * @return n+1/2
	 */
	public static double divisorsSaint(int n){
		return n+1.0/2;
	}
	
	/**
	 * @param n step
	 * @return n+1/2
	 */
	public static double divisorsSaintMod(int n){
		if (n==0){
			return 0.7;
		}
		return n+1.0/2;
	}
	
	/**
	 * @param n step
	 * @return n+2
	 */
	public static double divisorsImperialii(int n){
		return 2+n;
	}
	
	/**
	 * @param n step
	 * @return 2^n
	 */
	public static double divisorsMacau(int n){
		return Math.pow(2, n);
	}
	
	/**
	 * @param n step
	 * @return n^0.9
	 */
	public static double divisorsEstonia(int n){
		return Math.pow(n, 0.9);
	}
	
	/**
	 * @param n step
	 * @return n+1
	 */
	public static double divisorsDHont(int n){
		return n+1;
	}
	
	/**
	 * @param n step
	 * @return n+1/3
	 */
	public static double divisorsDin(int n){
		return n+1.0/3;
	}

}
