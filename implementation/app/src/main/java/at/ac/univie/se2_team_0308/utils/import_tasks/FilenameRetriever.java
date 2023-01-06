package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

public class FilenameRetriever {
    public static String getFilename(Uri uri, ContentResolver contentResolver) throws UnsupportedDocumentFormatException{
        Cursor cursor = contentResolver.query(uri,null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        String filename =  cursor.getString(nameIndex);

        if(!filename.contains("xml") && !filename.contains("json"))
            throw new UnsupportedDocumentFormatException();

        return filename;
    }

}
