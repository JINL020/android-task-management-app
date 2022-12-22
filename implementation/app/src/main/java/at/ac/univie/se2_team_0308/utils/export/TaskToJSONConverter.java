package at.ac.univie.se2_team_0308.utils.export;

import com.google.gson.Gson;

import java.util.List;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class TaskToJSONConverter implements ITaskConverter {

    @Override
    public String convertTaskAppointment(List<TaskAppointment> tasks, EFormat format) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"AllTasks\": {");
        for(TaskAppointment eachTask : tasks){
            String json = convertTaskAppointment(eachTask);
            stringBuilder.append(json);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public String convertTaskChecklist(List<TaskChecklist> tasks, EFormat format) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"AllTasks\": {");
        for(TaskChecklist eachTask : tasks){
            String json = convertTaskChecklist(eachTask);
            stringBuilder.append(json);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    // TODO check if it would work with ATask
    private String convertTaskChecklist(TaskChecklist task){
        Gson gson = new Gson();
        String json = gson.toJson(task);

        return json;
    }

    private String convertTaskAppointment(TaskAppointment task){
        Gson gson = new Gson();
        String json = gson.toJson(task);

        return json;
    }
}
