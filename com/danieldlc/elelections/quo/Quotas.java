package danieldlc.elelections.quo;

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
public final class Quotas {

	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @param mm: method that distributes the remainder votes.
	 * @param quot: quota function of the method
	 * @return map with seats distributed
	 */
	public static Map<String,Integer> MethodQuota(Map<String,Integer> votes,int esc,Quota quot,Remainder mm){
		Iterator<String> it;
		Map<String,Double> aux=new HashMap<>();
		Map<String,Integer> res=new HashMap<>();
		it=votes.keySet().iterator();
		
		double D=quot.apply(votes,esc);
		while(it.hasNext()){
			String k=it.next();
			int R=(int)Math.floor(votes.get(k)/D);
			res.put(k, R);
			esc-=R;
			aux.put(k, votes.get(k)/D-R);
		}
		mm.apply(esc,aux,res);
		return res;
	}
	
	/**
	 * @param esc: number of pieces to divide among the parties.
	 * @param dob: remainders of the parties involved.
	 * @param mint: previous apportionment.
	 */
	public static void remaindersLargestRemainder(int esc,Map<String,Double> dob,Map<String,Integer> mint){
		Set<String> L=new HashSet<>(dob.keySet());
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
		for (String st:dob.keySet()	) {
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
		Set<String> L=new HashSet<>(dob.keySet());
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
	 * @return standard quota
	 */
	public static double quotaStandard(Map<String,Integer> votes,int esc){
		int totalV=0;
		Iterator<String> it=votes.keySet().iterator();
		for(String k: votes.keySet()){
			totalV+=votes.get(k);
		}
		return totalV/((double)esc);
	}
	
	/**
	 * @param esc: number of pieces to divide among the parties.
	 * @param votes: represents the votes given to each party involved.
	 * @return droop quota
	 */
	public static double quotaDroop(Map<String,Integer> votes,int esc){
		int totalV=0;
		for(String k: votes.keySet()){
			totalV+=votes.get(k);
		}
		return (totalV/((double)esc+1))+1;
	}
	
}
