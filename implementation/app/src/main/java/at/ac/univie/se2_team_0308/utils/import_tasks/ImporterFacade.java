package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

/**
 ImporterFacade is a class that acts as a facade for importing tasks from various file formats.
 */
public class ImporterFacade {
    public static final String TAG = "ImporterFacade";
    private final TaskViewModel taskViewModel;
    private final ContentResolver contentResolver;
    private ITaskImporter importer;

    /**
     * Constructor for ImporterFacade class.
     *
     * @param taskViewModel a viewmodel that holds the task data
     * @param contentResolver a ContentResolver that is used to access the file to be imported
     */
    public ImporterFacade(TaskViewModel taskViewModel, ContentResolver contentResolver){
        this.taskViewModel = taskViewModel;
        this.contentResolver = contentResolver;
    }

    /**
     * Imports tasks from a file specified by the uri and synchronizes them with the task data in the viewmodel.
     *
     * @param uri a Uri that points to the file to be imported
     */
    public void importTasks(Uri uri){
        Log.d(TAG, "Importing tasks");
        FileContentExtractor contentExtractor = new FileContentExtractor(contentResolver);
        List<String> supportedFormats = Arrays.asList("xml", "json");

        try{
            String fileContent = contentExtractor.extractContent(uri);
            String filename = FilenameRetriever.getFilename(uri, contentResolver, supportedFormats);
            Pair<List<TaskAppointment>, List<TaskChecklist>> importedTasks;


            if(filename.contains("xml")){
                Log.d(TAG, "Importing xml");
                XmlTaskRetriever xmlTaskRetriever = new XmlTaskRetriever(fileContent);
                importer = new XmlImporter(xmlTaskRetriever);
            }
            else if(filename.contains("json")){
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
