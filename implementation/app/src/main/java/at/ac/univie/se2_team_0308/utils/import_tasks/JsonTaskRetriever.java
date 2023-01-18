package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;
import java.util.List;


/**
 Retrieves individual tasks from a json array in the form of strings based on regex patterns.
 */
public class JsonTaskRetriever {
    private static final String TAG = "JsonTaskRetriever";
    private String fileContent;

    /**
     * Constructor for JsonTaskRetriever class.
     * @param fileContent a string representation of the json file
     */
    public JsonTaskRetriever(String fileContent){
        this.fileContent = fileContent;
    }

    /**
     * Retrieves individual TaskAppointment and TaskChecklist tasks from the json file in the form of strings.
     * @return a Pair object containing a list of TaskAppointment and a list of TaskChecklist as strings
     * representing the tasks in the json file.
     */
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
