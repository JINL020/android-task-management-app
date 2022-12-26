package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class ImporterFacade {
    public static final String TAG = "ImporterFacade";
    private final TaskViewModel taskViewModel;
    private ContentResolver contentResolver;

    public ImporterFacade(TaskViewModel taskViewModel, ContentResolver contentResolver){
        this.taskViewModel = taskViewModel;
        this.contentResolver = contentResolver;
    }

    public void importTasks(Uri uri){
        Log.d(TAG, "Importing tasks");
        FileContentRetriever contentRetriever = new FileContentRetriever(contentResolver);

        try{
            String fileContent = contentRetriever.getFile(uri);
            Log.d(TAG, fileContent);
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }
}
