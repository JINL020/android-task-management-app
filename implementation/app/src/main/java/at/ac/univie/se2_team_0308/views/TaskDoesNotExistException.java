package at.ac.univie.se2_team_0308.views;

public class TaskDoesNotExistException extends Exception {
    public TaskDoesNotExistException() {
        super("The Task you're trying to update does not exist in the database.");
    }

    public TaskDoesNotExistException(String message) {
        super(message);
    }
}
