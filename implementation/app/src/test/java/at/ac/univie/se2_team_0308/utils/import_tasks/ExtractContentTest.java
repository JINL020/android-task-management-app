package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.net.Uri;
import android.provider.MediaStore;

import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class ExtractContentTest extends TestCase {
    private FileContentExtractor fileContentExtractor;
    private Uri fileUri;

    @Before
    public void setUp() {
        fileContentExtractor = new FileContentExtractor(InstrumentationRegistry.getInstrumentation().getContext().getContentResolver());
        fileUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/1");
    }

    @Test
    public void testExtractContent() {
        String content = fileContentExtractor.extractContent(fileUri);
        assertNotNull(content);
    }
//    @Test
//    public void testConvertStreamToString() {
//        InputStream inputStream = InstrumentationRegistry.getInstrumentation().getContext().getContentResolver().openInputStream(fileUri);
//        String content = fileContentExtractor.convertStreamToString(inputStream);
//        assertNotNull(content);
//    }
}