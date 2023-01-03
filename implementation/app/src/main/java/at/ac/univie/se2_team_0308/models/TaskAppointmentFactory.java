package at.ac.univie.se2_team_0308.models;

import java.util.Date;
import java.util.List;

public class TaskAppointmentFactory extends ATaskFactory {

    @Override
    public ATask createTask(String taskName, String description, EPriority priority, EStatus status, Date deadline, List<String> subtasks) {
        return new TaskAppointment(taskName, description, priority, status, ECategory.APPOINTMENT, deadline);
    }
}