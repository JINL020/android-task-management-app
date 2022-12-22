package at.ac.univie.se2_team_0308.utils.export;

import java.util.List;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public interface ITaskConverter {
    public String convertTaskAppointment(List<TaskAppointment> tasks, EFormat format);
    public String convertTaskChecklist(List<TaskChecklist> tasks, EFormat format);
}
