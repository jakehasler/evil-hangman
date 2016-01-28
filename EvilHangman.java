package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;





public class EvilHangman implements IEvilHangmanGame {
	
	private int wordLength; //( >= 1)
	private int numGuesses; //( >0 && < 26)
	private static Set<String> wordSet = new HashSet<String>();
	// Alphabetical Set - Used so far
	private static Set guessedLetters = new TreeSet();
	public String partialWord = "";
	public Partition bestPart;
	public char currChar;
	public Key theWord = new Key(wordLength);
	public Partitions partitions = new Partitions();
	
	public void printMan() {
		System.out.println(" _______");
		System.out.println("|       |");
		System.out.println("|       O");
		System.out.println("|      /|\\");
		System.out.println("|       | ");
		System.out.println("|      /  ");
		System.out.println("|");
		System.out.println("|_____");
	}
	
	public void userOutput(int origGuesses) {
		System.out.println();
		System.out.println("You have " + numGuesses + " guesses left.");		
		if(this.numGuesses < origGuesses) {
			System.out.print("Guessed Letters: ");
			for(Object c : guessedLetters) {
				char letter = (char)c;
				System.out.print(letter + " ");
			}
			System.out.println();
			// Print out Key
			System.out.println("Current Word: " + this.theWord.toString());
		}
	}

	public void addGuessed(char guess) {
		guessedLetters.add(guess);
	}
	
	public void decrementGuesses() {
		numGuesses--;
	}
	
	public boolean isAlpha(String val) {
	    return val.matches("[a-zA-Z]+");
	}
	
	public boolean checkWordSet(char guess) {

		boolean guessMade = false;
		for(String str : wordSet) {	
			Key testKey = new Key(this.wordLength);
			ArrayList indices = new ArrayList();
			
			for(int i = 0; i < str.length(); i++) {
				if(str.charAt(i) == guess) {
					indices.add(i);
				}
			}
			// adding the values to the key.
			testKey.addChar(indices, guess);
			
			if(!partitions.find(testKey)) {
				// if the key doesn't exist
				// create a new partition and initialize it with the key
				// add it to the HashMap
				Partition anotherOne = new Partition(testKey);
				anotherOne.add(str);
				partitions.put(testKey, anotherOne);	
			}
			else {
				// Key does exist
				// add the string to the partition with matching key
				partitions.insert(testKey, str);
			}
		}
		
		bestPart = partitions.getBest(wordLength);
		if(!bestPart.getKey().empty()) {
			guessMade = true;
		}
		this.wordSet = bestPart.getSet();
		this.partialWord = bestPart.getKey().toString();
		return guessMade;
	}
	
	@Override
	public void startGame(File dictionary, int wordLength) {
		
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {

		boolean guessMade = false;
		int freq = 0;
		// Check if guess has already been made
		for (Object thisGuess : this.guessedLetters) {
			char theGuess = (char)thisGuess;
			if(theGuess == guess) {
				throw new GuessAlreadyMadeException();
			}
		}
		
		this.addGuessed(guess);
		
		guessMade = this.checkWordSet(guess);
		
		if(guessMade) {
			System.out.println("Yes, there is " + freq + " " + guess);
			//this.theWord.updateValue(guess);
		}
		else {
			System.out.println("Sorry, there are no " + guess + "'s");
			this.decrementGuesses();
		}
		return wordSet;
	}
	
	public void buildWordSet(String dictionary) {
		
		File file = new File(dictionary);
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);	
		Scanner sc = new Scanner(br);
		sc.useDelimiter("[^a-zA-Z]+");
		while(sc.hasNext()) {
			String next = sc.next();
			if(next.length() == this.wordLength) {
				wordSet.add(next);
			}
		}
	}
	
	public static void main(String[] args) {
		EvilHangman game = new EvilHangman();
		String dictionary = args[0];
		game.wordLength = Integer.parseInt(args[1]);
		game.numGuesses = Integer.parseInt(args[2]);
		int origGuesses = Integer.parseInt(args[2]);		
		game.theWord = new Key(game.wordLength);
		
		game.buildWordSet(dictionary);
		
		System.out.println("Welcome to Hangman!");
		game.printMan();
		
		while(game.numGuesses > 0) {
			
			game.userOutput(origGuesses);
			
			System.out.print("What letter do you want to guess?: ");
			Scanner input = new Scanner(System.in);
			String in = input.next();
			
			if(in.length() == 1 && game.isAlpha(in)) {
				char guess = in.charAt(0);
				try {
					// Making Guess Here.
					game.makeGuess(guess);
				} catch (GuessAlreadyMadeException e) {
					System.out.println("You already used that letter.");
					e.printStackTrace();
				}
			}
			else {
				System.out.print("Invalid input");
				System.out.println();
			}
		}
	}
}
