package at.ac.univie.se2_team_0308.models;

import java.util.ArrayList;
import java.util.Date;

public abstract class ATaskFactory {
    public Task getNewTask(String taskName, String description, EPriority priority, EStatus status, Date deadline, ArrayList<String> subtasks) {
        return createTask(taskName, description, priority, status, deadline, subtasks);
    }
    public abstract Task createTask(String taskName, String description, EPriority priority, EStatus status, Date deadline, ArrayList<String> subtasks);
}
