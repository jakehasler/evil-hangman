package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
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
	public char currChar;
	public Key theWord = new Key(wordLength);
	
	public void printMan() {
		System.out.println(" _______");
		System.out.println("|       |");
		System.out.println("|       O");
		System.out.println("|      /| ");
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
	
	public int checkWordSet(char guess) {
		boolean exists = true;
		int runningValue = this.wordLength;
		for(String str : wordSet) {
			// Check if char exists in the string itself
			int count = 0;
			for(int i = 0; i < str.length(); i++) {
				if(str.charAt(i) == guess) {
					// Keeps count at how many times the letter actually appears. 
					count++;
				}
			}
			if(count < runningValue) count = runningValue;
		}
		
		return runningValue;
		
	}
	
	@Override
	public void startGame(File dictionary, int wordLength) {
		
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		
		this.addGuessed(guess);
		boolean guessMade = true;
		int freq = 0;
		
		this.checkWordSet(guess);
		
		if(guessMade) {
			
			System.out.println("Yes, there is " + freq + " " + guess);
			this.theWord.updateValue(guess);
						
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
