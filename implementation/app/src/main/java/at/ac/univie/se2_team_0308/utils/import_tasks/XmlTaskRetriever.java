package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;

import java.util.List;

/**
 * XmlTaskRetriever is a class that retrieves individual tasks from an xml file in the form of strings.
 */
public class XmlTaskRetriever {
    private static final String TAG = "XmlTaskRetriever";
    private final String fileContent;

    /**
     * Constructor for XmlTaskRetriever class.
     * @param fileContent a string representation of the xml file
     */
    public XmlTaskRetriever(String fileContent){
        this.fileContent = fileContent;
    }
    /**
     * Retrieves individual TaskAppointment and TaskChecklist tasks from the xml file in the form of strings.
     * @return a Pair object containing a list of TaskAppointment and a list of TaskChecklist as strings
     * representing the tasks in the xml file.
     */
    public Pair<List<String>, List<String>> getTasks() {
        Log.d(TAG, "Retrieve individual strings containing tasks from xml file");
        String patternAppointment = "<at.ac.univie.se2__team__0308.models.TaskAppointment>.*?</at.ac.univie.se2__team__0308.models.TaskAppointment>";
        String patternChecklist = "<at.ac.univie.se2__team__0308.models.TaskChecklist>.*?</at.ac.univie.se2__team__0308.models.TaskChecklist>";

        boolean dotall = true;
        List<String> taskAppointmentStrings = MatchRetriever.retrieveMatch(patternAppointment, fileContent, dotall);
        List<String> taskChecklistStrings = MatchRetriever.retrieveMatch(patternChecklist, fileContent, dotall);

        return new Pair<>(taskAppointmentStrings, taskChecklistStrings);
    }
}
