package at.ac.univie.se2_team_0308.utils.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.utils.INotifierTypeConverter;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String EVENT_KEY = "appointment";
    public static final String NOTIFIER_KEY = "notifier";

    @Override
    public void onReceive(Context context, Intent intent) {
        String notifierString = intent.getStringExtra(NOTIFIER_KEY);
        INotifierTypeConverter notifierTypeConverter = new INotifierTypeConverter();
        INotifier notifier = notifierTypeConverter.toINotifier(notifierString);

        notifier.sendNotification(ENotificationEvent.APPOINTMENT, intent.getStringExtra(EVENT_KEY), context);
    }
}
