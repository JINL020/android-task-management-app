package at.ac.univie.se2_team_0308.utils.notifications;

import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.views.MainActivity;

public class PopupNotifier extends ADecoratorNotifier {
    public PopupNotifier(IObserver wrapped) {
        super(wrapped);
    }

    @Override
    public void update(ENotificationEvent event, ATask... task) {
        super.update(event, task);

        String message = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            message = Arrays.stream(task).map(ATask::getTaskName).collect(Collectors.joining("\n\n"));
        }

        Toast.makeText(MainActivity.getAppContext(), event.name() + "\n" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public List<ENotifier> getNotifierType() {
        List<ENotifier> wrappedRet = super.getNotifierType();
        wrappedRet.add(ENotifier.POPUP);
        return wrappedRet;
    }

}
