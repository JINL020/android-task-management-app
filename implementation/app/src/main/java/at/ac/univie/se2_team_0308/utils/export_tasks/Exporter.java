package at.ac.univie.se2_team_0308.utils.export_tasks;

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
 * The Exporter class is responsible for exporting tasks in the specified format.
 * Supported formats are defined in the enum EFormat.
 * It uses the ITaskConverter interface to convert the tasks into the specified format.
 * It then writes the converted tasks to a file in the downloads directory of the device.
 */
public class Exporter {
    private static final String TAG = "Exporter";
    /**
     * Exports the provided taskAppointment and taskChecklists in the specified format.
     * @param taskAppointment list of TaskAppointment objects to be exported
     * @param taskChecklists  list of TaskChecklist objects to be exported
     * @param format          format in which the tasks should be exported (Xml or Json)
     */
    public void exportTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists, EFormat format){
        ITaskConverter taskConverter;
        String filename;
        String extension;

        if(format == EFormat.XML){
            Log.d(TAG, "Format is XML");
            taskConverter = new TaskToXmlConverter();
            extension = "xml";
        }
        else{
            assert format == EFormat.JSON;

            Log.d(TAG, "Format is JSON");
            taskConverter = new TaskToJsonConverter();
            extension = "json";
        }

        // Create date that will be appended to the filename in order to distinguish between files
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Calendar.getInstance().getTime());

        // Create filename based on extension and date
        filename = FilenameComposer.composeName(extension, date);

        // Convert tasks to a string formatted based on the chosen extension
        String fileContent = taskConverter.convertTasks(taskAppointment, taskChecklists);
        Log.d(TAG, "Content of converted file: " + fileContent);

        writeToFile(fileContent, filename);
    }

    private void writeToFile(String fileContent, String fileName){
        try {
            // Downloads directory in local storage chosen as directory to export to
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, fileName);
            file.createNewFile();
            Log.d(TAG, "Directory to be saved in " + file);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputStreamWriter.write(fileContent);
            outputStreamWriter.close();

            Log.d(TAG, "File written to system");
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e);
        }
    }
}
