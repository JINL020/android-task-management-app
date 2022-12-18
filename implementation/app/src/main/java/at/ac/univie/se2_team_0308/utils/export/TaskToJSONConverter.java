package at.ac.univie.se2_team_0308.utils.export;

import com.google.gson.Gson;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

public class TaskToJSONConverter implements ITaskConverter {

    @Override
    public String convertTasks(List<ATask> tasks, EFormat format) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"AllTasks\": {");
        for(ATask eachTask : tasks){
            String json = convertTask(eachTask);
            stringBuilder.append(json);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
    private String convertTask(ATask task){
        Gson gson = new Gson();
        String json = gson.toJson(task);

        return json;
    }
}
