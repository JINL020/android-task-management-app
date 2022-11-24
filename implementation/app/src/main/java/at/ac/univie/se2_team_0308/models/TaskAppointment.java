package at.ac.univie.se2_team_0308.models;
import java.util.Date;

public class TaskAppointment extends ATask {

    private Date deadline;

    public TaskAppointment(String taskName, String description, EPriority priority, EStatus status, ECategory category, Date deadline){
        super(taskName, description, priority, status, category);
        this.deadline = deadline;
    }

    public Date getDeadline(){
        return deadline;
    }

    public void setDeadline(Date deadline){
        this.deadline = deadline;
    }
}
