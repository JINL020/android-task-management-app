package at.ac.univie.se2_team_0308.utils.notifications;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;

public class LoggerCore implements INotifier {
    @Override
    public void sendNotification(ENotificationEvent event, String message) {
        Log.i(event.name(), ": " + message);
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> ret = new ArrayList<ENotifier>();
        ret.add(ENotifier.LOGGER);
        return ret;
    }
}
