package hangman;

import java.util.ArrayList;
import java.util.Arrays;

public class Key {
	
	String value;
	
	int charCount;
	
	Key(int wordLength) {
		charCount = wordLength;
		char[] chars = new char[wordLength];
		Arrays.fill(chars, '-');
		value = new String(chars);
	}

	public int getFirstChar() {
		// returns first seen character.
		String one = this.value;
		for(int i = 0; i < one.length(); i++) {
			if(one.charAt(i) != '-') {
				return i;
			}
		}
		
		return 0;
		
	}
	
	public void updateValue(Partition bestPart) {
		String currValue = value;
		Key toCompare = bestPart.getKey();
		StringBuilder sb = new StringBuilder(value);
		for(int i = 0; i < toCompare.toString().length(); i++) {
			//System.out.println("sbtest");
			//System.out.println(sb.toString());
			if(value.charAt(i) == '-') {
				sb.setCharAt(i, toCompare.toString().charAt(i));
			}
		}
		value = sb.toString();
		
		
	}
	
	public void addChar(ArrayList indices, char guessed) {
		StringBuilder sb = new StringBuilder(value);
		for(Object i : indices) {
			int index = (int)i;
			sb.setCharAt(index, guessed);
		}
		value = sb.toString();
	}
	
	public boolean empty() {
		boolean ifDashes = true;
		for(int i = 0; i < value.length(); i++) {
			if(value.charAt(i) != '-') {
				ifDashes = false;
			}
		}
		return ifDashes;
	}
	
	public int letterCount() {
		int charCount = 0;
		for(int i = 0; i < value.length(); i++) {
			if(value.charAt(i) != '-') {
				charCount++;
			}
		}
		return charCount;
	}
	
	public String toString() {
		return value;
	}

	public int hashCode() {
		return value.hashCode();
	}
	
	public boolean equals(Object o) {
		if(o.getClass() == this.getClass()) {
			String str = o.toString();
			boolean equal = value.equals(str);
			return equal;
		}
		else {			
			return false;
		}
	}
}
