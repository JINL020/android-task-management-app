package at.ac.univie.se2_team_0308.views;

public class TaskAlreadyExistsException extends Exception {
    public TaskAlreadyExistsException(String message) {
        super(message);
    }

    public TaskAlreadyExistsException() {
        super("The Task you're trying to insert already exists in the database");
    }
}
