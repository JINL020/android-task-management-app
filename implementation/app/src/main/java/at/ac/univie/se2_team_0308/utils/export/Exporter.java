package at.ac.univie.se2_team_0308.utils.export;

import android.util.Log;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class Exporter {
    private static final String TAG = "Exporter";
    private final FileWriter fileWriter = new FileWriter();

    public void exportTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists, EFormat format){
        Log.d(TAG, "Export tasks");

        ITaskConverter taskConverter;
        String extension;

        if(format == EFormat.XML){
            Log.d(TAG, "Format is XML");
            taskConverter = new TaskToXMLConverter();
            extension = "xml";
        }
        else{
            assert format == EFormat.JSON;

            Log.d(TAG, "Format is JSON");
            taskConverter = new TaskToJSONConverter();
            extension = "json";
        }

        Log.d(TAG, String.valueOf(EPriority.LOW));
        Log.d(TAG, String.valueOf(EStatus.NOT_STARTED));
        Log.d(TAG, String.valueOf(ECategory.APPOINTMENT));

        String convertedFile = taskConverter.convertTasks(taskAppointment, taskChecklists);
        Log.d(TAG, "Content of converted file: " + convertedFile);
        fileWriter.writeToFile(convertedFile, extension);
    }
}
