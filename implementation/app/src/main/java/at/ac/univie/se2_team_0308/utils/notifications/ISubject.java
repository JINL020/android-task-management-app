package at.ac.univie.se2_team_0308.utils.notifications;

import at.ac.univie.se2_team_0308.models.ATask;

public interface ISubject {
    void attachObserver(IObserver observer);

    void detachObserver(IObserver observer);

    void notifyObservers(ENotificationEvent event, ATask... tasks);
}
