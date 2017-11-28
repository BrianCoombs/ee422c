package assignment2;

public class Pegs {
	int black = 0;
	int white = 0;
	String guessed;
	String coded;

	public Pegs(String guess, String code) {
		char[] tempCode = code.toCharArray();
		char[] tempGuess = guess.toCharArray();
		guessed = guess;
		coded = code;
		for (int i = 0; i < tempCode.length; i++) {
			if(tempCode[i]!='-'&& tempCode[i]==tempGuess[i]) {
				black++;
				tempCode[i]='-';
				tempGuess[i]='-';
			}
		}
		for(int j=0; j<tempCode.length; j++) {
			for(int k=0; k<tempGuess.length; k++) {
				if(tempCode[j]!='-'&& tempCode[j]==tempGuess[k]) {
					white++;
					tempCode[j]='-';
					tempGuess[k]='-';
				}
			}
		}
	}

	public boolean checkWin() {
		if (black == GameConfiguration.pegNumber) {
			return true;
		}
		return false;
	}

	public void printResult() {
		System.out.println();
		System.out.print(guessed + "-> Result: ");
		if (black > 0 && white == 0) {
			System.out.println(black + " black pegs ");
		}
		if (black > 0) {
			System.out.print(black + " black pegs ");
		}
		if (white > 0) {
			System.out.println(white + " white pegs ");
		}
		if (black == 0 && white == 0) {
			System.out.println("No pegs");
		}
		System.out.println();
	}
}
