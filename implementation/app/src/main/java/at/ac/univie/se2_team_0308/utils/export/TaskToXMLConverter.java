package at.ac.univie.se2_team_0308.utils.export;

import com.thoughtworks.xstream.XStream;

import java.util.List;
import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class TaskToXMLConverter implements ITaskConverter{
    @Override
    public String convertTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<AllTasks>");
        for(TaskAppointment eachTask : taskAppointment){
            String xml = convertTaskAppointment(eachTask);
            stringBuilder.append(xml);
        }
        for(TaskChecklist eachTask : taskChecklists){
            String xml = convertTaskChecklist(eachTask);
            stringBuilder.append(xml);
        }
        stringBuilder.append("</AllTasks>");
        return stringBuilder.toString();
    }

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
