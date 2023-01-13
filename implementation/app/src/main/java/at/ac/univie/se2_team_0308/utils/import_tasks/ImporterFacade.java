package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class ImporterFacade {
    public static final String TAG = "ImporterFacade";
    private final TaskViewModel taskViewModel;
    private final ContentResolver contentResolver;
    private ITaskImporter importer;

    public ImporterFacade(TaskViewModel taskViewModel, ContentResolver contentResolver){
        this.taskViewModel = taskViewModel;
        this.contentResolver = contentResolver;
    }

    public void importTasks(Uri uri){
        Log.d(TAG, "Importing tasks");
        FileContentExtractor contentRetriever = new FileContentExtractor(contentResolver);

        try{
            String fileContent = contentRetriever.extractContent(uri);
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
        catch (UnsupportedDocumentFormatException | ParserConfigurationException | IOException | SAXException e){
            Log.e(TAG, e.toString());
        }

    }
}
