package dan232.elections;

import dan232.elections.div.Divisors;
import dan232.elections.quo.Quotas;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/**
 * Class of execution
 * 
 * @author daconcep
 *
 */
@SuppressWarnings("ALL")
public final class Main {
	
	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @return
	 */
	public static Map<String,Integer> Webster(Map<String,Integer> votes, int esc) {
		return Divisors.MethodDivisor(votes,esc,Divisors::divisorsSaint);
	}
	
	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @return
	 */
	public static Map<String,Integer> Jefferson(Map<String,Integer> votes, int esc) {
		return Divisors.MethodDivisor(votes,esc,Divisors::divisorsDHont);
	}
	
	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Integer> Danes(Map<String,Integer> votes, int esc) {
		return Divisors.MethodDivisor(votes,esc,Divisors::divisorsDin);
	}
	
	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @return
	 */
	public static Map<String,Integer> Hamilton(Map<String,Integer> votes,int esc) {
		
		return Quotas.MethodQuota(votes,esc,Quotas::quotaStandard,Quotas::remaindersLargestRemainder);
		
	}	
	
	/**
	 * @param votes: represents the votes given to each party involved.
	 * @param esc: number of pieces to divide among the parties.
	 * @return
	 */
	@SuppressWarnings("WeakerAccess")
	public static Map<String,Integer> HamiltonRel(Map<String,Integer> votes, int esc) {
		
		return Quotas.MethodQuota(votes,esc,Quotas::quotaStandard,Quotas::remaindersLargestRemainderRelative);
		
	}
	
	public static void main(String [] args){
		int esc=352;
		Map<String,Integer> pob=new HashMap<>();
		Map<String,Integer> res;
		pob.put("Alava",321932);
		pob.put("Albacete",395007);
		pob.put("Alicante",1842963);
		pob.put("Almeria",690851);
		pob.put("Asturias",1041754);
		pob.put("Avila",165786);
		pob.put("Badajoz",686032);
		pob.put("Barcelona",5427322);
		pob.put("Burgos",366900);
		pob.put("Caceres",405560);
		pob.put("Cadiz", 1248625);
		pob.put("Cantabria", 588656);
		pob.put("Castellon", 574906);
		pob.put("Ciudad Real", 514543);
		pob.put("Cordoba", 795718);
		pob.put("La Coruna",1128807);
		pob.put("Cuenca", 206653);
		pob.put("Gerona",740537);
		pob.put("Granada",919329);
		pob.put("Guadalajara",254388);
		pob.put("Guipuzcoa",707298);
		pob.put("Huelva",522216);
		pob.put("Huesca",221942);
		pob.put("Is. Baleares",1124744);
		pob.put("Jaen",652253);
		pob.put("Leon",480209);
		pob.put("Lerida",430655);
		pob.put("Lugo",338873);
		pob.put("Madrid",6377364);
		pob.put("Malaga",1632949);
		pob.put("Murcia",1463249);
		pob.put("Navarra",636638);
		pob.put("Orense",318739);
		pob.put("Palencia",165782);
		pob.put("Las Palmas",1106779);
		pob.put("Pontevedra",948496);
		pob.put("La Rioja",319002);
		pob.put("Salamanca",342045);
		pob.put("Tenerife",1021868);
		pob.put("Segovia",158085);
		pob.put("Sevilla",1939625);
		pob.put("Soria",92221);
		pob.put("Tarragona",792619);
		pob.put("Teruel",137838);
		pob.put("Toledo",699136);
		pob.put("Valencia",2521681);
		pob.put("Valladoliz",527508);
		pob.put("Vizcaya",1151905);
		pob.put("Zamora",184238);
		pob.put("Zaragoza",960111);
		pob.put("Ceuta", 84726);
		pob.put("Melilla", 84621);
		try {
			res=HamiltonRel(pob,esc);
			System.out.println(res);
			res=Hamilton(pob,esc);
			System.out.println(res);
			res=Danes(pob,esc);
			System.out.println(res);
			Iterator<Integer> it=res.values().iterator();
			int sum=0;
			while(it.hasNext()){
				sum+=it.next();
			}
			System.out.println(sum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
