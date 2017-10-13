package com.dan232.elections;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class is a library of quota methods of apportionment. There is a list of remainder apportionment, which start their names with 'remainders'
 * and a function that implements the quota apportionment. There is also a list of functions that compute quotas from the number of
 * seats and the number of votes.
 * 
 * @author daconcep
 *
 */
public final class Quota {

	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @param mm: method that distributes the remainder votes.
	 * @param quot: quota function of the method
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Integer> MethodQuota(Map<String,Integer> votes,int esc,Method quot,Method mm) throws Exception{
		Iterator<String> it=votes.keySet().iterator();
		Map<String,Double> aux=new HashMap<String,Double>();
		Map<String,Integer> res=new HashMap<String,Integer>();
		it=votes.keySet().iterator();
		
		Object[] ob2 =new Object[2];
		ob2[0]=esc;
		ob2[1]=votes;
		double D=(Double) quot.invoke(null, ob2);
		while(it.hasNext()){
			String k=it.next();
			int R=(int)Math.floor(votes.get(k)/D);
			res.put(k, R);
			esc-=R;
			aux.put(k, votes.get(k)/D-R);
		}
		Object[] ob =new Object[3];
		ob[0]=esc;
		ob[1]=aux;
		ob[2]=res;
		mm.invoke(null, ob);
		return res;
	}
	
	/**
	 * @param esc: number of pieces to divide among the parties.
	 * @param dob: remainders of the parties involved.
	 * @param mint: previous apportionment.
	 */
	public static void remaindersLargestRemainder(int esc,Map<String,Double> dob,Map<String,Integer> mint){
		Set<String> L=new HashSet<String>(dob.keySet());
		while (esc>0){
			Iterator<String> d=L.iterator();
			String x=L.iterator().next();
			while (d.hasNext()){
				String s=d.next();
				Double dd=dob.get(s);
				if (dd>dob.get(x)){
					x=s;
				}
			}
			mint.put(x, mint.get(x)+1);
			L.remove(x);
			esc--;
		}
	}
	
	
	/**
	 * @param esc: number of pieces to divide among the parties.
	 * @param dob: remainders of the parties involved.
	 * @param mint: previous apportionment.
	 */
	public static void remaindersLargestRemainderRelative(int esc,Map<String,Double> dob,Map<String,Integer> mint){
		Iterator<String> it=dob.keySet().iterator();
		while(it.hasNext()){
			String st=it.next();
			dob.put(st, dob.get(st)/mint.get(st));
		}
		remaindersLargestRemainder(esc,dob,mint);
	}

	
	/**
	 * @param esc: number of pieces to divide among the parties.
	 * @param dob: remainders of the parties involved.
	 * @param mint: previous apportionment.
	 */
	public static void remaindersWinnerAll(int esc,Map<String,Double> dob,Map<String,Integer> mint){
		Set<String> L=new HashSet<String>(dob.keySet());
		Iterator<String> d=L.iterator();
		String x=L.iterator().next();
		while (d.hasNext()){
			String s=d.next();
			Double dd=dob.get(s);
			if (dd>dob.get(x)){
				x=s;
			}
		}
		mint.put(x, mint.get(x)+esc);
	}
	
	/**
	 * @param esc: number of pieces to divide among the parties.
	 * @param votes: represents the votes given to each party involved.
	 * @return
	 */
	public static double quotaStandar(int esc, Map<String,Integer> votes){
		int totalV=0;
		Iterator<String> it=votes.keySet().iterator();
		while(it.hasNext()){
			String k=it.next();
			totalV+=votes.get(k);
		}
		return totalV/((double)esc);
	}
	
	/**
	 * @param esc: number of pieces to divide among the parties.
	 * @param votes: represents the votes given to each party involved.
	 * @return
	 */
	public static double quotaDroop(int esc, Map<String,Integer> votes){
		int totalV=0;
		Iterator<String> it=votes.keySet().iterator();
		while(it.hasNext()){
			String k=it.next();
			totalV+=votes.get(k);
		}
		return (totalV/((double)esc+1))+1;
	}
	
}
