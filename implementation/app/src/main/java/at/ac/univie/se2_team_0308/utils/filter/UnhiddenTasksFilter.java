package at.ac.univie.se2_team_0308.utils.filter;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

public class UnhiddenTasksFilter implements IFilter {
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
