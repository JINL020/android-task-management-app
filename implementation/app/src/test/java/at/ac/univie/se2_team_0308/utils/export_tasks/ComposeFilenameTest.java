package at.ac.univie.se2_team_0308.utils.export;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ComposeFilenameTest extends TestCase {

    @Test
    public void validInput_validOutput() {
        String extension = "json";
        String date = "2022-01-01";
        String expected = "tasks_2022-01-01.json";
        String result = FilenameComposer.composeName(extension, date);
        assertEquals(expected, result);
    }

    @Test
    public void validInput_validOutput_emptyDate() {
        String extension = "json";
        String date = "";
        String expected = "tasks_.json";
        String result = FilenameComposer.composeName(extension, date);
        assertEquals(expected, result);
    }

    @Test
    public void validInput_validOutput_emptyExtension() {
        String extension = "";
        String date = "2022-01-01";
        String expected = "tasks_2022-01-01.";
        String result = FilenameComposer.composeName(extension, date);
        assertEquals(expected, result);
    }
}