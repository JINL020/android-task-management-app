package at.ac.univie.se2_team_0308.utils.export;

import com.google.gson.Gson;

import java.util.List;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class TaskToJSONConverter implements ITaskConverter {
    @Override
    public String convertTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists) {
        Gson gson = new Gson();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"AllTasks\": {");
        stringBuilder.append(System.getProperty("line.separator"));

        if(taskAppointment != null){
            for(TaskAppointment eachTask : taskAppointment){
                String json = gson.toJson(eachTask);
                stringBuilder.append(json);
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }
        if(taskChecklists != null){
            for(TaskChecklist eachTask : taskChecklists){
                String json = gson.toJson(eachTask);
                stringBuilder.append(json);
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}
