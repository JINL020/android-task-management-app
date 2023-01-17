package at.ac.univie.se2_team_0308.utils.import_tasks;

import static org.mockito.Mockito.when;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

public class GetFilenameTest extends TestCase {
    @Mock
    private Uri uri;

    @Mock
    private ContentResolver contentResolver;

    @Mock
    private Cursor cursor;

    @Test
    public void testGetFilename() throws UnsupportedDocumentFormatException {
        // Set up test data
        List<String> supportedFormats = Arrays.asList(".json", ".xml");
        String expectedFilename = "tasks.json";

        // Mock the cursor
        when(contentResolver.query(uri, null, null, null, null)).thenReturn(cursor);
        when(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)).thenReturn(0);
        when(cursor.moveToFirst()).thenReturn(true);
        when(cursor.getString(0)).thenReturn(expectedFilename);

        // Call the method under test
        String actualFilename = FilenameRetriever.getFilename(uri, contentResolver, supportedFormats);

        // Assert that the expected and actual filenames match
        assertEquals(expectedFilename, actualFilename);
    }

    @Test(expected = UnsupportedDocumentFormatException.class)
    public void testGetFilename_UnsupportedFormat_ThrowsException() throws UnsupportedDocumentFormatException {
        // Set up test data
        List<String> supportedFormats = Arrays.asList(".json", ".xml");
        String fileName = "tasks.txt";

        // Mock the cursor
        when(contentResolver.query(uri, null, null, null, null)).thenReturn(cursor);
        when(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)).thenReturn(0);
        when(cursor.moveToFirst()).thenReturn(true);
        when(cursor.getString(0)).thenReturn(fileName);

        // Call the method under test
        FilenameRetriever.getFilename(uri, contentResolver, supportedFormats);
    }

}