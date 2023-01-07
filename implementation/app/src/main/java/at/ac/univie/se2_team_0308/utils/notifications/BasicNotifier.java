package at.ac.univie.se2_team_0308.utils.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.ENotifier;
import at.ac.univie.se2_team_0308.views.MainActivity;

public class BasicNotifier extends ADecoratorNotifier {
    private static final String TAG = "BASIC_NOTIFIER";

    public BasicNotifier(INotifier wrapped) {
        super(wrapped);
    }

    @Override
    public void sendNotification(ENotificationEvent event, String message) {
        super.sendNotification(event, message);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MainActivity.getAppContext(), "notifications")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setStyle(new NotificationCompat
                        .BigTextStyle()
                        .setBigContentTitle(event.name())
                        .bigText(message)
                );

        Notification notification = notificationBuilder.build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.getAppContext());
        notificationManagerCompat.notify(1, notification);

        Log.d(TAG, "send out notification: " + event.name() + message);
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = super.getNotifierType();
        wrappedRet.add(ENotifier.BASIC);
        return wrappedRet;
    }

}
