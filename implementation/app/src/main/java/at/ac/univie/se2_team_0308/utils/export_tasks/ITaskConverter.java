package at.ac.univie.se2_team_0308.utils.export_tasks;

import java.util.List;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

/**
 * ITaskConverter is an interface that defines a method for converting a list of TaskAppointment and
 * a list of TaskChecklists into a single String whose format depends on the implementation of the
 * interface.
 */
public interface ITaskConverter {
    /**
     * Converts the given list of TaskAppointment and TaskChecklist objects to a string formatted
     * according to the implementation of the interface.
     * @param taskAppointment list of TaskAppointment objects
     * @param taskChecklists  list of TaskChecklist objects
     * @return                the converted JSON format string
     */
    public String convertTasks(List<TaskAppointment> taskAppointment, List<TaskChecklist> taskChecklists);
}
