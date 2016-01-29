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
				//System.out.println("Existing Partition:");
				//System.out.println(pair.getValue().getKey().toString() + " " +  pair.getValue().getSet().toString());
			}
		}
	}
		
	public void put(Key theKey, Partition anotherOne) {
		partitions.put(theKey, anotherOne);
	}
	
	public void clear() {
		partitions = new HashMap<Key, Partition>();
	}
	
	public Partition getBest(int wordLength, Key currentWord) {
		Partition best = new Partition(currentWord);
		int letterCount = best.getKey().letterCount();
		//System.out.println(letterCount);
		//System.out.println("Getting Best");
		//System.out.println(best.getKey().toString());
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
					int first = pair.getValue().getKey().getFirstChar();
					int second = best.getKey().getFirstChar();
					if(first > second) {
						best = pair.getValue();
					}
					else if(first == second) {
						// TODO Choose the next rightmost letter;
					}
					
				}
			}
		}
		return best;
	}


}
