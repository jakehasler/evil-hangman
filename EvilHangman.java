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
	
	private static int wordLength; //( >= 1)
	private static int numGuesses; //( >0 && < 26)
	private static Set<String> wordSet = new HashSet<String>();
	// Alphabetical Set - Used so far
	private Set guessedLetters = new TreeSet();
	private String partialWord = "";
	private char currChar;


	@Override
	public void startGame(File dictionary, int wordLength) {
		
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {

		return null;
	}
	
	public static void buildWordSet(String dictionary) {
		
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
			if(next.length() == wordLength) {
				wordSet.add(next);
			}
		}
	}
	
	public static void main(String[] args) {
		String dictionary = args[0];
		numGuesses = Integer.parseInt(args[2]);
		wordLength = Integer.parseInt(args[1]);
		
		buildWordSet(dictionary);
		
		System.out.println("Welcome to Hangman!");
		//System.out.print("Would you Like to play?(Y/N): ");
		//Scanner input = new Scanner(System.in);
		//String in = input.next();
		//System.out.println(in);
		
		while(numGuesses > 0) {
			System.out.println("You have " + numGuesses + " guesses left.");
			System.out.print("What letter do you want to guess?: ");
			Scanner input1 = new Scanner(System.in);
			String in1 = input1.next();
			System.out.println(in1);
			
			if(in1.length() == 1) {
				
			}
			else {
				System.out.print("Sorry, please try again: ");
			}
			
			numGuesses--;
		}
		
		
	}

}
