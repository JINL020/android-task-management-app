package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class responsible for extracting the content as a String
 * of a file passed as an Uri.
 *
 * The class takes a ContentResolver object as constructor parameter.
 */
public class FileContentExtractor {
    public static final String TAG = "FileContentExtractor";
    private final ContentResolver contentResolver;

    public FileContentExtractor(ContentResolver contentResolver){
        this.contentResolver = contentResolver;
    }

    /**
     * Method used to extract content from a file. If the extraction throws an error,
     * the content returned will be an empty string
     *
     * @param  uri to the file that we should extract the content from
     * @return content of the file as a String
     */
    public String extractContent(Uri uri) {
        InputStream inputStream;
        String result = "";
        try {
            inputStream = contentResolver.openInputStream(uri);
            result = convertStreamToString(inputStream);

            Log.d(TAG, result);
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e){
            Log.e(TAG, e.toString());
        }

        return result;
    }

    /**
     * The method takes an InputStream object and retrieves its String content
     *
     * @param  inputStream resulted from the Uri and ContentResolver
     * @return the content of the file retrived from the InputStream as a String
     * @see <a href="https://stackoverflow.com/questions/12910503/read-file-as-string"> The code was inspired from here </a>
     * @throws IOException if the file is not found or an error occurred while reading or trying to close the file
     *
     */
    private String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder
                    .append(line)
                    .append(System.getProperty("line.separator"));
        }
        reader.close();

        return stringBuilder.toString();
    }

}
