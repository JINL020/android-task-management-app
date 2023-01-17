package at.ac.univie.se2_team_0308.utils.import_tasks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import junit.framework.TestCase;

import org.junit.Test;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

//TODO: fix this
public class DeserializeTest extends TestCase {
    @Test
    public void testDeserialize() throws JsonParseException {
//        String json = "{\"id\":1,\"taskName\":\"Test Task\",\"description\":\"Test Description\",\"priority\":\"HIGH\",\"status\":\"IN_PROGRESS\",\"category\":\"APPOINTMENT\",\"subtasks\":\"[{\\\"name\\\":\\\"Subtask 1\\\",\\\"completed\\\":false},{\\\"name\\\":\\\"Subtask 2\\\",\\\"completed\\\":false}]\",\"attachments\":[],\"sketchData\":null,\"taskColor\":\"#00FF00\",\"creationDate\":\"Jan 1, 1970 12:00:00 AM\"}";
////        JsonDeserializationContext context = new JsonDeserializationContextBuilder().build();
//        TaskChecklistDeserializer deserializer = new TaskChecklistDeserializer();
//        TaskChecklist taskChecklist = deserializer.deserialize(new JsonParser().parse(json), TaskChecklist.class, context);
//        assertEquals(1, taskChecklist.getId());
//        assertEquals("Test Task", taskChecklist.getTaskName());
//        assertEquals("Test Description", taskChecklist.getDescription());
//        assertEquals(EPriority.HIGH, taskChecklist.getPriority());
//        assertEquals(EStatus.IN_PROGRESS, taskChecklist.getStatus());
//        assertEquals(ECategory.APPOINTMENT, taskChecklist.getCategory());
//        assertEquals("#00FF00", taskChecklist.getTaskColor());
    }
}