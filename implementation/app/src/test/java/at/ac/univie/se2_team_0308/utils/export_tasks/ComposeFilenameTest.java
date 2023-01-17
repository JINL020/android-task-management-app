package at.ac.univie.se2_team_0308.utils.export_tasks;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ComposeFilenameTest {

    @Test
    public void validInputJson_expectedOutput() {
        String expected = "tasks_2023-16-01.json";

        String extension = "json";
        String date = "2023-16-01";
        String actual = FilenameComposer.composeName(extension, date);

        assertEquals(expected, actual);
    }

    @Test
    public void validInputXml_expectedOutput() {
        String expected = "tasks_2023-16-01.xml";

        String extension = "xml";
        String date = "2023-16-01";
        String actual = FilenameComposer.composeName(extension, date);

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyDate_throwIllegalArgumentException() {
        String extension = "json";
        String date = "";

        FilenameComposer.composeName(extension, date);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyExtension_throwIllegalArgumentException() {
        String extension = "";
        String date = "2022-01-01";

        FilenameComposer.composeName(extension, date);
    }
}