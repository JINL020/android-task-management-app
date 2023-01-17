package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;
import java.util.List;

// TODO: check that the Json array is constructed correctly
// TODO: check why there is not a single TaskRetriever with different arguments

/**
 Retrieves individual tasks from a json array in the form of strings based on regex patterns.
 */
public class JsonTaskRetriever {
    private static final String TAG = "JsonTaskRetriever";
    private String fileContent;

    /**
     * Constructor for JsonTaskRetriever class.
     *
     * @param fileContent a string representation of the json file
     */
    public JsonTaskRetriever(String fileContent){
        this.fileContent = fileContent;
    }


    /**
     * Retrieves individual tasks from the json file in the form of strings. Tasks are separated into two categories:
     * appointments and checklists.
     *
     * @return a Pair object containing two lists of strings representing the tasks in the json file. The first list
     * contains tasks of the "APPOINTMENT" category and the second list contains tasks of the "CHECKLIST" category.
     */

    // TODO: maybe remove these classes and simply use the MatchRetriever with the right arguments??
    public Pair<List<String>, List<String>> getTasks(){
        Log.d(TAG, "Retrieve individual strings containing tasks from json file");
        String regexAppointment = "\\{.*\"category\":\"APPOINTMENT\".*\\}";
        String regexChecklist = "\\{.*\"category\":\"CHECKLIST\".*\\}";

        boolean dotall = false;
        List<String> taskAppointmentStrings = MatchRetriever.retrieveMatch(regexAppointment, fileContent, dotall);
        List<String> taskChecklistStrings = MatchRetriever.retrieveMatch(regexChecklist, fileContent, dotall);

        return new Pair<>(taskAppointmentStrings, taskChecklistStrings);
    }
}
