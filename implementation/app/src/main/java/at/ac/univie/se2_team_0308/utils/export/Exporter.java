package at.ac.univie.se2_team_0308.utils.export;

import android.content.Context;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class Exporter {
    // TODO: add path
    private static final String PATH = "";

    public void exportTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists, EFormat format){
        ITaskConverter taskConverter;
        if(format == EFormat.XML){
            taskConverter = new TaskToXMLConverter();
        }
        else{
            taskConverter = new TaskToJSONConverter();

            // TODO: assert this if it's not JSON
        }

//        String convertedFile = taskConverter.convertTasks(taskAppointment, taskChecklists);
//        writeToFile(convertedFile, context);
    }

    // Following code snippet was taken from this URL
    // https://stackoverflow.com/questions/14376807/read-write-string-from-to-a-file-in-android
    private void writeToFile(String convertedFile, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(convertedFile);
            outputStreamWriter.close();
        }
        catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
        }

    }
}
