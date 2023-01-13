package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.util.List;

public class FilenameRetriever {
    /**
     *
     */
    public static String getFilename(Uri uri, ContentResolver contentResolver, List<String> supportedFormats) throws UnsupportedDocumentFormatException{
        @SuppressLint("Recycle") Cursor cursor = contentResolver.query(uri,null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        String filename =  cursor.getString(nameIndex);

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
