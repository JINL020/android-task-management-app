package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchRetriever {
    private static final String TAG = "MatchRetriever";
    public static List<String> retrieveTasks(String regex, String fileContent){
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);

        while (matcher.find()){
            String match = matcher.group();
            matches.add(match);
            Log.d(TAG, match);
        }

        return matches;
    }
}
