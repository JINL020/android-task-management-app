package at.ac.univie.se2_team_0308.viewmodels;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.repository.EventNotifierRepository;
import at.ac.univie.se2_team_0308.utils.notifications.EventNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.INotifier;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;

public class EventNotifierViewModel extends AndroidViewModel {
    private static final String TAG = "VIEW_MODEL";

    private EventNotifierRepository eventNotifierRepository;
    private LiveData<List<EventNotifier>> settingNotifiers;

    private INotifier onCreateNotifier = new LoggerCore();
    private INotifier onUpdateNotifier = new LoggerCore();
    private INotifier onDeleteNotifier = new LoggerCore();
    private INotifier onAppointmentNotifier = new LoggerCore();

    public EventNotifierViewModel(@NonNull Application application) {
        super(application);
        this.eventNotifierRepository = new EventNotifierRepository(application);
        this.settingNotifiers = eventNotifierRepository.getAllEventNotifiers();
    }

    /*public void insert(SettingsNotifier settingsNotifier){
        notifierRepository.insert(settingsNotifier);
    }*/

    public void update(EventNotifier eventNotifier) {
        eventNotifierRepository.update(eventNotifier);
        Log.d(TAG, "EventNotifierViewModel updated" + eventNotifier.toString());
        Toast.makeText(getApplication(), "settings changed", Toast.LENGTH_SHORT).show();
    }

    public LiveData<List<EventNotifier>> getAllNotifiers() {
        return settingNotifiers;
    }

    public INotifier getOnCreateNotifier() {
        return onCreateNotifier;
    }

    public void setOnCreateNotifier(INotifier onCreateNotifier) {
        this.onCreateNotifier = onCreateNotifier;
    }

    public INotifier getOnUpdateNotifier() {
        return onUpdateNotifier;
    }

    public void setOnUpdateNotifier(INotifier onUpdateNotifier) {
        this.onUpdateNotifier = onUpdateNotifier;
    }

    public INotifier getOnDeleteNotifier() {
        return onDeleteNotifier;
    }

    public void setOnDeleteNotifier(INotifier onDeleteNotifier) {
        this.onDeleteNotifier = onDeleteNotifier;
    }

    public INotifier getOnAppointmentNotifier() {
        return onAppointmentNotifier;
    }

    public void setOnAppointmentNotifier(INotifier onAppointmentNotifier) {
        this.onAppointmentNotifier = onAppointmentNotifier;
    }
}