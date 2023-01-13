package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import java.util.List;

public class FilenameRetriever {
    public static final String TAG = "FilenameRetriever";
    /**
     * Retrieves the name of a file from an Uri
     *
     * @param uri               the identifier to the file to be imported
     * @param contentResolver   facilitates the interaction with the data outside the app
     * @param supportedFormats  a list of strings that specifies which document formats are supported
     * @return                  the name of the file name as String
     * @throws UnsupportedDocumentFormatException if the file name indicates that the format is not among the supported format
     */
    public static String getFilename(Uri uri, ContentResolver contentResolver, List<String> supportedFormats) throws UnsupportedDocumentFormatException{
        Log.d(TAG, "Retrieve filename");

        // Query the table of the given Uri and get the information at column OpenableColumns.DISPLAY_NAME
        @SuppressLint("Recycle") Cursor cursor = contentResolver.query(uri,null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();

        String filename =  cursor.getString(nameIndex);
        Log.d(TAG, "File name is " + filename);

        // Check if the file name contains any of the supported format
        boolean formatSupported = false;
        for(String eachSupportedFormat: supportedFormats){
            if(filename.contains(eachSupportedFormat)){
                formatSupported = true;
                break;
            }
        }

        if(!formatSupported)
            throw new UnsupportedDocumentFormatException();

        return filename;
    }

}
