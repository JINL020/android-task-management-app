package at.ac.univie.se2_team_0308.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import at.ac.univie.se2_team_0308.repository.SettingsNotifierRepository;
import at.ac.univie.se2_team_0308.repository.TaskRepository;
import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;

public class SettingsNotifierViewModel extends AndroidViewModel {
    private SettingsNotifierRepository settingsNotifierRepository;
    private LiveData<List<SettingsNotifier>> settingNotifiers;

    public SettingsNotifierViewModel(@NonNull Application application) {
        super(application);
        this.settingsNotifierRepository = new SettingsNotifierRepository(application);
        this.settingNotifiers = settingsNotifierRepository.getAllSettingsNotifier();
    }

    public void insert(SettingsNotifier settingsNotifier){
        settingsNotifierRepository.insert(settingsNotifier);
    }

    public void update(SettingsNotifier settingsNotifier){
        settingsNotifierRepository.update(settingsNotifier);
    }

    public void delete(SettingsNotifier settingsNotifier){
        settingsNotifierRepository.delete(settingsNotifier);
    }

    public void deleteAll(){
        settingsNotifierRepository.deleteAll();
    }

    public LiveData<List<SettingsNotifier>> getAllSettingsNotifier(){
        return settingsNotifierRepository.getAllSettingsNotifier();
    }

}