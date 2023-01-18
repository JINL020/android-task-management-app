package at.ac.univie.se2_team_0308.utils.import_tasks;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.List;

public class RetrieveMatchTest {
    @Test
    public void dotallTrue_twoMatchesConsideredAcrossMultipleLines() {
        String regex = "<at.ac.univie.se2__team__0308.models.TaskChecklist>.*?</at.ac.univie.se2__team__0308.models.TaskChecklist>";
        String start = "<AllTasks>";
        String task1 =
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
                "</at.ac.univie.se2__team__0308.models.TaskChecklist>";
        String task2 =
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
                "</at.ac.univie.se2__team__0308.models.TaskChecklist>";
        String end = "</AllTasks>";

        String text = start + task1 + "\n" + task2 + "\n" + end;
        boolean dotall = true;
        List<String> matches = MatchRetriever.retrieveMatch(regex, text, dotall);

        assertEquals(2, matches.size());
        assertEquals(task1, matches.get(0));
        assertEquals(task2, matches.get(1));
    }

    @Test
    public void dotallFalse_twoMatchesConsideredAcrossOneLine() {
        String regex = "\\{.*\"category\":\"APPOINTMENT\".*\\}";
        String start = "{\"AllTasks\": [\n";
        String task1 = "{\"deadline\":\"Jan 17, 2023 2:33:00 PM\",\"attachments\":[],\"category\":\"APPOINTMENT\",\"creationDate\":\"Jan 17, 2023 2:33:24 PM\",\"description\":\"\",\"id\":1,\"isHidden\":false,\"isSelected\":true,\"priority\":\"LOW\",\"sketchData\":[],\"status\":\"NOT_STARTED\",\"taskColor\":\"#E1E1E1\",\"taskName\":\"dasd\"}";
        String task2 = "{\"deadline\":\"Jan 17, 2023 7:52:00 PM\",\"attachments\":[],\"category\":\"APPOINTMENT\",\"creationDate\":\"Jan 17, 2023 7:52:32 PM\",\"description\":\"\",\"id\":2,\"isHidden\":false,\"isSelected\":true,\"priority\":\"LOW\",\"sketchData\":[],\"status\":\"NOT_STARTED\",\"taskColor\":\"#E1E1E1\",\"taskName\":\"dsfs\"}";
        String end = "]}";

        String text = start + task1 + ",\n" + task2 + "\n" + end;
        boolean dotall = false;
        List<String> matches = MatchRetriever.retrieveMatch(regex, text, dotall);

        assertEquals(2, matches.size());
        assertEquals(task1, matches.get(0));
        assertEquals(task2, matches.get(1));
    }

    @Test
    public void dotallFalse_oneMatchConsideredAcrossOneLine() {
        String regex = "\\{.*\"category\":\"APPOINTMENT\".*\\}";
        String start = "{\"AllTasks\": [\n";
        String task1 = "{\"deadline\":\"Jan 17, 2023 2:33:00 PM\",\"attachments\":[],\"category\":\"APPOINTMENT\"," +
                "\"creationDate\":\"Jan 17, 2023 2:33:24 PM\",\"description\":\"\",\"id\":1,\"isHidden\":false,\"isSelected\":true," +
                "\"priority\":\"LOW\",\"sketchData\":[],\"status\":\"NOT_STARTED\",\"taskColor\":\"#E1E1E1\",\"taskName\":\"dasd\"}";
        String task2 = "{\"deadline\":\"Jan 17, 2023 7:52:00 PM\",\"attachments\":[],\"category\":\"APPOINTMENT\"," +
                "\"creationDate\":\"Jan 17, 2023 7:52:32 PM\",\"description\":\"\",\"id\":2,\"isHidden\":false,\"isSelected\":true," +
                "\"priority\":\"LOW\",\"sketchData\":[],\"status\":\"NOT_STARTED\",\"taskColor\":\"#E1E1E1\",\"taskName\":\"dsfs\"}";
        String end = "]}";
        String expected = start + task1 + ",\n" + task2 + "\n" + end;

        boolean dotall = true;
        List<String> matches = MatchRetriever.retrieveMatch(regex, expected, dotall);
        String actual = matches.get(0);

        assertEquals(1, matches.size());
        assertEquals(expected, actual);
    }
}