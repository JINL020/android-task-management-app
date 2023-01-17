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
 XmlImporter is a class that imports tasks from an xml file and converts them into task objects.
 It implements the ITaskImporter interface.
 */
public class XmlImporter implements ITaskImporter {
    private final static String TAG = "XmlImpoter";
    private final XmlTaskRetriever xmlTaskRetriever;

    public XmlImporter(XmlTaskRetriever xmlTaskRetriever){
        this.xmlTaskRetriever = xmlTaskRetriever;
    }

    /**
     * Imports tasks from an xml file and converts them into task objects.
     *
     * @return a Pair object containing two lists of task objects. The first list contains TaskAppointment objects and
     * the second list contains TaskChecklist objects.
     */
    @Override
    public Pair<List<TaskAppointment>, List<TaskChecklist>> importTasks() {
        Log.d(TAG, "Converting imported tasks from xml to task objects");

        Pair<List<TaskAppointment>, List<TaskChecklist>> importedTasks = new Pair<>(new ArrayList<>(), new ArrayList<>());
        Pair<List<String>, List<String>> tasks = xmlTaskRetriever.getTasks();

        for(String eachAppointmentString: tasks.first){
            TaskAppointment taskAppointment = (TaskAppointment) convertString(eachAppointmentString, TaskAppointment.class);
            importedTasks.first.add(taskAppointment);
//            Log.d(TAG, taskAppointment.toString());
        }

        for(String eachChecklistString: tasks.second){
            TaskChecklist taskChecklist = (TaskChecklist) convertString(eachChecklistString, TaskChecklist.class);
            importedTasks.second.add(taskChecklist);
//            Log.d(TAG, taskChecklist.toString());
        }

        return importedTasks;
    }

    // TODO: check if private methods should have javadocs as well
    /**
     * Converts the string representation of a task in the xml file to a task object.
     *
     * @param xml a string representation of the task in the xml file
     * @param t the class of the task object to be converted. This can be either TaskAppointment.class or TaskChecklist.class
     * @return a task object of the specified class
     */
    private ATask convertString(String xml, Class<?> t) {
        XStream xstream = new XStream();

        ATask task;
        if(t == TaskAppointment.class) {
            task = (TaskAppointment) xstream.fromXML(xml);
        }
        else {
            assert t == TaskChecklist.class;

            task = (TaskChecklist) xstream.fromXML(xml);
        }

        return task;
    }
}
