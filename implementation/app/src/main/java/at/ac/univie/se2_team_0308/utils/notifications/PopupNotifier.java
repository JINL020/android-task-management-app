package at.ac.univie.se2_team_0308.utils.notifications;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.ENotifier;
import at.ac.univie.se2_team_0308.views.MainActivity;

public class PopupNotifier extends ADecoratorNotifier {
    private static final String TAG = "POPUP_NOTIFIER";

    public PopupNotifier(INotifier wrapped) {
        super(wrapped);
    }

    @Override
    public void sendNotification(ENotificationEvent event, String message, Context context) {
        super.sendNotification(event, message, context);
        Toast.makeText(context, event.name() + "\n" + message, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "send out notification: " + event.name() + message);
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = super.getNotifierType();
        wrappedRet.add(ENotifier.POPUP);
        return wrappedRet;
    }

}
