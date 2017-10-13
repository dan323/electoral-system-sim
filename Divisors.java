package com.dan232.elections;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	 * @param mm: method that represent the list of divisors; i.e. a strictly growing function from int to double.
	 * @return a partition of esc among the parties involved with respect to the votes.
	 * @throws Exception in case the method is not a divisors method.
	 */
	public static Map<String,Integer> MethodDivisor(Map<String,Integer> votes, int esc, Method mm) throws Exception{
		if(!mm.getName().startsWith("divisors")){
			if (mm.getParameterCount()!=1){
				if (!mm.getParameterTypes()[0].equals(int.class)){
					if(!mm.getReturnType().equals(double.class)){
						throw new Exception("The method is not a divisors method.");
					}
				}
			}
		}
		Map<String,Double> aux=new HashMap<String,Double>();
		Map<String,Integer> res=new HashMap<String,Integer>();
		Iterator<String> it=votes.keySet().iterator();
		while(it.hasNext()){
			String k=it.next();
			try {
				aux.put(k, votes.get(k)/((Double)(mm.invoke(null,0))));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			res.put(k, 0);
		}
		while (esc>0){
			it=votes.keySet().iterator();
			double m=0;
			String M="";
			while(it.hasNext()){
				String st=it.next();
				if (aux.get(st)>m){
					m=aux.get(st);
					M=st;
				}
				else if (aux.get(st)==m){
					if (votes.get(st)>votes.get(M)){
						m=aux.get(st);
						M=st;
					}
				}
			}
			res.put(M, res.get(M)+1);
			esc--;
			aux.put(M,votes.get(M)/((Double)(mm.invoke(null,res.get(M)))));
		}
		return res;
	}
	
	/**
	 * @param n
	 * @return
	 */
	public static double divisorsHill(int n){
		if (n==0){
			return 0.001;
		}
		return Math.sqrt(n*(n+1));
	}
	
	/**
	 * @param n
	 * @return
	 */
	public static double divisorsDean(int n){
		if (n==0){
			return 0.001;
		}
		return n*(n+1)/(n+0.5);
	}
	
	/**
	 * @param n
	 * @return
	 */
	public static double divisorsSaint(int n){
		return n+1/2;
	}
	
	/**
	 * @param n
	 * @return
	 */
	public static double divisorsSaintMod(int n){
		if (n==0){
			return 0.7;
		}
		return n+1/2;
	}
	
	/**
	 * @param n
	 * @return
	 */
	public static double divisorsImperialii(int n){
		return 2+n;
	}
	
	/**
	 * @param n
	 * @return
	 */
	public static double divisorsMacau(int n){
		return Math.pow(2, n);
	}
	
	/**
	 * @param n
	 * @return
	 */
	public static double divisorsEstonia(int n){
		return Math.pow(n, 0.9);
	}
	
	/**
	 * @param n
	 * @return
	 */
	public static double divisorsDHont(int n){
		return n+1;
	}
	
	/**
	 * @param n
	 * @return
	 */
	public static double divisorsDin(int n){
		return n+1/3;
	}

}
