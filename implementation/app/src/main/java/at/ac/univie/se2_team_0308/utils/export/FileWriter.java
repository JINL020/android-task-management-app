package at.ac.univie.se2_team_0308.utils.export;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class FileWriter {
    private static final String TAG = "FileWriter";

    public void writeToFile(String convertedFile, String extension){
        assert (Objects.equals(extension, "xml")) || (Objects.equals(extension, "json"));

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String fileName = composeName(extension);
            File file = new File(path, fileName);
            file.createNewFile();
            Log.d(TAG, "Directory to be saved in " + file);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputStreamWriter.write(convertedFile);
            outputStreamWriter.close();

            Log.d(TAG, "File written to system");
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e);
        }
    }

    private String composeName(String extension){
        Log.d(TAG, "Compose file name");

        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append("tasks");
        fileNameBuilder.append(".");
        fileNameBuilder.append(extension);

        Log.d(TAG, "File name is " + fileNameBuilder);
        return fileNameBuilder.toString();
    }
}
