package at.ac.univie.se2_team_0308.utils.notifications;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;

public class PopupNotifier extends ADecoratorNotifier {
    private static final String TAG = "POPUP_NOTIFIER";

    public PopupNotifier(INotifier wrapped) {
        super(wrapped);
    }

    @Override
    public void sendNotification(Context context, ENotificationEvent event, ATask... tasks) {
        super.sendNotification(context, event, tasks);
        for(ATask task : tasks){
            Toast.makeText(context, event.name() + "\n" + task.getTaskName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = super.getNotifierType();
        wrappedRet.add(ENotifier.POPUP);
        return wrappedRet;
    }

}
