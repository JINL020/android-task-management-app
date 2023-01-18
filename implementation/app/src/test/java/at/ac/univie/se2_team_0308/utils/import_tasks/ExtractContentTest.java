package at.ac.univie.se2_team_0308.utils.import_tasks;

import static org.mockito.Mockito.when;

import android.content.ContentResolver;
import android.net.Uri;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(RobolectricTestRunner.class)
public class ExtractContentTest extends TestCase {
    private ContentResolver contentResolver;
    private FileContentExtractor fileContentExtractor;

    @Before
    public void setUp() {
        contentResolver = Mockito.mock(ContentResolver.class);
        fileContentExtractor = new FileContentExtractor(contentResolver);
    }

    @Test
    public void fileContainsText_expectedOutput() throws IOException {
        String expected = "{\"deadline\":\"Jan 17, 2023 2:33:00 PM\"," +
                "\"attachments\":[],\"category\":\"APPOINTMENT\",\"creationDate\":\"Jan 17, 2023 2:33:24 PM\"," +
                "\"description\":\"\",\"id\":1,\"isHidden\":false,\"isSelected\":true,\"priority\":\"LOW\"," +
                "\"sketchData\":[],\"status\":\"NOT_STARTED\",\"taskColor\":\"#E1E1E1\",\"taskName\":\"dasd\"}\n";
        InputStream inputStream = new ByteArrayInputStream(expected.getBytes());

        Uri uri = Uri.parse("file://test.json");
        when(contentResolver.openInputStream(uri)).thenReturn(inputStream);

        String actual = fileContentExtractor.extractContent(uri);

        assertEquals(expected, actual);
    }
}