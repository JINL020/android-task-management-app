package at.ac.univie.se2_team_0308.utils.notifications;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;

public abstract class ADecoratorNotifier implements IObserver {
    private IObserver wrapped;

    protected ADecoratorNotifier(IObserver wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void update(ENotificationEvent event, ATask... task) {
        wrapped.update(event, task);
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = wrapped.getNotifierType();
        return wrappedRet;
    }
}
