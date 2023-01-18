package at.ac.univie.se2_team_0308.utils.notifications;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.views.DeadlinePassedException;

public interface IObserver {
    void receivedUpdate(ENotificationEvent event, ATask... tasks) throws DeadlinePassedException;
}
