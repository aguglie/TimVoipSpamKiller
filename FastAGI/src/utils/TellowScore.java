package utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrea on 01/07/17.
 */
public class TellowScore {
        private static HashMap<String, Integer> scoresCache = new HashMap<String, Integer>(){{
            //put("3476724903",9);
        }};

    public static int getScore(String callerID){
        callerID = sanitizeTelephoneNumber(callerID);

        //Check if number is in cache
        if (scoresCache.containsKey(callerID))
            return scoresCache.get(callerID);

        //No cache, ask tellows
        try {
            String url = "https://www.tellows.it/num/"+callerID;
            String webPage = HTTPRequest.get(url);

            Pattern pattern = Pattern.compile("(?<=Score )(.*?)(?= zu)");
            Matcher matcher = pattern.matcher(webPage);

            //If we find a score, cache it and return
            if (matcher.find()){
                Integer score = Integer.valueOf(matcher.group());
                scoresCache.put(callerID, score);
                return score;
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return 0;//In case of no internet or other shit.
    }

    private static String sanitizeTelephoneNumber(String callerID){
        if (callerID.substring(0,1).equals("+")){
            callerID = callerID.substring(3);
        }
        return callerID;
    }
}
