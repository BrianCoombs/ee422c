package assignment2;

import java.util.ArrayList;
import java.util.List;

public class History {
	List<String>guesses;
	List<String> replies;
	
	public History(){
		guesses = new ArrayList<String>();
		replies = new ArrayList<String>();
	}
	public void printHistory(){
		for(int i = 0; i < guesses.size(); i++){
			System.out.println(guesses.get(i) + "\t" + "\t" +replies.get(i));
		}
	}
	public static String histFormat(int black, int white){
		String reply = black+ "B_" + white + "W";
		return reply;
	}
	
	public void loadHist(String guess, String reply){
		guesses.add(guess);
		replies.add(reply);
	}
}
