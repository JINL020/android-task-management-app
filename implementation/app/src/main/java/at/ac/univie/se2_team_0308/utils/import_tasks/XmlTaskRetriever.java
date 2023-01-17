package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;

import java.util.List;

/**
XmlTaskRetriever is a class that retrieves individual tasks from an xml file in the form of strings.
 */
public class XmlTaskRetriever {
    private static final String TAG = "XmlTaskRetriever";
    private String fileContent;

    /**
     * Constructor for XmlTaskRetriever class.
     *
     * @param fileContent a string representation of the xml file
     */
    public XmlTaskRetriever(String fileContent){
        this.fileContent = fileContent;
    }

    //TODO: add more comments in the method
    /**
     * Retrieves individual tasks from the xml file in the form of strings. Tasks are separated into two categories:
     * appointments and checklists.
     *
     * @return a Pair object containing two lists of strings representing the tasks in the xml file. The first list
     * contains tasks of the "TaskAppointment" category and the second list contains tasks of the "TaskChecklist" category.
     */
    public Pair<List<String>, List<String>> getTasks() {
        Log.d(TAG, "Retrieve individual strings containing tasks from xml file");
        String regexAppointment = "<at.ac.univie.se2__team__0308.models.TaskAppointment>.*?</at.ac.univie.se2__team__0308.models.TaskAppointment>";
        String regexChecklist = "<at.ac.univie.se2__team__0308.models.TaskChecklist>.*?</at.ac.univie.se2__team__0308.models.TaskChecklist>";

        boolean dotall = true;
        List<String> taskAppointmentStrings = MatchRetriever.retrieveMatch(regexAppointment, fileContent, dotall);
        List<String> taskChecklistStrings = MatchRetriever.retrieveMatch(regexChecklist, fileContent, dotall);

        return new Pair<>(taskAppointmentStrings, taskChecklistStrings);
    }
}
