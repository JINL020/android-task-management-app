package at.ac.univie.se2_team_0308.utils.notifications;

import java.util.HashMap;
import java.util.Map;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;

public class NotificationSysytem {
    private Map<ENotificationEvent, IObserver> observers;

    public NotificationSysytem() {
        this.observers = new HashMap<>();
    }

    public void attach(ENotificationEvent event, IObserver observer) {
        observers.put(event, observer);
    }

    public void detach(ENotificationEvent event) {
        observers.remove(event);
    }

    public void notify(ENotificationEvent event, ATask task) {
        observers.get(event).update(event, task);
    }

}
