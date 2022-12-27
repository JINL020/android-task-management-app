package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;
import java.util.List;

public class JsonTaskRetriever {
    private static final String TAG = "JsonTaskRetriever";
    private String fileContent;

    public JsonTaskRetriever(String fileContent){
        this.fileContent = fileContent;
    }
    public Pair<List<String>, List<String>> getTasks(){
        Log.d(TAG, "Retrieve individual strings containing tasks from json file");
        String regexAppointment = "\\{.*\"category\":\"APPOINTMENT\".*\\}";
        String regexChecklist = "\\{.*\"category\":\"CHECKLIST\".*\\}";

        boolean dotall = false;
        List<String> taskAppointmentStrings = MatchRetriever.retrieveTasks(regexAppointment, fileContent, dotall);
        List<String> taskChecklistStrings = MatchRetriever.retrieveTasks(regexChecklist, fileContent, dotall);

        return new Pair<>(taskAppointmentStrings, taskChecklistStrings);
    }
}
