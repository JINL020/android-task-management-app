package at.ac.univie.se2_team_0308.utils.export;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class Exporter {
    private static final String TAG = "Exporter";
    private static int currVersion = 0;

    public void exportTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists, EFormat format, Context context){
        Log.d(TAG, "Export tasks");

        ITaskConverter taskConverter;
        String fileName;

        if(format == EFormat.XML){
            Log.d(TAG, "Format is XML");
            taskConverter = new TaskToXMLConverter();
            fileName = composeName("xml");
        }
        else{
            assert format == EFormat.JSON;

            Log.d(TAG, "Format is JSON");
            taskConverter = new TaskToJSONConverter();
            fileName = composeName("json");
        }

        String convertedFile = taskConverter.convertTasks(taskAppointment, taskChecklists);
        Log.d(TAG, "Content of converted file: " + convertedFile);
        writeToFile(convertedFile, fileName, context);
    }

    private String composeName(String extension){
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append("tasks");
        fileNameBuilder.append("_");
        fileNameBuilder.append(String.valueOf(currVersion++));
        fileNameBuilder.append(".");
        fileNameBuilder.append(extension);

        return fileNameBuilder.toString();
    }

    // Following code snippet was taken from this URL
    // https://stackoverflow.com/questions/14376807/read-write-string-from-to-a-file-in-android
    private void writeToFile(String convertedFile, String fileName, Context context){
        try {
           OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(convertedFile);
            outputStreamWriter.close();

            Log.d(TAG, "File written to system");
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }

    }
}
