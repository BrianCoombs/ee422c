
/*
Brian Coombs
bjc2975
11/1/2017
Thomaz
Ben = TA
 */


package assignment3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Scanner;





public class GraphPoet {


	private Map <String , ArrayList<Node>> graph = new HashMap<String, ArrayList<Node>>();

    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */

    public GraphPoet(File corpus) throws IOException {
    	//Use scanner to read words into ArrayList
		Scanner in = new Scanner(corpus);
		ArrayList<String>words = new ArrayList();
		while(in.hasNext())
		{
			words.add(in.next());
		}
		fillMap(words);
		in.close();
	}


	public void fillMap(ArrayList<String> words){
    	//Add first node so don't have to worry about going i-1
		ArrayList<Node> firstArrayList = new ArrayList();
		graph.put(words.get(0), firstArrayList);
		//If new word create next node, if not new add to current
		for(int i = 1; i<words.size()-1; i++){
			String curWord = words.get(i);
			if (graph.containsKey(curWord)) {
				//Check if visited before by prior Node, if so add
				ArrayList<Node> var = graph.get(words.get(i-1));
				boolean alreadyNode = false;
				for(Node n:var){
					if(n.getWord().equals(curWord)){ n.setValue(n.getValue()+1);
						alreadyNode = true;
					}
				}
				//If not so, create a new Node with val of 1
				if(!alreadyNode) {
					addNewPointerNode(curWord, words.get(i - 1));
				}
			}
			else{
				//Adds a new pointer node to the previous word pointing to the new word
				addNewPointerNode(curWord, words.get(i-1));
				//Adds the new word to the graph along with an empty ArrayList of nodes
				ArrayList<Node> newArrayList = new ArrayList();
				graph.put(curWord, newArrayList);
			}

		}
	}
	public void addNewPointerNode(String curWord, String priorWord){
		Node newNode = new Node(curWord, 1);
		//Adds the nodes pointer to the key behind it, as long as it isn't the first key
		addNodeToArrayList(newNode, priorWord);
		//words.get(i-1) is word behind curWord
	}

	public void addNodeToArrayList(Node n, String word){
		ArrayList<Node> var = graph.get(word);
		var.add(n);
		graph.put(word, var);
	}

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
	public String poem(File input) throws IOException
	{
		Scanner in = new Scanner(input);
		ArrayList<String>origPoem = new ArrayList();
		while(in.hasNext())
		{
			origPoem.add(in.next());
		}
		//Initialize string output of poem with first word,
		//required to test next word
		String newPoem = "";
		newPoem += origPoem.get(0);
		newPoem += " ";
		//Iterate through and check for bridge words
		for(int i = 0; i < origPoem.size() - 1; i++)
		{
			String bridgeWord = findBridgeWord(origPoem.get(i), origPoem.get(i + 1));
			if(!bridgeWord.equals("null"))
			{
				newPoem += bridgeWord;
				newPoem += " ";
			}
			newPoem += origPoem.get(i + 1);
			newPoem += " ";
		}
		in.close();
		return newPoem;
	}

	public String findBridgeWord(String priorWord, String afterWord)
	{
		String bridgeWord = "null";
		int maxWeight = 0;
		if(graph.containsKey(priorWord))
		{
			ArrayList<Node>listBridges = graph.get(priorWord);
			for(int i = 0; i < listBridges.size(); i++)
			{
				int valOfBridge = listBridges.get(i).getValue();
				ArrayList<Node>listAfterWords = graph.get(listBridges.get(i).getWord());
				for(int j = 0; j < listAfterWords.size(); j++)
				{
					//If the chosen bridge has a word after it that fits the poem
					//and it has the highest value of the words that have the after word then it is the bridge
					if(listAfterWords.get(j).getWord().equals(afterWord) && listAfterWords.get(j).getValue() + valOfBridge > maxWeight)
					{
						bridgeWord = listBridges.get(i).getWord();
						maxWeight = listAfterWords.get(j).getValue() + valOfBridge;
					}
				}
			}
		}
		return bridgeWord;
	}

}


