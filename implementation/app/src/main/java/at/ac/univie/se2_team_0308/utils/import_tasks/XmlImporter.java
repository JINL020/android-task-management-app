package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;
import android.util.Pair;


import com.thoughtworks.xstream.XStream;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class XmlImporter implements TaskImporter{
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
            TaskAppointment taskAppointment = convertTaskAppointment(eachAppointmentString);
            importedTasks.first.add(taskAppointment);
            Log.d(TAG, taskAppointment.toString());
        }

        for(String eachChecklistString: tasks.second){
            TaskChecklist taskChecklist = convertTaskChecklist(eachChecklistString);
            importedTasks.second.add(taskChecklist);
            Log.d(TAG, taskChecklist.toString());
        }

        return importedTasks;
    }

    private TaskAppointment convertTaskAppointment(String xml) {
        XStream xstream = new XStream();
        xstream.alias("at.ac.univie.se2__team__0308.models.TaskAppointment", TaskAppointment.class);
        xstream.alias("category", ECategory.class);
        xstream.alias("status", EStatus.class);
        xstream.alias("priority", EPriority.class);
        TaskAppointment task = (TaskAppointment) xstream.fromXML(xml);
        return task;
    }

    private TaskChecklist convertTaskChecklist(String xml){
        XStream xstream = new XStream();
        xstream.alias("at.ac.univie.se2__team__0308.models.TaskChecklist", TaskChecklist.class);
        xstream.aliasField("category", ECategory.class, "category");
        xstream.aliasField("status", EStatus.class, "status");
        xstream.aliasField("priority", EPriority.class, "priority");
        TaskChecklist task = (TaskChecklist) xstream.fromXML(xml);
        return task;
    }
}
