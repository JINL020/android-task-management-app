package at.ac.univie.se2_team_0308.utils.export_tasks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.utils.SubtasksConverter;

/**
 * TaskChecklistSerializer implements the JsonSerializer from Gson in order to create a custom
 * serialization of the subtasks in a TaskChecklist. It uses the SubtasksConverter class to achieve this.
 */
public class TaskChecklistSerializer implements JsonSerializer<TaskChecklist> {
    @Override
    public JsonElement serialize(TaskChecklist taskChecklist, Type typeOfSrc, JsonSerializationContext jsonSerializationContext) {
        SubtasksConverter subtasksConverter = new SubtasksConverter();
        String subtasks = subtasksConverter.subtasksToJson(taskChecklist.getSubtasks());

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("id", new JsonPrimitive(taskChecklist.getId()));
        jsonObject.add("taskName", new JsonPrimitive(taskChecklist.getTaskName()));
        jsonObject.add("description", new JsonPrimitive(taskChecklist.getDescription()));
        jsonObject.add("priority", new JsonPrimitive(taskChecklist.getPriority().name()));
        jsonObject.add("status", new JsonPrimitive(taskChecklist.getStatus().name()));
        jsonObject.add("category", new JsonPrimitive(taskChecklist.getCategory().name()));
        jsonObject.add("creationDate", jsonSerializationContext.serialize(taskChecklist.getCreationDate()));
        jsonObject.add("subtasks", new JsonPrimitive(subtasks));
        jsonObject.add("attachments", jsonSerializationContext.serialize(taskChecklist.getAttachments()));
        jsonObject.add("sketchData", jsonSerializationContext.serialize(taskChecklist.getSketchData()));
        jsonObject.add("taskColor", jsonSerializationContext.serialize(taskChecklist.getTaskColor()));

        return jsonObject;
    }
}
