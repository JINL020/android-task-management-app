package at.ac.univie.se2_team_0308.utils.export;

import com.thoughtworks.xstream.XStream;

import java.util.List;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<AllTasks>");
        for(TaskAppointment eachTask : taskAppointment){
            String xml = convertTaskAppointment(eachTask);
            stringBuilder.append(xml);
            stringBuilder.append(System.getProperty("line.separator"));
        }

        stringBuilder.append(System.getProperty("line.separator"));

        for(TaskChecklist eachTask : taskChecklists){
            String xml = convertTaskChecklist(eachTask);
            stringBuilder.append(xml);
            stringBuilder.append(System.getProperty("line.separator"));
        }
        stringBuilder.append("</AllTasks>");
        return stringBuilder.toString();
    }

    // TODO: remove repetitive code
    private String convertTaskAppointment(TaskAppointment task) {
        XStream xstream = new XStream();
        xstream.alias("category", ECategory.class);
        xstream.alias("status", EStatus.class);
        xstream.alias("priority", EPriority.class);
        String xml = xstream.toXML(task);
        return xml;
    }

    private String convertTaskChecklist(TaskChecklist task){
        XStream xstream = new XStream();
        xstream.alias("category", ECategory.class);
        xstream.alias("status", EStatus.class);
        xstream.alias("priority", EPriority.class);
        String xml = xstream.toXML(task);
        return xml;
    }
}
