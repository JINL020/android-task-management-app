package at.ac.univie.se2_team_0308.utils.notifications;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

public class BasicNotifier extends ADecoratorNotifier {
    private static final String TAG = "BASIC_NOTIFIER";

    public BasicNotifier(INotifier wrapped) {
        super(wrapped);
    }

    @Override
    public void sendNotification(Context context, ENotificationEvent event, ATask... tasks) {
        super.sendNotification(context, event, tasks);

        for(ATask task : tasks){
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "notifications")
                    .setSmallIcon(android.R.drawable.stat_notify_sync)
                    .setContentTitle(event.name())
                    .setContentText(task.getTaskName());

            Notification notification = notificationBuilder.build();

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(task.getId(), notification);
        }
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = super.getNotifierType();
        wrappedRet.add(ENotifier.BASIC);
        return wrappedRet;
    }

}
