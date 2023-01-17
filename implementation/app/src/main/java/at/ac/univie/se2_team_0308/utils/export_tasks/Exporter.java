package at.ac.univie.se2_team_0308.utils.export;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

/**
 The Exporter class is responsible for exporting tasks in the specified format.
 It can export the tasks in either XML or JSON format.
 It uses the ITaskConverter interface to convert the tasks into the specified format.
 It then writes the converted tasks to a file in the downloads directory of the device.
 */
public class Exporter {
    private static final String TAG = "Exporter";

    /**
     Exports the provided taskAppointment and taskChecklists in the specified format.
     @param taskAppointment A list of TaskAppointment objects to be exported
     @param taskChecklists A list of TaskChecklist objects to be exported
     @param format The format in which the tasks should be exported (XML or JSON)
     */
    public void exportTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists, EFormat format){
        Log.d(TAG, "Export tasks");

        ITaskConverter taskConverter;
        String filename;

        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

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

        filename = FilenameComposer.composeName(extension, date);
        String convertedFile = taskConverter.convertTasks(taskAppointment, taskChecklists);
        Log.d(TAG, "Content of converted file: " + convertedFile);
        writeToFile(convertedFile, filename);
    }

    // TODO: add java doc??
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
