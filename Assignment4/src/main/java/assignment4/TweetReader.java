package assignment4;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.*;
import java.lang.String;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TweetReader contains method used to return tweets from method
 * Do not change the method header
 */
public class TweetReader {



    /**
     * Find tweets written by a particular user.
     *
     * @param url
     *            url used to query a GET Request from the server
     * @return return list of tweets from the server
     *
     */
    public static List<Tweets> readTweetsFromString(String json) throws Exception
    {

        ObjectMapper mapper = new ObjectMapper();
        List<Tweets> tweetList= mapper.readValue(json, new TypeReference<List<Tweets>>(){});
        removeInvalidTweets(tweetList);
        return tweetList;
    }


    public static List<Tweets> readTweetsFromWeb(String url) throws Exception
    {

        ObjectMapper mapper = new ObjectMapper();

        List<Tweets> tweetList = new ArrayList<Tweets>();

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Chrome/60.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response);

        try {
            //Read tweets into tempTweets List and then remove invalid ones before adding into actual list for returning
            List<Tweets> tempTweets= mapper.readValue(response.toString(), new TypeReference<List<Tweets>>(){});
            removeInvalidTweets(tempTweets);
            tweetList = tempTweets;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return tweetList;
    }
    public static void removeInvalidTweets(List<Tweets> tweetsList){
        for(int i =0 ; i<tweetsList.size(); i++){
            Tweets t = tweetsList.get(i);
            if(!validTweet(t)){
                tweetsList.remove(t);
                i--;
            }
        }
    }

    public static boolean validTweet(Tweets t){
        if(!isThisDateValid(t.getDate(), "yyyy-MM-dd'T'HH:mm:ss'Z'")) {
            return false;
        }
        else if(t.getId()>(Math.pow(2, 32)) || t.getId() < 1){
            return false;
        }
        else if(!isNameValid(t.getName())){
            return false;
        }
        /* Check if null first to prevent .length being called on null string */
        else if(t.getText() == null || t.getText().length() > 140 ){
            return false;
        }

        return true;
    }
    public static boolean isNameValid(final String name){
        if(name == null){
            return false;
        }
        return name.matches("[a-zA-Z0-9_]*");
    }

    public static boolean isThisDateValid(String dateToValidate, String dateFormat){

        if(dateToValidate == null){
            return false;
        }

        try{
            Instant tweetInstant = Instant.parse(dateToValidate);
        }
        catch(DateTimeParseException e){
            return false;
        }

        return true;
    }
}
