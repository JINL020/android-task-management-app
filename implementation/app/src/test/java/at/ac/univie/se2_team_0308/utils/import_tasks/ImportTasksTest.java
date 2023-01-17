package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Pair;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

//TODO: add tests here
//TODO: add tests for both JsonImporter and XmlImporter
// TODO: also add tests for ImporterFacade
public class ImportTasksTest extends TestCase {
    private ITaskImporter jsonImporter;
//    private JsonTaskRetriever jsonTaskRetriever;
    private Pair<List<String>, List<String>> tasks;

    public void setUp() {

        tasks = new Pair<>(Arrays.asList("{\"id\":1,\"taskName\":\"Task 1\",\"description\":\"Description 1\",\"priority\":\"HIGH\",\"status\":\"IN_PROGRESS\",\"category\":\"WORK\",\"startTime\":\"2020-01-01T10:00:00.000Z\",\"endTime\":\"2020-01-01T11:00:00.000Z\",\"location\":\"Location 1\",\"reminder\":\"REMINDER_15_MINUTES\",\"attachments\":[],\"sketchData\":null,\"taskColor\":\"#ffffff\"}"),
                Arrays.asList("{\"id\":1,\"taskName\":\"Task 1\",\"description\":\"Description 1\",\"priority\":\"HIGH\",\"status\":\"IN_PROGRESS\",\"category\":\"WORK\",\"subtasks\":[],\"attachments\":[],\"sketchData\":null,\"taskColor\":\"#ffffff\"}"));

//        jsonTaskRetriever = mock(JsonTaskRetriever.class);
//        when(jsonTaskRetriever.getTasks()).thenReturn(tasks);
//        jsonImporter = new JsonImporter(jsonTaskRetriever);
    }

}