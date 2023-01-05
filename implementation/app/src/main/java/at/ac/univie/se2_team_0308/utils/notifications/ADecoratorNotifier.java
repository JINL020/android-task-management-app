package at.ac.univie.se2_team_0308.utils.notifications;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;

public abstract class ADecoratorNotifier implements INotifier {
    private INotifier wrapped;

    protected ADecoratorNotifier(INotifier wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void sendNotification(ENotificationEvent event, ATask... task) {
        wrapped.sendNotification(event, task);
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = wrapped.getNotifierType();
        return wrappedRet;
    }
}
