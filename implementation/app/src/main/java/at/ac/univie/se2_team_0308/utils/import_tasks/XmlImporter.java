package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;


import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class XmlImporter implements ITaskImporter {
    private final static String TAG = "XmlImpoter";
    private final XmlTaskRetriever xmlTaskRetriever;

    public XmlImporter(XmlTaskRetriever xmlTaskRetriever){
        this.xmlTaskRetriever = xmlTaskRetriever;
    }

    @Override
    public Pair<List<TaskAppointment>, List<TaskChecklist>> importTasks() {
        Log.d(TAG, "Converting imported tasks from xml to task objects");

        Pair<List<TaskAppointment>, List<TaskChecklist>> importedTasks = new Pair<>(new ArrayList<>(), new ArrayList<>());
        Pair<List<String>, List<String>> tasks = xmlTaskRetriever.getTasks();

        for(String eachAppointmentString: tasks.first){
            TaskAppointment taskAppointment = (TaskAppointment) convertATask(eachAppointmentString, TaskAppointment.class);
            importedTasks.first.add(taskAppointment);
            Log.d(TAG, taskAppointment.toString());
        }

        for(String eachChecklistString: tasks.second){
            TaskChecklist taskChecklist = (TaskChecklist) convertATask(eachChecklistString, TaskChecklist.class);
            importedTasks.second.add(taskChecklist);
            Log.d(TAG, taskChecklist.toString());
        }

        return importedTasks;
    }

    private ATask convertATask(String xml, Class<?> t) {
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
