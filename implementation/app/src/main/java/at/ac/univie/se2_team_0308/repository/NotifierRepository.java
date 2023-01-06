package at.ac.univie.se2_team_0308.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;

public class NotifierRepository implements INotifierRepository {
    private static final String TAG = "I_NOTIFIER_REPOSITORY";

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
        Log.d(TAG, "inserted notifier into db");
    }

    @Override
    public void update(SettingsNotifier settingsNotifier) {
        AppDatabase.databaseWriteExecutor.execute( () -> notifierDao.update(settingsNotifier));
        Log.d(TAG, "updated notifier in db");
    }

    @Override
    public void delete(SettingsNotifier settingsNotifier) {
        AppDatabase.databaseWriteExecutor.execute( () -> notifierDao.delete(settingsNotifier));
        Log.d(TAG, "deleted notifier in db");
    }

    @Override
    public void deleteAll(){
        AppDatabase.databaseWriteExecutor.execute( () -> notifierDao.deleteAll());
        Log.d(TAG, "deleted all notification settings in db");
    }

    @Override
    public LiveData<List<SettingsNotifier>> getAllNotifiers() {
        return notifiers;
    }

}
