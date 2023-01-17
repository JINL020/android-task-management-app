package at.ac.univie.se2_team_0308.utils.export_tasks;

import java.util.List;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public interface ITaskConverter {
    public String convertTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists);
}
