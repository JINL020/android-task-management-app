package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;


/**
 JsonImporter is a class that imports tasks from a json file and converts them into task objects.
 It implements the ITaskImporter interface.
 */
public class JsonImporter implements ITaskImporter {
    private static final String TAG = "TaskImporter";
    private final JsonTaskRetriever jsonTaskRetriever;

    /**
     * Constructor for JsonImporter class.
     *
     * @param jsonTaskRetriever an object of the JsonTaskRetriever class.
     */
    public JsonImporter(JsonTaskRetriever jsonTaskRetriever){
        this.jsonTaskRetriever = jsonTaskRetriever;
    }

    /**
     * Imports tasks from a json file and converts them into task objects.
     *
     * @return a Pair object containing two lists of task objects. The first list contains TaskAppointment objects and
     * the second list contains TaskChecklist objects.
     */
    @Override
    public Pair<List<TaskAppointment>, List<TaskChecklist>> importTasks() {
        Log.d(TAG, "Converting imported tasks from json to task objects");
        
        Pair<List<TaskAppointment>, List<TaskChecklist>> importedTasks = new Pair<>(new ArrayList<>(), new ArrayList<>());
        Pair<List<String>, List<String>> tasks = jsonTaskRetriever.getTasks();

        Gson gson = new GsonBuilder().registerTypeAdapter(TaskChecklist.class, new TaskChecklistDeserializer()).create();

        for(String eachAppointmentString: tasks.first){
            TaskAppointment taskAppointment = gson.fromJson(eachAppointmentString, TaskAppointment.class);
            importedTasks.first.add(taskAppointment);
            Log.d(TAG, taskAppointment.toString());
        }

        for(String eachChecklistString: tasks.second){
            try{
                TaskChecklist taskChecklist = gson.fromJson(eachChecklistString, TaskChecklist.class);
                importedTasks.second.add(taskChecklist);
                 Log.d(TAG, taskChecklist.toString());
            }
            catch(Exception e){
                Log.e(TAG, e.toString());
            }
        }
        Log.d(TAG, "size of checklist is " + importedTasks.second.size());

        return importedTasks;
    }
}
