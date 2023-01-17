package at.ac.univie.se2_team_0308.utils.export_tasks;

import android.util.Log;

/**
*   FilenameComposer composes the name of the file by concatenating "tasks" with current date and
 *   an extension.
*/
public class FilenameComposer {
    private static final String TAG = "FilenameComposer";
    /**
     * Composes a file name using the specified extension.
     * @param extension The extension to be used in the file name (e.g. "xml" or "json")
     * @param date      The current date used in order to differentiate between files
     * @return          The composed file name
     */
    public static String composeName(String extension, String date)  {
        Log.d(TAG, "Compose file name");

        if(date.isEmpty()){
            Log.e(TAG, "Empty date in composeName");
            throw new IllegalArgumentException("Date is empty");
        }

        if(extension.isEmpty()){
            Log.e(TAG, "Empty extension in composeName");
            throw new IllegalArgumentException("Extension is empty");
        }

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
