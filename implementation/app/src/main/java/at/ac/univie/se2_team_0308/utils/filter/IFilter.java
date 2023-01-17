package at.ac.univie.se2_team_0308.utils.filter;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

/**
 * Interface to simplify filters implementation
 */
public interface IFilter {
    /**
     Interface has single function to apply filter to the task list.
     @param taskList list of ATask objects
     @return list of ATask with filter applied
     */
    List<ATask> applyFilter(List<ATask> taskList);
}
