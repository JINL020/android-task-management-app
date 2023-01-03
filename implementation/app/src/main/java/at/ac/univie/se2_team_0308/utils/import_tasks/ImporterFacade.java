package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class ImporterFacade {
    public static final String TAG = "ImporterFacade";
    private final TaskViewModel taskViewModel;
    private final ContentResolver contentResolver;
    private TaskImporter importer;

    public ImporterFacade(TaskViewModel taskViewModel, ContentResolver contentResolver){
        this.taskViewModel = taskViewModel;
        this.contentResolver = contentResolver;
    }

    public void importTasks(Uri uri){
        Log.d(TAG, "Importing tasks");
        FileContentRetriever contentRetriever = new FileContentRetriever(contentResolver);

        try{
            String fileContent = contentRetriever.getFile(uri);
            String fileName = FilenameRetriever.getFilename(uri, contentResolver);
            Pair<List<TaskAppointment>, List<TaskChecklist>> importedTasks;

            Log.d(TAG, "File name is " + fileName);
            if(fileName.contains("xml")){
                Log.d(TAG, "Importing xml");
                XmlTaskRetriever xmlTaskRetriever = new XmlTaskRetriever(fileContent);
                importer = new XmlImporter(xmlTaskRetriever);
            }
            else if(fileName.contains("json")){
                Log.d(TAG, "Importing json");
                JsonTaskRetriever jsonTaskRetriever = new JsonTaskRetriever(fileContent);
                importer = new JsonImporter(jsonTaskRetriever);

            }
            importedTasks = importer.importTasks();

            TaskSynchronizer taskSynchronizer = new TaskSynchronizer();
            taskSynchronizer.synchronizeTasks(taskViewModel, importedTasks.first, importedTasks.second);
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
        }

    }
}
