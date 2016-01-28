package hangman;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Partitions {
	
	public HashMap<Key, Partition> partitions = new HashMap<Key, Partition>();

	
	public boolean find(Key theKey) {
		boolean equals = false;
		for(Key key : partitions.keySet()) {
			if(theKey.equals(key)) {
				return true;
			}
		}
		return equals;
	}

	public void insert(Key theKey, String str) {
		Iterator it = partitions.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Key, Partition> pair = (Map.Entry)it.next();
			if(pair.getKey().equals(theKey)) {
				pair.getValue().add(str);
			}
		}
	}
		
	public void put(Key theKey, Partition anotherOne) {
		partitions.put(theKey, anotherOne);
	}
	
	public Partition getBest(int wordLength) {
		
		Key theKey = new Key(wordLength);
		Partition best = new Partition(theKey);
		Iterator it = partitions.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Key, Partition> pair = (Map.Entry)it.next();
			if(pair.getValue().getSize() > best.getSize()) {
				best = pair.getValue();
			}
			else if(pair.getValue().getSize() == best.getSize()) {
				if(pair.getValue().emptyKey()) {
					best = pair.getValue();
				}
				else {
				// TODO choose key with rightmost guessed letter
				}
			}
		}
		return best;
	}


}
