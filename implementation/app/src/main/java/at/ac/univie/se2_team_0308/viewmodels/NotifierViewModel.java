package at.ac.univie.se2_team_0308.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.IObserver;
import at.ac.univie.se2_team_0308.models.ISubject;
import at.ac.univie.se2_team_0308.repository.NotifierRepository;
import at.ac.univie.se2_team_0308.utils.notifications.INotifier;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;

public class NotifierViewModel extends AndroidViewModel {
    private static final String TAG = "MAIN_ACTIVITY";

    private NotifierRepository notifierRepository;
    private LiveData<List<SettingsNotifier>> settingNotifiers;

    private INotifier onCreateNotifier = new LoggerCore();
    private INotifier onUpdateNotifier = new LoggerCore();
    private INotifier onDeleteNotifier = new LoggerCore();

    public NotifierViewModel(@NonNull Application application) {
        super(application);
        this.notifierRepository = new NotifierRepository(application);
        this.settingNotifiers = notifierRepository.getAllNotifiers();
    }

    /*public void insert(SettingsNotifier settingsNotifier){
        notifierRepository.insert(settingsNotifier);
    }*/

    public void update(SettingsNotifier settingsNotifier){
        notifierRepository.update(settingsNotifier);
        Log.d(TAG, "updated settings " + settingsNotifier.toString());
    }

    /*public void delete(SettingsNotifier settingsNotifier){
        notifierRepository.delete(settingsNotifier);
    }*/

    public void deleteAll(){
        notifierRepository.deleteAll();
    }

    public LiveData<List<SettingsNotifier>> getAllNotifiers(){
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
}