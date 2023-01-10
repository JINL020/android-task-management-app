package at.ac.univie.se2_team_0308.utils.notifications;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.ENotifier;

public class LoggerCore implements INotifier {
    private static final String TAG = "LOGGER_CORE";

    @Override
    public void sendNotification(Context context, ENotificationEvent event, ATask... tasks) {
        for(ATask task : tasks){
            Log.d(event.name(), task.getTaskName());
        }
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> ret = new ArrayList<ENotifier>();
        ret.add(ENotifier.LOGGER);
        return ret;
    }
}
