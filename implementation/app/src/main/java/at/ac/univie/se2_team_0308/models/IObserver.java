package at.ac.univie.se2_team_0308.models;

public interface IObserver {
    void receivedUpdate(ENotificationEvent event, ATask... tasks);
}
