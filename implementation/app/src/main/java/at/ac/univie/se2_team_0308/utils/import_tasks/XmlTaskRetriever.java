package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;

import java.util.List;

public class XmlTaskRetriever {
    private static final String TAG = "XmlTaskRetriever";
    private String fileContent;

    public XmlTaskRetriever(String fileContent){
        this.fileContent = fileContent;
    }

    public Pair<List<String>, List<String>> getTasks() {
        Log.d(TAG, "Retrieve individual strings containing tasks from xml file");
        String regexAppointment = "<at.ac.univie.se2__team__0308.models.TaskAppointment>.*</at.ac.univie.se2__team__0308.models.TaskAppointment>";
        String regexChecklist = "<at.ac.univie.se2__team__0308.models.TaskChecklist>.*</<at.ac.univie.se2__team__0308.models.TaskChecklist>";

        boolean dotall = true;
        List<String> taskAppointmentStrings = MatchRetriever.retrieveTasks(regexAppointment, fileContent, dotall);
        List<String> taskChecklistStrings = MatchRetriever.retrieveTasks(regexChecklist, fileContent, dotall);

        return new Pair<>(taskAppointmentStrings, taskChecklistStrings);
    }
}
