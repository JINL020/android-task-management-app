package at.ac.univie.se2_team_0308.utils.filter;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

public interface IFilter {
    List<ATask> applyFilter(List<ATask> taskList);
}
