package at.ac.univie.se2_team_0308.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.databinding.ActivityMainBinding;
import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.utils.notifications.EventNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.IObserver;
import at.ac.univie.se2_team_0308.viewmodels.EventNotifierViewModel;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class MainActivity extends AppCompatActivity implements IObserver {

    private static final String TAG = "MAIN_ACTIVITY";

    private ActivityMainBinding binding;
    private static Context context;

    private EventNotifierViewModel eventNotifierViewModel;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        configureBottomNavBar();

        initViewModels();

        taskViewModel.attachObserver(this);
        Log.d(TAG, "attached  observer to taskViewModel");

        eventNotifierViewModel.getAllNotifiers().observe(this, new Observer<List<EventNotifier>>() {
            @Override
            public void onChanged(List<EventNotifier> eventNotifiers) {
                for (EventNotifier notifier : eventNotifiers) {
                    if (notifier.getEvent() == ENotificationEvent.CREATE) {
                        eventNotifierViewModel.setOnCreateNotifier(notifier.getNotifier());
                    }
                    if (notifier.getEvent() == ENotificationEvent.UPDATE) {
                        eventNotifierViewModel.setOnUpdateNotifier(notifier.getNotifier());
                    }
                    if (notifier.getEvent() == ENotificationEvent.DELETE) {
                        eventNotifierViewModel.setOnDeleteNotifier(notifier.getNotifier());
                    }
                }

                Log.d(TAG, "Saved settings:\n" + eventNotifiers.toString());
            }
        });

    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    public void receivedUpdate(ENotificationEvent event, ATask... tasks) {
        String message = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            message = Arrays.stream(tasks).map(ATask::getTaskName).collect(Collectors.joining("\n"));
        }

        Log.d(TAG, "received update from taskViewModel:" + event.name() + message);

        if (event == ENotificationEvent.CREATE) {
            eventNotifierViewModel.getOnCreateNotifier().sendNotification(event, message);
        }
        if (event == ENotificationEvent.UPDATE) {
            eventNotifierViewModel.getOnUpdateNotifier().sendNotification(event, message);
        }
        if (event == ENotificationEvent.DELETE) {
            eventNotifierViewModel.getOnDeleteNotifier().sendNotification(event, message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskViewModel.detachObserver(this);
    }

    private void configureBottomNavBar() {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_list, R.id.navigation_calendar, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
    }

    private void initViewModels() {
        eventNotifierViewModel = new ViewModelProvider(this).get(EventNotifierViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
    }
}