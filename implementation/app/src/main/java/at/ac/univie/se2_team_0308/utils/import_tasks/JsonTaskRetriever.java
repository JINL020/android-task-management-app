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
        Log.d(TAG, "Retrieve individual strings containing tasks");
        String regexAppointment = "\\{.*\"category\":\"APPOINTMENT\".*\\}";
        String regexChecklist = "\\{.*\"category\":\"CHECKLIST\".*\\}";

        List<String> taskAppointmentStrings = MatchRetriever.retrieveTasks(regexAppointment, fileContent);
        List<String> taskChecklistStrings = MatchRetriever.retrieveTasks(regexChecklist, fileContent);

        return new Pair<>(taskAppointmentStrings, taskChecklistStrings);
    }
}
