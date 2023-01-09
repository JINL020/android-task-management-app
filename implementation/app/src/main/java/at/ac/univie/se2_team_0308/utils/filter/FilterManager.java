package at.ac.univie.se2_team_0308.utils.filter;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

public class FilterManager {
    public List<ATask> applyFilter(List<ATask> taskList, IFilter filter) {
        List<ATask> filteredList = new ArrayList<>(taskList);
        filteredList = filter.applyFilter(taskList);
        return filteredList;
    }
}
