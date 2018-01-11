package assignment4;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.time.Instant;
import java.util.*;

public class Main {
    final static String URLEndpoint = "http://kevinstwitterclient2.azurewebsites.net/api/products";

    public static void main(String[] args) throws Exception {
        // Testing: Tweets from JSON in text file
        TweetReader reader = new TweetReader();
        String json = new Scanner(new File("C:/Users/User/IdeaProjects/Assignment4/src/json_11_19_2017_3_29_pm.txt")).useDelimiter("\\A").next();
        List<Tweets> tweetsList = reader.readTweetsFromString(json);

        System.out.println("All valid Tweets:");
        System.out.println(tweetsList);

        // Printing Mentions
        /*
        System.out.println("\n*** MENTIONS BY TWEET ***\n");
        for (Tweets tweet : tweetsList) {
            List<String> mentions q= SocialNetwork.findMentionsInTweets(tweet);
            if (mentions.size() > 0) System.out.print(tweet.getText() + " : ");
            for (String mention : mentions) {
                System.out.print(mention + " ");
            }
            if (mentions.size() > 0) System.out.println();
        }
        System.out.println("\n*** END OF MENTIONS BY TWEET ***\n");
*/

        // Problem 2:
        System.out.println("\n*** BEGIN PROBLEM 2 ***\n");
        // Filter Tweets by Username
        System.out.print("Tweets by kevinyee: ");
        List<Tweets> filteredUser = Filter.writtenBy(tweetsList,"kevinyee");
        System.out.println(filteredUser);

        // Filter by Timespan
        System.out.print("Tweets from 2017-11-11T00:00:00Z to 2017-11-12T12:00:00Z: ");
        Instant testStart = Instant.parse("2017-11-11T00:00:00Z");
        Instant testEnd = Instant.parse("2017-11-12T12:00:00Z");
        Timespan timespan = new Timespan(testStart,testEnd);
        List<Tweets> filteredTimeSpan = Filter.inTimespan(tweetsList,timespan);
        System.out.println(filteredTimeSpan);

        //Filter by words containing
        System.out.print("Tweets that Contain \"good\" and \"luck\": ");
        List<Tweets> filteredWords = Filter.containing(tweetsList, Arrays.asList("good","luck"));
        System.out.println(filteredWords);

        System.out.println("\n*** END OF PROBLEM 2 ***\n");

        // Problem 3:
        System.out.println("\n*** BEGIN PROBLEM 3 ***\n");

        // SocialNetwork.buildSocialNetwork(tweetsList); // Particular to my implementation

        System.out.println("Top 5 Most Followers:");
        for (String username : SocialNetwork.findKMostFollower(tweetsList, 5)) {
            System.out.println(username);
        }

        System.out.println("\nAll Max Cliques:");
        for (Set<String> clique : SocialNetwork.findCliques(tweetsList)) {
            for (String username : clique) {
                System.out.print(username + " ");
            }
            System.out.println();
        }


        System.out.println("\n*** END OF PROBLEM 3 ***\n");

        System.out.println("\n*** START OF PROBLEM 4 SERVER TWEETS***\n");

        System.out.println("\n*** START OF PROBLEM 4.1 RETURNING***\n");
        // Problem 1: Returning Tweets from Server

        tweetsList = reader.readTweetsFromWeb(URLEndpoint);
        System.out.println(tweetsList);

        System.out.println("\n*** START OF PROBLEM 4.2 FILTER BY USERNAME***\n");
        // Problem 2:
        // Filter Tweets by Username
        Filter filter = new Filter();
        filteredUser = filter.writtenBy(tweetsList,"kevinyee");
        System.out.println(filteredUser);

        System.out.println("\n*** START OF PROBLEM 4.3 FILTER BY TIMESPAN***\n");
        // Filter by Timespan
        testStart = Instant.parse("2017-11-11T00:00:00Z");
        testEnd = Instant.parse("2017-11-12T12:00:00Z");
        timespan = new Timespan(testStart,testEnd);
        filteredTimeSpan = filter.inTimespan(tweetsList,timespan);
        System.out.println(filteredTimeSpan);

        System.out.println("\n*** START OF PROBLEM 4.3 FILTER BY CONTAINED WORDS***\n");

        //Filter by words containinng
        filteredWords = filter.containing(tweetsList,Arrays.asList("good","luck"));
        System.out.println(filteredWords);

        System.out.println("\n*** START OF PROBLEM 4.4 KMOSTFOLLOWERS***\n");
        for (String username : SocialNetwork.findKMostFollower(tweetsList, 5)) {
            System.out.println(username);
        }

        System.out.println("\n*** START OF PROBLEM 4.5 CLIQUES***\n");
        for (Set<String> list : SocialNetwork.findCliques(tweetsList)) {
            System.out.println(list);
        }



    }




}