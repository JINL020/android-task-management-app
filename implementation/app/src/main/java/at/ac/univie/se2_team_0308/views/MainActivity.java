package at.ac.univie.se2_team_0308.views;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.databinding.ActivityMainBinding;
import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.utils.INotifierTypeConverter;
import at.ac.univie.se2_team_0308.utils.notifications.AlarmReceiver;
import at.ac.univie.se2_team_0308.utils.notifications.EventNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.IObserver;
import at.ac.univie.se2_team_0308.viewmodels.EventNotifierViewModel;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class MainActivity extends AppCompatActivity implements IObserver {

    private static final String TAG = "MAIN_ACTIVITY";
    public static final String APPOINTMENT_KEY = "appointment";
    public static final String NOTIFIER_KEY = "notifier";

    private ActivityMainBinding binding;

    private EventNotifierViewModel eventNotifierViewModel;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configureBottomNavBar();

        initViewModels();

        initNotificationChannel();

        taskViewModel.attachObserver(this);
        Log.d(TAG, "attached observer to taskViewModel(MainActivity)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        taskViewModel.detachObserver(this);
        Log.d(TAG, "detached observer from taskViewModel(MainActivity)");
    }

    private void configureBottomNavBar() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_list, R.id.navigation_calendar, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
    }

    private void initViewModels() {
        eventNotifierViewModel = new ViewModelProvider(this).get(EventNotifierViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

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
                    if (notifier.getEvent() == ENotificationEvent.APPOINTMENT) {
                        eventNotifierViewModel.setOnAppointmentNotifier(notifier.getNotifier());
                    }
                }
                Log.d(TAG, "Saved settings: " + eventNotifiers);
            }
        });
    }

    private void initNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("notifications", "Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void receivedUpdate(ENotificationEvent event, ATask... tasks) {
        if (event == ENotificationEvent.CREATE) {
            eventNotifierViewModel.getOnCreateNotifier().sendNotification(this, event, tasks);
            for (ATask task : tasks) {
                if (task.getCategory().equals(ECategory.APPOINTMENT)) {
                    setAlarm((TaskAppointment)task);
                }
                Log.d(TAG, "received onCreate update from taskViewModel: " + event.name() + " " + task.getTaskName());
            }
        }
        if (event == ENotificationEvent.UPDATE) {
            eventNotifierViewModel.getOnUpdateNotifier().sendNotification(this, event, tasks);
            for (ATask task : tasks) {
                Log.d(TAG, "received onUpdate update from taskViewModel: " + event.name() + " " + task.getTaskName());
            }
        }
        if (event == ENotificationEvent.DELETE) {
            eventNotifierViewModel.getOnDeleteNotifier().sendNotification(this, event, tasks);
            for (ATask task : tasks) {
                if (task.getCategory().equals(ECategory.APPOINTMENT)) {
                    cancelAlarm((TaskAppointment) task);
                }
                Log.d(TAG, "received onDelete update from taskViewModel: " + event.name() + " " + task.getTaskName());
            }
        }
    }

    private void setAlarm(@NonNull TaskAppointment appointment) {
        Date deadline = appointment.getDeadline();

        Date currentTime = Calendar.getInstance().getTime();
        if(currentTime.after(deadline)){
            Log.d(TAG, "deadline already passed");
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deadline);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);

        Bundle extras = new Bundle();
        INotifierTypeConverter notifierTypeConverter = new INotifierTypeConverter();
        String notifier = notifierTypeConverter.fromINotifier(eventNotifierViewModel.getOnAppointmentNotifier());
        extras.putString(NOTIFIER_KEY, notifier);
        extras.putParcelable(APPOINTMENT_KEY, appointment);

        intent.putExtras(extras);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, appointment.getId(), intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d(TAG, "set alarm for " + appointment);
    }

    private void cancelAlarm(@NonNull TaskAppointment appointment) {
        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, appointment.getId(), intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "cancel alarm for " + appointment);
    }
}