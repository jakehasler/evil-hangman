package hangman;

import java.util.ArrayList;
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
	
	
//	public Key getRightmost(Key first, Key second) {
//		Key rightmost = new Key(5);
//		String one = first.toString();
//		String two = second.toString();
//		ArrayList oneList = new ArrayList();
//		for(int i = 0; i < one.length(); i++) {
//			if(one.charAt(i) != '-') {
//				oneList.add(i);
//			}
//		}
//		
//		ArrayList twoList = new ArrayList();
//		for(int i = 0; i < two.length(); i++) {
//			if(two.charAt(i) != '-') {
//				twoList.add(i);
//			}
//		}
//		
//		if(oneList.size() > 0 && twoList.size() > 0) {
//			int test1 = (int)oneList.get(0);
//			int test2 = (int)twoList.get(0);
//			if(test1 > test2) {
//				
//			}
//		}
//		
//		return rightmost;
//	}
	
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

	public void setSet(Set<String> newSet) {
		words = newSet;
	}
}
