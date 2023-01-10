package at.ac.univie.se2_team_0308.utils.notifications;

import android.content.Context;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.ENotifier;

public abstract class ADecoratorNotifier implements INotifier {

    private INotifier wrapped;

    protected ADecoratorNotifier(INotifier wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void sendNotification(Context context, ENotificationEvent event, ATask... tasks) {
        wrapped.sendNotification(context, event, tasks);
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = wrapped.getNotifierType();
        return wrappedRet;
    }
}
