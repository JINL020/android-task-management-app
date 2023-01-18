package at.ac.univie.se2_team_0308.utils.import_tasks;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class GetFilenameTest {
    private Uri uri;
    private ContentResolver contentResolver;
    private Cursor cursor;

    @Before
    public void setUp() {
        contentResolver = Mockito.mock(ContentResolver.class);
        cursor = Mockito.mock(Cursor.class);

        when(contentResolver.query(uri, null, null, null, null)).thenReturn(cursor);
        when(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)).thenReturn(0);
        when(cursor.moveToFirst()).thenReturn(true);
    }

    @Test
    public void jsonFile_expectedOutput() throws UnsupportedDocumentFormatException {
        List<String> supportedFormats = Arrays.asList(".json", ".xml");
        String expected = "tasks.json";
        when(cursor.getString(0)).thenReturn(expected);

        String actual = FilenameRetriever.getFilename(uri, contentResolver, supportedFormats);

        assertEquals(expected, actual);
    }

    @Test
    public void xmlFile_expectedOutput() throws UnsupportedDocumentFormatException {
        List<String> supportedFormats = Arrays.asList(".json", ".xml");
        String expected = "tasks.xml";
        when(cursor.getString(0)).thenReturn(expected);

        String actual = FilenameRetriever.getFilename(uri, contentResolver, supportedFormats);

        assertEquals(expected, actual);
    }

    @Test(expected = UnsupportedDocumentFormatException.class)
    public void unsupportedFormat_throwsException() throws UnsupportedDocumentFormatException {
        List<String> supportedFormats = Arrays.asList(".json", ".xml");
        String expected = "tasks.pdf";
        when(cursor.getString(0)).thenReturn(expected);

        String actual = FilenameRetriever.getFilename(uri, contentResolver, supportedFormats);

        assertEquals(expected, actual);
    }

}