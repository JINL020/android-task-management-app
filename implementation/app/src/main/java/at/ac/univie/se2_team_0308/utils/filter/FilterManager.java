package at.ac.univie.se2_team_0308.utils.filter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

/**
 * Filter Manager is class to manage filters and to simplify the task of using them.
 */

public class FilterManager {

    /**
     Class has single function to apply the chosen filter to the task list.
     @param taskList list of ATask objects
     @param filter filter interface
     @return list of ATask with filter applied
     */

    public List<ATask> applyFilter(@NonNull List<ATask> taskList, IFilter filter) {
        List<ATask> filteredList = new ArrayList<>(taskList);
        filteredList = filter.applyFilter(taskList);
        return filteredList;
    }
}
