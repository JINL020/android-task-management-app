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
        return null;
    }

    private String convertTaskAppointment(List<TaskAppointment> tasks) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<AllTasks>");
        for(ATask eachTask : tasks){
            String xml = convertTask(eachTask);
            stringBuilder.append(xml);
        }
        stringBuilder.append("<AllTasks>");
        return stringBuilder.toString();
    }
    private String convertTask(ATask task){
        XStream xstream = new XStream();
//        xstream.alias("attachment", Attachment.class);
        xstream.alias("category", ECategory.class);
//        xstream.alias("date", Date);
        xstream.alias("status", EStatus.class);
        xstream.alias("priority", EPriority.class);
//        xstream.alias("notificationService", NotificationService.class);
//        xstream.alias();
        String xml = xstream.toXML(task);
        return xml;
    }

    public String convertTaskChecklist(List<TaskChecklist> tasks) {
        return null;
    }
}
