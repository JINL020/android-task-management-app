package at.ac.univie.se2_team_0308.utils.export_tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

/**
 TaskToJSONConverter class converts the given list of TaskAppointment and TaskChecklist objects to a JSON format string.
 This class is an implementation of ITaskConverter interface.
 */
public class TaskToJSONConverter implements ITaskConverter {
    /**
     * Converts the given list of TaskAppointment and TaskChecklist objects to a JSON format string.
     * @param taskAppointment list of TaskAppointment objects
     * @param taskChecklists  list of TaskChecklist objects
     * @return                the converted JSON format string
     */
    @Override
    public String convertTasks(List < TaskAppointment > taskAppointment, List < TaskChecklist > taskChecklists) {
        // Create and append beginning of json array to json string
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"AllTasks\": [");
        stringBuilder.append(System.getProperty("line.separator"));

        // Convert and append TaskAppointment objects to json string
        for (TaskAppointment eachTask: taskAppointment) {
            String convertedTask = composeTask(eachTask);
            stringBuilder.append(convertedTask);
        }

        // Convert and append TaskChecklist objects to json string
        for (TaskChecklist eachTask: taskChecklists) {
            String convertedTask = composeTask(eachTask);
            stringBuilder.append(convertedTask);
        }

        // Create and append end of json array to json string
        stringBuilder.append("]}");

        return stringBuilder.toString();
    }

    // Compose the json string with new line and comma
    private String composeTask(ATask task){
        StringBuilder stringBuilder = new StringBuilder();
        String json = convertATask(task);
        stringBuilder.append(json);
        stringBuilder.append(",");
        stringBuilder.append(System.getProperty("line.separator"));

        return stringBuilder.toString();
    }

    // Convert ATask representation to TaskChecklist/TaskAppointment
    // representation in json
    private String convertATask(ATask task){
        // Add custom serializer for TaskChecklist that can successfully convert ASubtask to json string
        Gson gson = new GsonBuilder().registerTypeAdapter(TaskChecklist.class, new TaskChecklistSerializer()).create();
        String json = gson.toJson(task);

        return json;
    }

}