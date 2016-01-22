package hangman;

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
	
	public String toString() {
		return value;
	}

}
