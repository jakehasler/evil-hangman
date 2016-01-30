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
	public int numGuesses; //( >0 && < 26)
	private static Set<String> wordSet;
	// Alphabetical Set - Used so far
	private static Set guessedLetters = new TreeSet();
	public Partition bestPart;
	public char currChar;
	public Key theWord = new Key(wordLength);
	public Partitions partitions = new Partitions();
	public int currGuessFreq = 0;
	public boolean gameWon = false;
	
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
		//System.out.println(wordSet);
		System.out.println("You have " + numGuesses + " guesses left.");		
		if(this.numGuesses <= origGuesses) {
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
			
			//System.out.println(indices);
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
				partitions.insert(testKey, str, numGuesses);
				//System.out.println("Inserted into partition:");
				//System.out.println(testKey + str);
			}
		}
		int prevCount = theWord.letterCount();
		bestPart = partitions.getBest(wordLength, theWord);
		//prevCount += bestPart.getKey().letterCount();
		this.currGuessFreq = bestPart.getKey().letterCount();
		//System.out.println("Best Partition:");
		//System.out.println(bestPart.getKey().toString() + bestPart.getSet().toString());
		// TODO Resolve how to properly determine if a guess has ben made.
		if(bestPart.getKey().letterCount() > 0) {
			guessMade = true;
			this.theWord.updateValue(bestPart);
		}
		else if(prevCount == bestPart.getKey().letterCount()) {
			//guessMade = true;
			//this.theWord.updateValue(bestPart);
		}
		// TODO be sure that the Key is getting assigned of the partition
		this.wordSet = bestPart.getSet();
		//System.out.println("After Best");
		//System.out.println(this.theWord.toString());
		return guessMade;
	}
	
	@Override
	public void startGame(File dictionary, int wordLength) {
		this.partitions = new Partitions();
		this.wordLength = wordLength;
		this.theWord = new Key(wordLength);
		this.buildWordSet(dictionary);
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		partitions.clear();
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
			if(this.theWord.letterCount() == wordLength) {
				System.out.println("Congrats! You won!");
				System.out.println("The word is: " + theWord.toString() + "!");
				this.gameWon = true;
				
			}
			else if (guessedLetters.size() == numGuesses) {
				
			}
			else if (this.currGuessFreq > 0) {
				System.out.println("Yes, there is " + this.currGuessFreq + " " + guess);			
			}
		}
		else {
			System.out.println("Sorry, there are no " + guess + "'s");
			this.decrementGuesses();
		}
		return wordSet;
	}
	
	public void buildWordSet(File file) {
		this.wordSet = new HashSet();
		this.guessedLetters = new TreeSet();
		
		
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
		File file = new File(dictionary);
		game.startGame(file, Integer.parseInt(args[1]));
		game.numGuesses = Integer.parseInt(args[2]);
		int origGuesses = Integer.parseInt(args[2]);		
		game.theWord = new Key(game.wordLength);
		
		
		System.out.println("Welcome to Hangman!");
		game.printMan();
		
		while(game.numGuesses > 0 && game.gameWon == false) {
			
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
		if(game.numGuesses == 0 && !game.gameWon) {
			System.out.println("You Lose!");
			System.out.println("The word was: " + game.wordSet.iterator().next());
		}
	}
}
