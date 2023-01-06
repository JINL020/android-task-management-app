package at.ac.univie.se2_team_0308.utils.notifications;

import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.views.MainActivity;

public class PopupNotifier extends ADecoratorNotifier {

    public PopupNotifier(INotifier wrapped) {
        super(wrapped);
    }

    @Override
    public void sendNotification(ENotificationEvent event, String message) {
        super.sendNotification(event, message);
        Toast.makeText(MainActivity.getAppContext(), event.name() + "\n" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = super.getNotifierType();
        wrappedRet.add(ENotifier.POPUP);
        return wrappedRet;
    }

}
