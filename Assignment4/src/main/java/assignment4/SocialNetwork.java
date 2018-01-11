package assignment4;

import java.util.*;
import java.lang.*;

/**
 * Social Network consists of methods that filter users matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Get K most followed Users.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @param k
     *            integer of most popular followers to return
     * @return the set of usernames who are most mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getName()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like ethomaz@utexas.edu does NOT
     *         contain a mention of the username.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static List<String> findKMostFollower(List<Tweets> tweets, int k) {
        List<String> mostFollowers = new ArrayList<String>();
        //Loads hashmap with all data
        HashMap<String, Integer> followers = userMentionsHash(tweets);

        if(k>followers.size()) {
            k = followers.size();
        }
        addKMostPopularFromHashToList(followers, mostFollowers, k);
        return mostFollowers;
    }

    public static HashMap<String, Integer> userMentionsHash(List<Tweets> tweets){
        //Loads names of everyone into hashmap
        HashMap<String, Integer> followers = new HashMap<String, Integer>();
        for(Tweets t: tweets){
            String text = t.getText().toLowerCase();
            for(int i = 0; i<text.length(); i++ ){
                //Look for a username when @ sign is preceded by start of String or a char that is not a-z, A-Z or 0-9
                if(text.charAt(i) == '@' && (i == 0 || !(String.valueOf(text.charAt(i-1))).matches("[a-zA-Z0-9_]*"))){
                    //Save the index of the @ sign
                    int startIndexOfUsername = i;
                    //Get past the @ sign and then check each letter to see if they're valid until they're not
                    i++;
                    //While i is not at the end of the String or has not become a non-alphanumeric, then continue counting
                    while((i != text.length()) && (Character.isDigit(text.charAt(i)) || Character.isLetter(text.charAt(i))|| text.charAt(i) == '_')){
                        i++;
                    }
                    //Create a username to search the Hashmap for
                    String username = text.substring(startIndexOfUsername + 1, i);
                    //Since followers already contains all the usernames, check if username that was generated is in it,
                    //and if it is (meaning it is valid) then add 1 to it's follower count
                    if(followers.containsKey(username)){
                        followers.put(username, (followers.get(username).intValue() + 1));
                    } else{
                        followers.put(username, 1);
                    }
                }
            }

        }
        return followers;
    }

    //Put the most popular people inside the mostFollowers list
    public static void addKMostPopularFromHashToList(HashMap<String, Integer> followers, List<String> mostFollowers, int k){
        for(int j = 0; j<k; j++){
            int max =0;
            String popularUser = "";

            for(Iterator<Map.Entry<String, Integer>> it = followers.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Integer> entry = it.next();
                if (entry.getValue() >= max) {
                    max = entry.getValue();
                    popularUser = entry.getKey();
                }
            }
            followers.remove(popularUser);
            mostFollowers.add(popularUser);
        }
    }

    /**
     * Find all cliques in the social network.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     *
     * @return list of set of all cliques in the graph
     */


    public static List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> cliques = new ArrayList<Set<String>>();
        HashSet<String> userList=loadUsernames(tweets);
        for(String user: userList){
            Set<String> newClique= findClique(tweets,userList,user);
            if(contained(newClique,cliques)==false && (newClique.size() != 1)){
                cliques.add(newClique);
            }
        }

        //Delete doubled cliques, ones that include elements of a larger clique
        for(int i=0; i<cliques.size(); i++){
            for(int j=i+1; j<cliques.size(); j++){
                if((cliques.get(i)).containsAll(cliques.get(j))){
                    cliques.remove(j);
                }
            }
        }

        //Delete doubled cliques from other direction
        for(int i=(cliques.size()-1); i>0; i--){
            for(int j=i-1; j>0; j--){
                if((cliques.get(i)).containsAll(cliques.get(j))){
                    cliques.remove(j);
                }
            }
        }

        return cliques;
    }


    public static Set<String> findClique(List<Tweets> tweets, HashSet<String> users, String username){
        //2 sets for cliques so multiple users
        Set<String> mentionedByUser=new HashSet<String>();
        Set<String> clique=new HashSet<String>();
        username=username.toLowerCase();
        clique.add(username);

        //Check if every current clique member mentions another member, if so add them to list of mentioned users

         for(String user1:clique){
             List<Tweets> tweetsBy = Filter.writtenBy(tweets, user1);
             for (Tweets t : tweetsBy){
                 for (String user2 : users){
                     int count = clique.size();
                     if(userMentioned(t, user2)){
                         count--;
                         if (count == 0){
                             mentionedByUser.add(user2);
                         }
                     }

                 }
             }
         }

        //If any of these mentioned users also mention all of the current members of the clique, add them to the clique
        for(String user1: mentionedByUser){
            List<Tweets> filteredList = Filter.writtenBy(tweets, user1);
            for(Tweets t: filteredList){
                int count = clique.size();
                for(String user2:clique){
                    if(userMentioned(t,user2)){
                        count--;
                        if(count==0){
                            clique.add(user1);
                        }
                    }
                }

            }
        }

        return clique;

    }

    public static boolean contained(Set<String> createdClique, List<Set<String>> results){
        for(Set<String> curr : results){
            if(createdClique.equals(curr)){
                return true;
            }
        }
        return false;

    }

    //Loads everyones usernames into a hashmap key
    public static HashSet<String> loadUsernames(List<Tweets> tweets){
        HashSet<String> usernames = new HashSet<String>();
        for(Tweets t: tweets){
            if(!usernames.contains(t.getName())){
                //Make sure lowercase to deal with case insensitivity
                usernames.add(t.getName().toLowerCase());
            }
        }
        return usernames;
    }

    public static boolean userMentioned(Tweets t, String username){
        String text = t.getText().toLowerCase();
        username = username.toLowerCase();
        for(int i = 0; i<text.length(); i++ ){
            //Look for a username when @ sign is preceded by start of String or a char that is not a-z, A-Z or 0-9
            if(text.charAt(i) == '@' && (i == 0 || !(String.valueOf(text.charAt(i-1))).matches("[a-zA-Z0-9_]*"))){
                //Save the index of the @ sign
                int startIndexOfUsername = i;
                //Get past the @ sign and then check each letter to see if they're valid until they're not
                i++;
                //While i is not at the end of the String or has not become a non-alphanumeric, then continue counting
                while((i != text.length()) && (Character.isDigit(text.charAt(i)) || Character.isLetter(text.charAt(i))|| text.charAt(i) == '_')){
                    i++;
                }
                //Create a username to search the Hashmap for
                String foundName = text.substring(startIndexOfUsername + 1, i);
                foundName = foundName.toLowerCase();
                //Since followers already contains all the usernames, check if username that was generated is in it,
                //and if it is (meaning it is valid) then add 1 to it's follower count
                if(username.equals(foundName)){
                    return true;
                }
            }
        }
        return false;
    }


}




