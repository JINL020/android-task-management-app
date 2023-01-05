package at.ac.univie.se2_team_0308.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;

public class NotifierRepository implements INotifierRepository {
    private static INotifierDao notifierDao;
    private LiveData<List<SettingsNotifier>> notifiers;

    public NotifierRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        this.notifierDao = database.notifierDao();
        this.notifiers = notifierDao.getAllNotifiers();
    }

    @Override
    public void insert(SettingsNotifier settingsNotifier) {
        AppDatabase.databaseWriteExecutor.execute( () -> notifierDao.insert(settingsNotifier));
    }

    @Override
    public void update(SettingsNotifier settingsNotifier) {
        AppDatabase.databaseWriteExecutor.execute( () -> notifierDao.update(settingsNotifier));
    }

    @Override
    public void delete(SettingsNotifier settingsNotifier) {
        AppDatabase.databaseWriteExecutor.execute( () -> notifierDao.delete(settingsNotifier));
    }

    @Override
    public void deleteAll(){
        AppDatabase.databaseWriteExecutor.execute( () -> notifierDao.deleteAll());
    }

    @Override
    public LiveData<List<SettingsNotifier>> getAllNotifiers() {
        return notifiers;
    }

}
