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
import at.ac.univie.se2_team_0308.models.IObserver;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.PopupNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;
import at.ac.univie.se2_team_0308.viewmodels.NotifierViewModel;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class MainActivity extends AppCompatActivity implements IObserver {

    private static final String TAG = "MAIN_ACTIVITY";

    private ActivityMainBinding binding;
    private static Context context;

    private NotifierViewModel notifierViewModel;
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

        notifierViewModel.getAllNotifiers().observe(this, new Observer<List<SettingsNotifier>>() {
            @Override
            public void onChanged(List<SettingsNotifier> settingsNotifiers) {
                for (SettingsNotifier notifier : settingsNotifiers) {
                    if (notifier.getEvent() == ENotificationEvent.CREATE) {
                        notifierViewModel.setOnCreateNotifier(notifier.getNotifier());
                    }
                    if (notifier.getEvent() == ENotificationEvent.UPDATE) {
                        notifierViewModel.setOnUpdateNotifier(notifier.getNotifier());
                    }
                    if (notifier.getEvent() == ENotificationEvent.DELETE) {
                        notifierViewModel.setOnDeleteNotifier(notifier.getNotifier());
                    }
                }

                for (SettingsNotifier notifier : settingsNotifiers) {
                    Log.d(TAG, "Saved settings: " + settingsNotifiers.toString());
                }
            }
        });

    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    public void receivedUpdate(ENotificationEvent event, ATask... tasks) {
        Log.d(TAG, "received update from taskViewModel");
        String message = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            message = Arrays.stream(tasks).map(ATask::getTaskName) .collect(Collectors.joining("\n"));
        }
        if(event == ENotificationEvent.CREATE){
            notifierViewModel.getOnCreateNotifier().sendNotification(event, message);
        }
        if(event == ENotificationEvent.UPDATE){
            notifierViewModel.getOnUpdateNotifier().sendNotification(event, message);
        }
        if(event == ENotificationEvent.DELETE){
            notifierViewModel.getOnDeleteNotifier().sendNotification(event, message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskViewModel.detachObserver(this);
    }

    private  void configureBottomNavBar(){
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_list, R.id.navigation_calendar, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
    }

    private  void initViewModels(){
        notifierViewModel = new ViewModelProvider(this).get(NotifierViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
    }
}