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
	
	public void updateValue(char letter) {
		
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
