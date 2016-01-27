package hangman;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Partition {
	
	private Key word;
	private Set words = new TreeSet();;
	
	Partition(Key theKey) {
		// Constructor
		word = theKey;
	}
	
	public Key getKey() {
		return word;
	}
	
	public boolean emptyKey() {
		return word.empty();
	}
	
	public void add(String word) {
		words.add(word);
	}
	
	public int getSize() {
		return words.size();
	}

	public Set<String> getSet() {
		return words;
	}
}
