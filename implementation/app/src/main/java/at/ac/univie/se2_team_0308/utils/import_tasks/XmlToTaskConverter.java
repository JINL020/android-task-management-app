package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;


import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

/**
 * XmlToTaskConverter is a class that imports tasks from an xml file and converts them into task objects.
 * It implements the IFileConverter interface.
 */
public class XmlToTaskConverter implements IFileConverter {
    private final static String TAG = "XmlToTaskConverter";
    private final XmlTaskRetriever xmlTaskRetriever;

    public XmlToTaskConverter(XmlTaskRetriever xmlTaskRetriever){
        this.xmlTaskRetriever = xmlTaskRetriever;
    }

    /**
     * Imports tasks from an xml file and converts them into task objects.
     * @return a Pair object containing two lists of task objects. The first list contains
     * TaskAppointment objects and the second list contains TaskChecklist objects.
     */
    @Override
    public Pair<List<TaskAppointment>, List<TaskChecklist>> convertTasks() {
        Log.d(TAG, "Converting imported tasks from xml to task objects");

        Pair<List<TaskAppointment>, List<TaskChecklist>> importedTasks = new Pair<>(new ArrayList<>(), new ArrayList<>());
        Pair<List<String>, List<String>> tasks = xmlTaskRetriever.getTasks();

        for(String eachAppointmentString: tasks.first){
            TaskAppointment taskAppointment = (TaskAppointment) convertString(eachAppointmentString, TaskAppointment.class);
            importedTasks.first.add(taskAppointment);
            Log.d(TAG, taskAppointment.toString());
        }

        for(String eachChecklistString: tasks.second){
            TaskChecklist taskChecklist = (TaskChecklist) convertString(eachChecklistString, TaskChecklist.class);
            importedTasks.second.add(taskChecklist);
            Log.d(TAG, taskChecklist.toString());
        }

        return importedTasks;
    }

    private ATask convertString(String xml, Class<?> classType) {
        XStream xstream = new XStream();

        ATask task;
        if(classType == TaskAppointment.class) {
            task = (TaskAppointment) xstream.fromXML(xml);
        }
        else {
            assert classType == TaskChecklist.class;

            task = (TaskChecklist) xstream.fromXML(xml);
        }

        return task;
    }
}
