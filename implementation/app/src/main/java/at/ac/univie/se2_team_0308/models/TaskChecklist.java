package at.ac.univie.se2_team_0308.models;

import java.util.ArrayList;

public class TaskChecklist extends ATask {

    ArrayList<String> subtasks;

    public TaskChecklist(String taskName, String description,  EPriority priority, EStatus status, ECategory category, ArrayList<String> subtasks){
        super(taskName, description, priority, status, category);
        this.subtasks = subtasks;
    }


}
