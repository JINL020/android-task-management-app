package at.ac.univie.se2_team_0308.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskChecklistFactory extends ATaskFactory {

    @Override
    public ATask createTask(String taskName, String description, EPriority priority, EStatus status, Date deadline, List<String> subtasks) {
        subtasks = new ArrayList<>();
        subtasks.add("Subtask1");
        subtasks.add("Subtask2");
        subtasks.add("Subtask3");
        return new TaskChecklist(taskName, description, priority, status, ECategory.CHECKLIST, subtasks);
    }
}