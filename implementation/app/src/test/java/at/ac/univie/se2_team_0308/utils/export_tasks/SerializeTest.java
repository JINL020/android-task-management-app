package at.ac.univie.se2_team_0308.utils.export;

import static org.mockito.Mockito.when;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ASubtask;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.SubtaskItem;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.utils.SubtasksConverter;

public class SerializeTest extends TestCase {
    private TaskChecklistSerializer taskChecklistSerializer;
    @Mock
    private JsonSerializationContext jsonSerializationContext;
    @Mock
    private SubtasksConverter subtasksConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskChecklistSerializer = new TaskChecklistSerializer();
    }


    @Test
    public void testSerialize_validInput_validOutput() {
        TaskChecklist taskChecklist = new TaskChecklist("taskName", "description", EPriority.LOW, EStatus.NOT_STARTED, ECategory.CHECKLIST, null, null, null, "#E1E1E1");
        taskChecklist.setId(1);
        taskChecklist.setCreationDate(new Date());
        List<ASubtask> subtasks = Arrays.asList(new SubtaskItem("subtask1"), new SubtaskItem("subtask2"));
        when(subtasksConverter.subtasksToJson(subtasks)).thenReturn("[{\"subtaskName\":\"subtask1\"},{\"subtaskName\":\"subtask2\"}]");
        taskChecklist.setSubtasks(subtasks);

        JsonElement jsonElement = taskChecklistSerializer.serialize(taskChecklist, null, jsonSerializationContext);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        assertEquals(1, jsonObject.get("id").getAsInt());
        assertEquals("taskName", jsonObject.get("taskName").getAsString());
        assertEquals("description", jsonObject.get("description").getAsString());
        assertEquals("LOW", jsonObject.get("priority").getAsString());
        assertEquals("NOT_STARTED", jsonObject.get("status").getAsString());
        assertEquals("CHECKLIST", jsonObject.get("category").getAsString());
        assertEquals("[{\"subtaskName\":\"subtask1\"},{\"subtaskName\":\"subtask2\"}]", jsonObject.get("subtasks").getAsString());
    }
}