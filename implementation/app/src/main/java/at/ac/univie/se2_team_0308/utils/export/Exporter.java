package at.ac.univie.se2_team_0308.utils.export;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class Exporter {
    private static final String TAG = "Exporter";
    private static int currVersion = 0;

    public void exportTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists, EFormat format){
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
        writeToFile(convertedFile, fileName);
    }

    private String composeName(String extension){
        Log.d(TAG, "Compose file name");

        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append("tasks");
        fileNameBuilder.append("_");
        fileNameBuilder.append(currVersion++);
        fileNameBuilder.append(".");
        fileNameBuilder.append(extension);

        Log.d(TAG, "File name is " + fileNameBuilder);
        return fileNameBuilder.toString();
    }

    private void writeToFile(String convertedFile, String fileName){
        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, fileName);
            file.createNewFile();
            Log.d(TAG, "Directory to be saved in " + file);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputStreamWriter.write(convertedFile);
            outputStreamWriter.close();

            Log.d(TAG, "File written to system");
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e);
        }
    }
}
