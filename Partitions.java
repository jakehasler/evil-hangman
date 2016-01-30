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
				//if(numGuesses < 5) {
					//System.out.println("Existing Partition:");
					//System.out.println(pair.getValue().getKey().toString() + " " +  pair.getValue().getSet().toString());
					//System.out.println("Partition Size: " + pair.getValue().getSize());
				//}
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
		//System.out.println(best.getSize());
		//System.out.println("Getting Best");
		//System.out.println(best.getKey().toString());
		Iterator it = partitions.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Partition> compare = (Map.Entry)it.next();
			boolean hasBeenSet = false;
			//System.out.println("Compare: " + compare.getValue().getSize());
			//System.out.println("Best: " + best.getSize());
			
			if(compare.getValue().getSize() > best.getSize()) {
				best = compare.getValue();
				hasBeenSet = true;
			}
			else if(compare.getValue().getSize() == best.getSize()) {
				if(compare.getValue().getKey().letterCount() < best.getKey().letterCount()) {
					best = compare.getValue();
				}
				else if(compare.getValue().getKey().letterCount() == best.getKey().letterCount()) {
					if(best.getKey().toString().compareTo(compare.getValue().getKey().toString()) > 0) {
						best = compare.getValue();
					}
				}
//				else if(compare.getValue().emptyKey()) {
//						best = compare.getValue();	
//					}
//					else {
//						
//						 if(best.emptyKey()) {
//							System.out.println("Returning: " + best.getSet().toString());
//							return best;
//						}
//						int first = compare.getValue().getKey().getFirstChar();
//						int second = best.getKey().getFirstChar();
//						if(first > second) {
//							best = compare.getValue();
//						}
//						else if(first == second) {
//							// TODO Choose the next rightmost letter;
//						}
//						
//					}
				}	
			}
			
		return best;
	}


}
