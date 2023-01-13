package at.ac.univie.se2_team_0308.views;

import java.util.Date;

public class DeadlinePassedException extends  Exception{
    private Date currentTime;
    private Date deadline;

    public DeadlinePassedException(Date currentTime, Date deadline) {
        this.currentTime = currentTime;
        this.deadline = deadline;
    }

    public String getErrorMessage() {
        String s = "";
        s += "The deadline has already passed, no alarm was set\n";
        s += "current time: " + currentTime + "\n";
        s += "deadline: " + deadline + "\n";
        return s;
    }
}
