package at.ac.univie.se2_team_0308.models;

import java.util.Date;
import java.util.List;

public abstract class ATaskFactory {
    public ATask getNewTask(String taskName, String description, EPriority priority, EStatus status, Date deadline, List<ASubtask> subtasks, List<Attachment> attachments) {
        return createTask(taskName, description, priority, status, deadline, subtasks, attachments);
    }
    public abstract ATask createTask(String taskName, String description, EPriority priority, EStatus status, Date deadline, List<ASubtask> subtasks, List<Attachment> attachments);
}
