package at.ac.univie.se2_team_0308.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.repository.NotifierRepository;
import at.ac.univie.se2_team_0308.utils.notifications.IObserver;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;

public class NotifierViewModel extends AndroidViewModel {
    private NotifierRepository notifierRepository;
    private LiveData<List<SettingsNotifier>> settingNotifiers;

    private IObserver onCreateNotifier;


    public NotifierViewModel(@NonNull Application application) {
        super(application);
        this.notifierRepository = new NotifierRepository(application);
        this.settingNotifiers = notifierRepository.getAllNotifiers();
        this.onCreateNotifier = new LoggerCore();
        //initOnEventNotifiers();
    }

    /*public void insert(SettingsNotifier settingsNotifier){
        notifierRepository.insert(settingsNotifier);
    }*/

    public void update(SettingsNotifier settingsNotifier){
        notifierRepository.update(settingsNotifier);
    }

    /*public void delete(SettingsNotifier settingsNotifier){
        notifierRepository.delete(settingsNotifier);
    }*/

    public void deleteAll(){
        notifierRepository.deleteAll();
    }

    public LiveData<List<SettingsNotifier>> getAllNotifiers(){
        return notifierRepository.getAllNotifiers();
    }

    public IObserver getOnCreateNotifier() {
        return onCreateNotifier;
    }

    public void setOnCreateNotifier(IObserver onCreateNotifier) {
        this.onCreateNotifier = onCreateNotifier;
    }

    private void initOnEventNotifiers(){
        for(SettingsNotifier notifier : settingNotifiers.getValue()){
            if(notifier.getEvent() == ENotificationEvent.CREATE){
                this.onCreateNotifier = notifier.getNotifier();
            }
        }
    }
}