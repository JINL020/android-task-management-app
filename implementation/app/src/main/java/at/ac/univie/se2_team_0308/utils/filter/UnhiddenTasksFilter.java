package at.ac.univie.se2_team_0308.utils.filter;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

/**
 * Class for implementation IFilter interface to get unhidden tasks
 */
public class UnhiddenTasksFilter implements IFilter {
    /**
     * Single function to apply filter to the task list
     * @param taskList list of ATask objects
     * @return list of unhidden tasks
     */
    @Override
    public List<ATask> applyFilter(List<ATask> taskList) {
        List<ATask> unhiddenTasks = new ArrayList<>();
        for(ATask task : taskList) {
            if(!task.isHidden()) {
                unhiddenTasks.add(task);
            }
        }
        return unhiddenTasks;
    }
}
