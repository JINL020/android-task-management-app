package at.ac.univie.se2_team_0308.utils.notifications;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.ENotifier;

public class LoggerCore implements INotifier {
    private static final String TAG = "LOGGER_CORE";

    @Override
    public void sendNotification(ENotificationEvent event, String message) {
        Log.d(event.name(), message);
        Log.d(TAG, "send out notification: " + event.name() + " " + message);
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> ret = new ArrayList<ENotifier>();
        ret.add(ENotifier.LOGGER);
        return ret;
    }
}
