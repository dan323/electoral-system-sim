package danieldlc.elections.quo;

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

	private Quotas(){
	}

	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @param mm: method that distributes the remainder votes.
	 * @param quot: quota function of the method
	 * @return map with seats distributed
	 */
	public static Map<String,Integer> methodQuota(Map<String,Integer> votes, int esc, Quota quot, Remainder mm){
		Iterator<String> it;
		Map<String,Double> aux=new HashMap<>();
		Map<String,Integer> res=new HashMap<>();
		it=votes.keySet().iterator();
		
		double d=quot.apply(votes,esc);
		while(it.hasNext()){
			String k=it.next();
			int r=(int)Math.floor(votes.get(k)/d);
			res.put(k, r);
			esc-=r;
			aux.put(k, votes.get(k)/d-r);
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
		Set<String> l=new HashSet<>(dob.keySet());
		while (esc>0){
			Iterator<String> d=l.iterator();
			String x=l.iterator().next();
			while (d.hasNext()){
				String s=d.next();
				Double dd=dob.get(s);
				if (dd>dob.get(x)){
					x=s;
				}
			}
			mint.put(x, mint.get(x)+1);
			l.remove(x);
			esc--;
		}
	}
	
	
	/**
	 * @param esc: number of pieces to divide among the parties.
	 * @param dob: remainders of the parties involved.
	 * @param mint: previous apportionment.
	 */
	public static void remaindersLargestRemainderRelative(int esc,Map<String,Double> dob,Map<String,Integer> mint){
		for (Map.Entry<String,Double> st:dob.entrySet()	) {
			dob.put(st.getKey(), st.getValue()/mint.get(st.getKey()));
		}
		remaindersLargestRemainder(esc,dob,mint);
	}

	
	/**
	 * @param esc: number of pieces to divide among the parties.
	 * @param dob: remainders of the parties involved.
	 * @param mint: previous apportionment.
	 */
	public static void remaindersWinnerAll(int esc,Map<String,Double> dob,Map<String,Integer> mint){
		Set<String> l=new HashSet<>(dob.keySet());
		Iterator<String> d=l.iterator();
		String x=l.iterator().next();
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
		for(Map.Entry<String,Integer> k: votes.entrySet()){
			totalV+=k.getValue();
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
		for(Map.Entry<String,Integer> k: votes.entrySet()){
			totalV+=k.getValue();
		}
		return (totalV/((double)esc+1))+1;
	}
	
}
