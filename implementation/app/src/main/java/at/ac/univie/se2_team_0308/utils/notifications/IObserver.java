package at.ac.univie.se2_team_0308.utils.notifications;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;

public interface IObserver {
    void update(ENotificationEvent event, ATask... task);

    List<ENotifier> getNotifierType();
}
