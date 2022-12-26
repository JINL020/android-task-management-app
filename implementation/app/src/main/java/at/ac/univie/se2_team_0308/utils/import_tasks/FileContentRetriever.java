package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

// TODO catch exception
public class FileContentRetriever {
    public static final String TAG = "FileContentRetriever";
    private final ContentResolver contentResolver;

    public FileContentRetriever(ContentResolver contentResolver){
        this.contentResolver = contentResolver;
    }

    public String getFile(Uri uri) throws Exception {
        InputStream inputStream;
        String result = "";
        try {
            inputStream = contentResolver.openInputStream(uri);
            String ret = convertStreamToString(inputStream);

            Log.d(TAG, ret);
            inputStream.close();

        } catch (FileNotFoundException e) {
            Log.d(TAG, e.toString());
        }

        return result;
    }
// Taken from https://stackoverflow.com/questions/12910503/read-file-as-string

    public String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

}
