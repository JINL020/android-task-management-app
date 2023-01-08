package at.ac.univie.se2_team_0308.views;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.databinding.ActivityMainBinding;
import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.utils.notifications.AlarmReceiver;
import at.ac.univie.se2_team_0308.utils.notifications.EventNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.IObserver;
import at.ac.univie.se2_team_0308.viewmodels.EventNotifierViewModel;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class MainActivity extends AppCompatActivity implements IObserver {

    private static final String TAG = "MAIN_ACTIVITY";
    public static final String EVENT_KEY = "appointment";

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

        initNotificationChannel();

        taskViewModel.attachObserver(this);
        Log.d(TAG, "attached observer to taskViewModel(MainActivity)");

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

        Log.d(TAG, "received update from taskViewModel: " + event.name() + " " + message);

        if (event == ENotificationEvent.CREATE) {
            eventNotifierViewModel.getOnCreateNotifier().sendNotification(event, message);
            for (ATask task : tasks) {
                if (task.getCategory().equals(ECategory.APPOINTMENT)) {
                    setAlarm((TaskAppointment)task, message);
                }
            }
        }
        if (event == ENotificationEvent.UPDATE) {
            eventNotifierViewModel.getOnUpdateNotifier().sendNotification(event, message);
            for (ATask task : tasks) {
                if (task.getCategory().equals(ECategory.APPOINTMENT)) {
                    cancelAlarm((TaskAppointment) task);
                    setAlarm((TaskAppointment)task, message);
                }
            }
        }
        if (event == ENotificationEvent.DELETE) {
            eventNotifierViewModel.getOnDeleteNotifier().sendNotification(event, message);
            for (ATask task : tasks) {
                if (task.getCategory().equals(ECategory.APPOINTMENT)) {
                    cancelAlarm((TaskAppointment) task);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //taskViewModel.detachObserver(this);
        Log.d(TAG, "detached observer to taskViewModel(MainActivity)");

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

    private void initNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("notifications", "Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    private void setAlarm(TaskAppointment appointment, String message) {
        Date deadline = appointment.getDeadline();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        Bundle extras = new Bundle();
        extras.putString(EVENT_KEY, message);
        intent.putExtras(extras);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, appointment.getId(), intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deadline);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d(TAG, "set alarm for " + appointment);
    }

    private void cancelAlarm(TaskAppointment appointment) {
        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, appointment.getId(), intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "cancel alarm for " + appointment);
    }
}