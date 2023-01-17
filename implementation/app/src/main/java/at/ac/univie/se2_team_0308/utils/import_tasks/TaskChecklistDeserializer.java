package at.ac.univie.se2_team_0308.utils.import_tasks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ASubtask;
import at.ac.univie.se2_team_0308.models.Attachment;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.utils.SubtasksConverter;

public class TaskChecklistDeserializer implements JsonDeserializer<TaskChecklist> {
    @Override
    public TaskChecklist deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        String taskName = jsonObject.get("taskName").getAsString();
        String description = jsonObject.get("description").getAsString();
        EPriority priority = EPriority.valueOf(jsonObject.get("priority").getAsString());
        EStatus status = EStatus.valueOf(jsonObject.get("status").getAsString());
        ECategory category = ECategory.valueOf(jsonObject.get("category").getAsString());

        SubtasksConverter subtasksConverter = new SubtasksConverter();
        List<ASubtask> subtasks = subtasksConverter.jsonToSubtasks(jsonObject.get("subtasks").getAsString());

        List<Attachment> attachments = context.deserialize(jsonObject.get("attachments"), new TypeToken<List<Attachment>>(){}.getType());
        byte[] sketchData = context.deserialize(jsonObject.get("sketchData"), byte[].class);
        String taskColor = jsonObject.get("taskColor").getAsString();

        TaskChecklist taskChecklist = new TaskChecklist(taskName,description,priority,status,category,subtasks,attachments,sketchData,taskColor);

        Date creationDate = context.deserialize(jsonObject.get("creationDate"), Date.class);
        taskChecklist.setCreationDate(creationDate);
        taskChecklist.setId(id);

        return taskChecklist;
    }
}
