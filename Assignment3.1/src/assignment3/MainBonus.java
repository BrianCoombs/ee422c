package assignment3;

        import java.io.File;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.ArrayList;
        import java.util.Scanner;

        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;
        import org.jsoup.select.Elements;


public class MainBonus
{

    public static void main(String[] args) throws IOException
    {
        File input = new File("C:\\Users\\User\\IdeaProjects\\Assignment3.1\\src\\assignment3\\WebPage.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");


        Elements content = doc.getElementsByClass("panels-flexible-row panels-flexible-row-5-main-row clearfix");
        Elements para = content.select("p");

        Element actSen = para.get(0);

        File corpScraper=new File("C:\\Users\\User\\IdeaProjects\\Assignment3.1\\src\\assignment3\\CorpusScraper.txt");
        FileWriter fwrite=new FileWriter(corpScraper);
        PrintWriter writer = new PrintWriter(fwrite);
        writer.println(actSen);
        writer.close();

        Scanner input1 = new Scanner(new File("C:\\Users\\User\\IdeaProjects\\Assignment3.1\\src\\assignment3\\CorpusScraper.txt"));
        String change = input1.nextLine();
        change = change.replaceAll("<p>", "");
        change = change.replaceAll("</p>", "");
        change = change.replace(".", "");

        File corpScrap=new File("C:\\Users\\User\\IdeaProjects\\Assignment3.1\\src\\assignment3\\CorpusWebScrape.txt");
        FileWriter ffwrite=new FileWriter(corpScrap);
        PrintWriter writ = new PrintWriter(ffwrite);
        writ.println(change);
        writ.close();

        final GraphPoet nimoy = new GraphPoet(new File("C:\\Users\\User\\IdeaProjects\\Assignment3.1\\src\\assignment3\\CorpusWebScrape.txt"));
        System.out.println(nimoy.poem(new File("C:\\Users\\User\\IdeaProjects\\Assignment3.1\\src\\assignment3\\InputScraper.txt")));
    }

}

