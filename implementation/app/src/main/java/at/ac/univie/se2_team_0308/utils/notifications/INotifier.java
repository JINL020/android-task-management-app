package at.ac.univie.se2_team_0308.utils.notifications;

import android.content.Context;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.ENotifier;

public interface INotifier {
    void sendNotification(ENotificationEvent event, String message, Context context);

    List<ENotifier> getNotifierType();
}
