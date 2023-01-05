package at.ac.univie.se2_team_0308.utils.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.views.MainActivity;

public class BasicNotifier extends ADecoratorNotifier{
    public BasicNotifier(INotifier wrapped) {
        super(wrapped);
    }

    @Override
    public void sendNotification(ENotificationEvent event, ATask... task) {
        super.sendNotification(event, task);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("notifications", "Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = MainActivity.getAppContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        String message = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            message = Arrays.stream(task).map(ATask::getTaskName) .collect(Collectors.joining("\n\n"));
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MainActivity.getAppContext(), "notifications");
        notificationBuilder
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setStyle(new NotificationCompat
                        .BigTextStyle()
                        .setBigContentTitle(event.name())
                        .bigText(message));

        Notification notification  = notificationBuilder.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.getAppContext());
        notificationManagerCompat.notify(1, notification);
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = super.getNotifierType();
        wrappedRet.add(ENotifier.BASIC);
        return wrappedRet;
    }

}
