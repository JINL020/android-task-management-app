package at.ac.univie.se2_team_0308.utils.export;

import static org.mockito.Mockito.when;

import com.google.gson.Gson;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class ConvertTaskTest extends TestCase {
    @Mock
    private TaskChecklistSerializer taskChecklistSerializer;
    @Mock
    private Gson gson;
    @Mock
    private TaskChecklist taskChecklist;
    @Mock
    private TaskAppointment taskAppointment;
    private ITaskConverter taskConverter;

    @Test
    public void testConvertTasksJson_validInput_validOutput() {
        taskConverter = new TaskToJSONConverter();
        List<TaskAppointment> taskAppointments = new ArrayList<>();
        taskAppointments.add(taskAppointment);
        List<TaskChecklist> taskChecklists = new ArrayList<>();
        taskChecklists.add(taskChecklist);
        when(gson.toJson(taskAppointment)).thenReturn("{\"testAppointment\":\"test\"}");
        when(gson.toJson(taskChecklist)).thenReturn("{\"testChecklist\":\"test\"}");

        String expected = "{\"AllTasks\": {\n{\"testAppointment\":\"test\"}\n{\"testChecklist\":\"test\"}\n}";
        String result = taskConverter.convertTasks(taskAppointments, taskChecklists);
        assertEquals(expected, result);
    }

    @Test
    public void testConvertTasksJson_nullInput_validOutput() {
        taskConverter = new TaskToJSONConverter();

        String expected = "{\"AllTasks\": {\n}";
        String result = taskConverter.convertTasks(null, null);
        assertEquals(expected, result);
    }

    @Test
    public void testConvertTasksXml_validInput_validOutput() {
        taskConverter = new TaskToXMLConverter();
        List<TaskAppointment> taskAppointments = new ArrayList<>();
        taskAppointments.add(taskAppointment);
        List<TaskChecklist> taskChecklists = new ArrayList<>();
        taskChecklists.add(taskChecklist);
        when(gson.toJson(taskAppointment)).thenReturn("{\"testAppointment\":\"test\"}");
        when(gson.toJson(taskChecklist)).thenReturn("{\"testChecklist\":\"test\"}");

        String expected = "{\"AllTasks\": {\n{\"testAppointment\":\"test\"}\n{\"testChecklist\":\"test\"}\n}";
        String result = taskConverter.convertTasks(taskAppointments, taskChecklists);
        assertEquals(expected, result);
    }

    @Test
    public void testConvertTasksXml_nullInput_validOutput() {
        taskConverter = new TaskToXMLConverter();

        String expected = "{\"AllTasks\": {\n}";
        String result = taskConverter.convertTasks(null, null);
        assertEquals(expected, result);
    }

}