package at.ac.univie.se2_team_0308.utils.export_tasks;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class ConvertTaskTest {
    private ITaskConverter taskConverter;

    public TaskAppointment createTaskAppointment(int idAppointment){
        TaskAppointment taskAppointment = new TaskAppointment(
                "taskName",
                "description",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2022, 5, 12),
                new ArrayList<>(),
                new byte[0],
                ""
        );
        taskAppointment.setId(idAppointment);
        taskAppointment.setCreationDate(new Date(2020, 5, 12));

        return taskAppointment;
    }

    public TaskChecklist createTaskChecklist(int idChecklist){
        TaskChecklist taskChecklist = new TaskChecklist(
                "taskName",
                "description",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>(),
                new ArrayList<>(),
                new byte[0],
                ""
        );
        taskChecklist.setId(idChecklist);
        taskChecklist.setCreationDate(new Date(2020, 5, 12));

        return taskChecklist;
    }

    @Test
    public void convertTaskAppointmentTaskChecklistXml_expectedOutput() {
        taskConverter = new TaskToXmlConverter();

        List<TaskAppointment> taskAppointments = new ArrayList<>();
        List<TaskChecklist> taskChecklists = new ArrayList<>();

        // Create a TaskChecklist and add it to list
        TaskChecklist taskChecklist = createTaskChecklist(0);
        taskChecklists.add(taskChecklist);

        // Create a TaskAppointment and add it to list
        TaskAppointment taskAppointment = createTaskAppointment(0);
        taskAppointments.add(taskAppointment);

        String expected = "<AllTasks><at.ac.univie.se2__team__0308.models.TaskAppointment>\n" +
                "  <id>0</id>\n" +
                "  <taskName>taskName</taskName>\n" +
                "  <description>description</description>\n" +
                "  <priority>LOW</priority>\n" +
                "  <status>NOT_STARTED</status>\n" +
                "  <isSelected>false</isSelected>\n" +
                "  <category>APPOINTMENT</category>\n" +
                "  <isHidden>false</isHidden>\n" +
                "  <sketchData></sketchData>\n" +
                "  <attachments/>\n" +
                "  <taskColor></taskColor>\n" +
                "  <creationDate>3920-06-11 22:00:00.0 UTC</creationDate>\n" +
                "  <deadline>3922-06-11 22:00:00.0 UTC</deadline>\n" +
                "</at.ac.univie.se2__team__0308.models.TaskAppointment>\n" +
                "<at.ac.univie.se2__team__0308.models.TaskChecklist>\n" +
                "  <id>0</id>\n" +
                "  <taskName>taskName</taskName>\n" +
                "  <description>description</description>\n" +
                "  <priority>LOW</priority>\n" +
                "  <status>NOT_STARTED</status>\n" +
                "  <isSelected>false</isSelected>\n" +
                "  <category>CHECKLIST</category>\n" +
                "  <isHidden>false</isHidden>\n" +
                "  <sketchData></sketchData>\n" +
                "  <attachments/>\n" +
                "  <taskColor></taskColor>\n" +
                "  <creationDate>3920-06-11 22:00:00.0 UTC</creationDate>\n" +
                "  <subtasks/>\n" +
                "</at.ac.univie.se2__team__0308.models.TaskChecklist>\n" +
                "</AllTasks>";

        String actual = taskConverter.convertTasks(taskAppointments, taskChecklists);
        assertEquals(expected, actual);
    }


    @Test
    public void convertMultipleTaskChecklistXml_expectedOutput() {
        taskConverter = new TaskToXmlConverter();

        List<TaskAppointment> taskAppointments = new ArrayList<>();
        List<TaskChecklist> taskChecklists = new ArrayList<>();

        // Create a TaskChecklist and add it to list
        TaskChecklist taskChecklist1 = createTaskChecklist(0);
        taskChecklists.add(taskChecklist1);

        // Create a TaskChecklist and add it to list
        TaskChecklist taskChecklist2 = createTaskChecklist(1);
        taskChecklists.add(taskChecklist2);


        String expected = "<AllTasks><at.ac.univie.se2__team__0308.models.TaskChecklist>\n" +
                "  <id>0</id>\n" +
                "  <taskName>taskName</taskName>\n" +
                "  <description>description</description>\n" +
                "  <priority>LOW</priority>\n" +
                "  <status>NOT_STARTED</status>\n" +
                "  <isSelected>false</isSelected>\n" +
                "  <category>CHECKLIST</category>\n" +
                "  <isHidden>false</isHidden>\n" +
                "  <sketchData></sketchData>\n" +
                "  <attachments/>\n" +
                "  <taskColor></taskColor>\n" +
                "  <creationDate>3920-06-11 22:00:00.0 UTC</creationDate>\n" +
                "  <subtasks/>\n" +
                "</at.ac.univie.se2__team__0308.models.TaskChecklist>\n" +
                "<at.ac.univie.se2__team__0308.models.TaskChecklist>\n" +
                "  <id>1</id>\n" +
                "  <taskName>taskName</taskName>\n" +
                "  <description>description</description>\n" +
                "  <priority>LOW</priority>\n" +
                "  <status>NOT_STARTED</status>\n" +
                "  <isSelected>false</isSelected>\n" +
                "  <category>CHECKLIST</category>\n" +
                "  <isHidden>false</isHidden>\n" +
                "  <sketchData></sketchData>\n" +
                "  <attachments/>\n" +
                "  <taskColor></taskColor>\n" +
                "  <creationDate>3920-06-11 22:00:00.0 UTC</creationDate>\n" +
                "  <subtasks/>\n" +
                "</at.ac.univie.se2__team__0308.models.TaskChecklist>\n" +
                "</AllTasks>";

        String actual = taskConverter.convertTasks(taskAppointments, taskChecklists);
        assertEquals(expected, actual);
    }


    @Test
    public void convertTaskAppointmentXml_expectedOutput() {
        taskConverter = new TaskToXmlConverter();

        List<TaskAppointment> taskAppointments = new ArrayList<>();
        List<TaskChecklist> taskChecklists = new ArrayList<>();

        // Create a TaskAppointment and add it to list
        TaskAppointment taskAppointment = createTaskAppointment(0);
        taskAppointments.add(taskAppointment);

        String expected = "<AllTasks><at.ac.univie.se2__team__0308.models.TaskAppointment>\n" +
                "  <id>0</id>\n" +
                "  <taskName>taskName</taskName>\n" +
                "  <description>description</description>\n" +
                "  <priority>LOW</priority>\n" +
                "  <status>NOT_STARTED</status>\n" +
                "  <isSelected>false</isSelected>\n" +
                "  <category>APPOINTMENT</category>\n" +
                "  <isHidden>false</isHidden>\n" +
                "  <sketchData></sketchData>\n" +
                "  <attachments/>\n" +
                "  <taskColor></taskColor>\n" +
                "  <creationDate>3920-06-11 22:00:00.0 UTC</creationDate>\n" +
                "  <deadline>3922-06-11 22:00:00.0 UTC</deadline>\n" +
                "</at.ac.univie.se2__team__0308.models.TaskAppointment>\n" +
                "</AllTasks>";

        String actual = taskConverter.convertTasks(taskAppointments, taskChecklists);
        assertEquals(expected, actual);
    }

}