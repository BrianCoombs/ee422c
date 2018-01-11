package assignment3;

public class Node {
    private String word;
    private int value;

    //Constructor
    public Node(String word, int value)
    {
        this.word = word;
        this.value = value;
    }
    public String toString()
    {
        return String.valueOf(this.value);
    }
    public String getWord()
    {
        return word;
    }
    public int getValue()
    {
        return value;
    }
    public void setWord(String word)
    {
        this.word = word;
    }
    public void setValue(int value)
    {
        this.value = value;
    }


}

