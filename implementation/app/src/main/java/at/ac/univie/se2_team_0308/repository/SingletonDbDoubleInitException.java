package at.ac.univie.se2_team_0308.repository;

public class SingletonDbDoubleInitException extends Exception {
    public SingletonDbDoubleInitException() {
        super("Tried to initialise database two times.");
    }

    public SingletonDbDoubleInitException(String message) {
        super(message);
    }
}
