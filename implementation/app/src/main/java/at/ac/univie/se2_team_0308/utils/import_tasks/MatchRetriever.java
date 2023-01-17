package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchRetriever {
    private static final String TAG = "MatchRetriever";
    /**
     * Finds all the matches in a string based on regex and returns a list containg each match
     * @param regex  the pattern that we are looking for
     * @param text   the string to be searched
     * @param dotall specifies if the dot character (which matches all characters but newline)
     *               should also match newline character
     * @return       all the matches found in text as list
     */
    public static List<String> retrieveMatch(String regex, String text, boolean dotall){
        List<String> matches = new ArrayList<>();
        Pattern pattern;

        // Compile with Pattern.DOTALL so that the pattern can be searched across multiple lines
        if(dotall){
            pattern = Pattern.compile(regex, Pattern.DOTALL);
        }
        else{
            pattern = Pattern.compile(regex);
        }

        // Add match to list while a new match is found
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            String match = matcher.group();
            matches.add(match);
            Log.d(TAG, match);
        }

        Log.d(TAG, "No more matches found");

        return matches;
    }
}
