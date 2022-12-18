package at.ac.univie.se2_team_0308.utils.export;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

public interface ITaskConverter {
    public String convertTasks(List<ATask> tasks, EFormat format);
}
