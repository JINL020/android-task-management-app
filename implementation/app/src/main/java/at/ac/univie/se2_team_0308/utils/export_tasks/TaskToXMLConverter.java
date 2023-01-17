package at.ac.univie.se2_team_0308.utils.export_tasks;

import com.thoughtworks.xstream.XStream;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

/**
 TaskToXMLConverter class converts the given list of TaskAppointment and TaskChecklist objects to a XML format string.
 This class is an implementation of ITaskConverter interface.
 */
public class TaskToXMLConverter implements ITaskConverter{
    /**
     Converts the given list of TaskAppointment and TaskChecklist objects to a XML format string.
     @param taskAppointment list of TaskAppointment objects
     @param taskChecklists list of TaskChecklist objects
     @return the converted XML format string
     */
    @Override
    public String convertTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists) {
        // Create and append beginning of xml array to xml string
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<AllTasks>");

        // Convert and append TaskAppointment objects to xml string
        for(TaskAppointment eachTask : taskAppointment){
          String convertedTask = composeTask(eachTask);
            stringBuilder.append(convertedTask);
        }

        // Add new line between different types of tasks
        stringBuilder.append(System.getProperty("line.separator"));

        // Convert and append TaskChecklist objects to xml string
        for(TaskChecklist eachTask : taskChecklists){
            String convertedTask = composeTask(eachTask);
            stringBuilder.append(convertedTask);
        }

        // Create and append end of xml array to xml string
        stringBuilder.append("</AllTasks>");

        return stringBuilder.toString();
    }

    // Compose the xml string with new line
    private String composeTask(ATask task){
        StringBuilder stringBuilder = new StringBuilder();
        String xml = convertATask(task);
        stringBuilder.append(xml);
        stringBuilder.append(System.getProperty("line.separator"));

        return stringBuilder.toString();
    }

    // Convert ATask representation to TaskChecklist/TaskAppointment
    // representation in xml
    private String convertATask(ATask task){
        XStream xstream = new XStream();
        String xml = xstream.toXML(task);

        return xml;
    }
}
