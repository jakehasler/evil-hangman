package hangman;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Partitions {
	
	public TreeMap<String, Partition> partitions = new TreeMap<String, Partition>();
	
	
	public boolean find(Key theKey) {
		boolean equals = false;
		for(String key : partitions.keySet()) {
			if(theKey.toString().equals(key)) {
				return true;
			}
		}
		return equals;
	}

	public void insert(Key theKey, String str, int numGuesses) {
		Iterator it = partitions.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Partition> pair = (Map.Entry)it.next();
			if(pair.getKey().toString().equals(theKey.toString())) {
				pair.getValue().add(str);
				if(numGuesses < 5) {
					//System.out.println("Existing Partition:");
					//System.out.println(pair.getValue().getKey().toString() + " " +  pair.getValue().getSet().toString());
				}
			}
		}
	}
		
	public void put(Key theKey, Partition anotherOne) {
		this.partitions.put(theKey.toString(), anotherOne);
	}
	
	public void clear() {
		partitions = new TreeMap<String, Partition>();
	}
	
	public Partition getBest(int wordLength, Key currentWord) {
		Partition best = new Partition(currentWord);
		int letterCount = best.getKey().letterCount();
		//System.out.println(letterCount);
		//System.out.println("Getting Best");
		//System.out.println(best.getKey().toString());
		Iterator it = partitions.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Partition> pair = (Map.Entry)it.next();
			if(pair.getValue().getSize() > best.getSize()) {
				best = pair.getValue();
			}
			else if(pair.getValue().getSize() == best.getSize()) {
				if(pair.getValue().emptyKey()) {
					best = pair.getValue();
					
				}
				else {
					if(best.emptyKey()) {
						return best;
					}
					else if(pair.getValue().getKey().letterCount() < best.getKey().letterCount()) {
						best = pair.getValue();
					}
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
