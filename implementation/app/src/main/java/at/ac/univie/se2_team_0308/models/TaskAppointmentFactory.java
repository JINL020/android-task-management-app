package at.ac.univie.se2_team_0308.models;

import java.util.ArrayList;
import java.util.Date;

public class TaskAppointmentFactory extends ATaskFactory {

    @Override
    public Task createTask(String taskName, String description, EPriority priority, EStatus status, Date deadline, ArrayList<String> subtasks) {
        return new TaskAppointment(taskName, description, priority, status, ECategory.APPOINTMENT, deadline);
    }
}