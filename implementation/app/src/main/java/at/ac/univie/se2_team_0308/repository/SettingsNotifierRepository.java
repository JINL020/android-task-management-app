package at.ac.univie.se2_team_0308.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;

public class SettingsNotifierRepository implements ISettingsRepository {
    private static ISettingsNotifierDao settingsNotifierDao;
    private LiveData<List<SettingsNotifier>> notifiers;

    public static final String TAG = "TaskRepository";

    public SettingsNotifierRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        this.settingsNotifierDao = database.iSettingsNotifierDao();
        this.notifiers = settingsNotifierDao.getAllSettingsNotifier();
    }

    @Override
    public void insert(SettingsNotifier settingsNotifier) {
        new insertSettingsNotifierAsyncTask(settingsNotifierDao).execute(settingsNotifier);
    }

    @Override
    public void update(SettingsNotifier settingsNotifier) {
        new updateSettingsNotifierAsyncTask(settingsNotifierDao).execute(settingsNotifier);
    }

    @Override
    public void delete(SettingsNotifier settingsNotifier) {
        new deleteSettingsNotifierAsyncTask(settingsNotifierDao).execute(settingsNotifier);
    }

    @Override
    public void deleteAll(){
        new deleteAllSettingsNotifierAsyncTask(settingsNotifierDao).execute();
    }

    @Override
    public LiveData<List<SettingsNotifier>> getAllSettingsNotifier() {
        return notifiers;
    }

    private static class insertSettingsNotifierAsyncTask extends AsyncTask<SettingsNotifier, Void, Void> {
        private ISettingsNotifierDao settingsNotifierDao;

        public insertSettingsNotifierAsyncTask(ISettingsNotifierDao settingsNotifierDao) {
            this.settingsNotifierDao = settingsNotifierDao;
        }

        @Override
        protected Void doInBackground(SettingsNotifier... settingsNotifiers) {
            settingsNotifierDao.insert(settingsNotifiers[0]);
            return null;
        }
    }

    private static class updateSettingsNotifierAsyncTask extends AsyncTask<SettingsNotifier, Void, Void> {
        private ISettingsNotifierDao settingsNotifierDao;

        public updateSettingsNotifierAsyncTask(ISettingsNotifierDao settingsNotifierDao) {
            this.settingsNotifierDao = settingsNotifierDao;
        }

        @Override
        protected Void doInBackground(SettingsNotifier... settingsNotifiers) {
            settingsNotifierDao.update(settingsNotifiers[0]);
            return null;
        }
    }

    private static class deleteSettingsNotifierAsyncTask extends AsyncTask<SettingsNotifier, Void, Void> {
        private ISettingsNotifierDao settingsNotifierDao;

        public deleteSettingsNotifierAsyncTask(ISettingsNotifierDao settingsNotifierDao) {
            this.settingsNotifierDao = settingsNotifierDao;
        }

        @Override
        protected Void doInBackground(SettingsNotifier... settingsNotifiers) {
            settingsNotifierDao.delete(settingsNotifiers[0]);
            return null;
        }
    }

    private static class deleteAllSettingsNotifierAsyncTask extends AsyncTask<Void, Void, Void> {
        private ISettingsNotifierDao settingsNotifierDao;

        public deleteAllSettingsNotifierAsyncTask(ISettingsNotifierDao settingsNotifierDao) {
            this.settingsNotifierDao = settingsNotifierDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            settingsNotifierDao.deleteAll();
            return null;
        }
    }
}
