package at.ac.univie.se2_team_0308.utils.notifications;

import static at.ac.univie.se2_team_0308.views.MainActivity.APPOINTMENT_KEY;
import static at.ac.univie.se2_team_0308.views.MainActivity.NOTIFIER_KEY;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.utils.typeConverters.INotifierTypeConverter;

/**
 * The onReceive method is triggered every time a alarm is due.
 *
 * @param intent contains the task that is due and a INotifier to send out
 *               notifications according to what is saved in the settings
 * @author Jin-Jin Lee
 */
public class AlarmReceiver extends BroadcastReceiver {
    private INotifierTypeConverter notifierTypeConverter = new INotifierTypeConverter();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){
            String notifierString = intent.getStringExtra(NOTIFIER_KEY);
            INotifier notifier = notifierTypeConverter.toINotifier(notifierString);

            ATask appointment = intent.getParcelableExtra(APPOINTMENT_KEY);
            notifier.sendNotification(context, ENotificationEvent.APPOINTMENT, appointment);
        }
    }
}
