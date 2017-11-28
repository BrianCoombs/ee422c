package assignment2;

import java.util.Scanner;

public class Driver {
	static Scanner in = new Scanner(System.in);

	
	public static void main(String[] args){
		
		intro();
		char Check = '0';
		while(checkStart(Check)){
			
			Game newGame;
			if(args[0].isEmpty()){
				newGame = new Game(false);
			}
			else{
				newGame = new Game("1".equals(args[0]));
			}
			if(newGame.testMode){
				System.out.println("\nGenerating secret code ...(for this example the secret code is " + newGame.code + ")\n");
			}
			else{
				System.out.println("\nGenerating secret code ...\n");
			}
			newGame.runGame();
			System.out.println("Are you ready for another game (Y/N): ");
		}
	}
	
	static void intro(){
		System.out.print("Welcome to Mastermind.  Here are the rules.\nThis is a text version of the classic board game Mastermind.\nThe computer will think of a secret code. The code consists of 4 colored pegs. The pegs MUST be one of six colors: blue, green, orange, purple, red, or yellow. A color may appear more than once in the code. You try to guess what colored pegs are in the code and what order they are in. After you make a valid guess the result (feedback) will be displayed.The result consists of a black peg for each peg you have guessed exactly correct (color and position) in your guess. For each peg in the guess that is the correct color, but is out of position, you get a white peg. For each peg, which is fully incorrect, you get no feedback.\nOnly the first letter of the color is displayed. B for Blue, R for Red, and so forth. When entering guesses you only need to enter the first character of each color as a capital letter.\nYou have 12 guesses to figure out the secret code or you lose the game. Are you ready to play? (Y/N): ");
	}
	static boolean checkStart(char check){
		boolean ret = false;
		check = '0';
		while(!(check == 'Y' || check == 'N')){
			check = in.nextLine().charAt(0);
			if(check == ('Y')){
				ret = true;
			}
			else if(check == ('N')){
				ret = false;
			}else{
				System.out.println("Please enter a valid input such as 'Y' or 'N'.");
			}
		}
		return ret;
	}

}
