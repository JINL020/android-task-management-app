package at.ac.univie.se2_team_0308.utils.filter;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

public class HiddenTasksFilter implements IFilter {
    @Override
    public List<ATask> applyFilter(List<ATask> taskList) {
        List<ATask> hiddenTasks = new ArrayList<>();
        for(ATask task : taskList) {
            if(task.isHidden()) {
                hiddenTasks.add(task);
            }
        }
        return hiddenTasks;
    }
}
