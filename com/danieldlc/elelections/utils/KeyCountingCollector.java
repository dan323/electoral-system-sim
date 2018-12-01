package danieldlc.elelections.utils;

import java.util.Map.Entry;

/**
 * This Collector is used to count the number of appearances of each key in a Stream<Entry<T,?>>.
 * @author daconcep
 *
 * @param <T> Type of the keys
 */
public class KeyCountingCollector<T> extends CountingCollector<Entry<T,?>,T> {

	public KeyCountingCollector(){
		super(Entry::getKey);
	}

}