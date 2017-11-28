package assignment2;

public class Game {
	boolean testMode = false; 
	String code;
	History hist;

	public Game(boolean mode){
		testMode = mode;
		code = SecretCodeGenerator.getInstance().getNewSecretCode();
		hist = new History();
	}
	
	public void runGame(){
		int numGuesses = GameConfiguration.guessNumber;
		String input = "";
		String reply = "";
		while(numGuesses > 0){
			if(!input.isEmpty()){
				hist.loadHist(input, reply);
			}
			if(numGuesses == 1){
				System.out.print("You have 1 guess left.\n");
			} else{
				System.out.print("You have " + numGuesses + " guesses left.\n");
			}
			printNextGuess();
			input = Driver.in.nextLine();
			int result;
			
			while((result = checkinput(input)) != 1){
				if(result == 0){
					System.out.print("   -> INVALID GUESS\n\n");
					printNextGuess();
					input = Driver.in.nextLine();
				}
				if(result == -1){
					printNextGuess();
					input = Driver.in.nextLine();
				}
			}
			Pegs p = new Pegs(input, code);
			if(p.checkWin()){
				numGuesses = 0;
			}
			p.printResult();
			reply = History.histFormat(p.black, p.white);
			numGuesses--;
		}
		if(numGuesses == 0){
			System.out.println("Sorry you are out of guesses");
		}
		else if(numGuesses == -1){
			System.out.println("Congratulations! You win!");
		}
		
	}
	
	public int checkinput(String input){
		if(input.equals("HISTORY")){
			hist.printHistory();
			return -1;
		}
		else if(allofStringAreCorrectChars(input, GameConfiguration.colors) && input.length() == GameConfiguration.pegNumber){
			return 1;
		}
		else if(input.isEmpty()){
			return 0;
		}
		else{
			return 0;
		}
	}
	
	public static void printNextGuess(){
		System.out.print("What is your next guess?\nType in the characters for your guess and press enter.\nEnter guess: ");
	}
	
	public boolean allofStringAreCorrectChars(String inputStr, String[] chars)
	{
		int numCorrectColors = 0;
	    for(int i =0; i < inputStr.length(); i++)
	    {
	    	for(int j=0; j < chars.length; j++){
	    		if(chars[j].charAt(0) == inputStr.charAt(i))
		        {
		            numCorrectColors++;
		        }
	    	}  
	    }
	    if(numCorrectColors == GameConfiguration.pegNumber){
	    	return true;
	    }
	    return false;
	    
	}
	

}

