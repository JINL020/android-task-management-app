package at.ac.univie.se2_team_0308.utils.export;

import android.util.Log;

public class FilenameComposer {
    private static final String TAG = "FilenameComposer";
    /**
     Composes a file name using the specified extension.
     @param extension The extension to be used in the file name (e.g. "xml" or "json")
     @return The composed file name
     */
    public static String composeName(String extension, String date){
        Log.d(TAG, "Compose file name");

        StringBuilder filenameBuilder = new StringBuilder();
        filenameBuilder.append("tasks");
        filenameBuilder.append("_");
        filenameBuilder.append(date);
        filenameBuilder.append(".");
        filenameBuilder.append(extension);

        Log.d(TAG, "File name is " + filenameBuilder);
        return filenameBuilder.toString();
    }
}
