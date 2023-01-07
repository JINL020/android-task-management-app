package at.ac.univie.se2_team_0308.utils.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String EVENT_KEY = "appointment";

    @Override
    public void onReceive(Context context, Intent intent) {
        INotifier notifier = new BasicNotifier(new PopupNotifier(new LoggerCore()));
        notifier.sendNotification(ENotificationEvent.APPOINTMENT, intent.getStringExtra(EVENT_KEY));
    }
}
