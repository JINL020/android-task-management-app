package at.ac.univie.se2_team_0308.utils.export_tasks;

import static org.mockito.Mockito.when;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class ExporteTasksTest extends TestCase {
    private Exporter exporter;
    private List<TaskAppointment> taskAppointments;
    private List<TaskChecklist> taskChecklists;

    @Mock
    private ITaskConverter mockTaskConverter;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        exporter = new Exporter();
        //taskAppointments = Arrays.asList(new TaskAppointment("Task 1"), new TaskAppointment("Task 2"));
        //taskChecklists = Arrays.asList(new TaskChecklist("Task 3"), new TaskChecklist("Task 4"));
    }

    @Test
    public void testExportTasks_XMLFormat() throws IOException {
        // Arrange
        when(mockTaskConverter.convertTasks(taskAppointments, taskChecklists)).thenReturn("<xml></xml>");
        // Act
        exporter.exportTasks(taskAppointments, taskChecklists, EFormat.XML);
        // Assert
        File file = new File(System.getProperty("user.dir") + "/tasks_" + "01-01-2022" + ".xml");
        assertTrue(file.exists());
    }
}