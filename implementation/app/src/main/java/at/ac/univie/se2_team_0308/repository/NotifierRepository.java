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
        new insertNotifierAsyncTask(notifierDao).execute(settingsNotifier);
    }

    @Override
    public void update(SettingsNotifier settingsNotifier) {
        new updateNotifierAsyncTask(notifierDao).execute(settingsNotifier);
    }

    @Override
    public void delete(SettingsNotifier settingsNotifier) {
        new deleteNotifierAsyncTask(notifierDao).execute(settingsNotifier);
    }

    @Override
    public void deleteAll(){
        new deleteAllNotifiersAsyncTask(notifierDao).execute();
    }

    @Override
    public LiveData<List<SettingsNotifier>> getAllNotifiers() {
        return notifiers;
    }

    private static class insertNotifierAsyncTask extends AsyncTask<SettingsNotifier, Void, Void> {
        private INotifierDao notifierDao;

        public insertNotifierAsyncTask(INotifierDao settingsNotifierDao) {
            this.notifierDao = settingsNotifierDao;
        }

        @Override
        protected Void doInBackground(SettingsNotifier... settingsNotifiers) {
            notifierDao.insert(settingsNotifiers[0]);
            return null;
        }
    }

    private static class updateNotifierAsyncTask extends AsyncTask<SettingsNotifier, Void, Void> {
        private INotifierDao notifierDao;

        public updateNotifierAsyncTask(INotifierDao settingsNotifierDao) {
            this.notifierDao = settingsNotifierDao;
        }

        @Override
        protected Void doInBackground(SettingsNotifier... settingsNotifiers) {
            notifierDao.update(settingsNotifiers[0]);
            return null;
        }
    }

    private static class deleteNotifierAsyncTask extends AsyncTask<SettingsNotifier, Void, Void> {
        private INotifierDao notifierDao;

        public deleteNotifierAsyncTask(INotifierDao settingsNotifierDao) {
            this.notifierDao = settingsNotifierDao;
        }

        @Override
        protected Void doInBackground(SettingsNotifier... settingsNotifiers) {
            notifierDao.delete(settingsNotifiers[0]);
            return null;
        }
    }

    private static class deleteAllNotifiersAsyncTask extends AsyncTask<Void, Void, Void> {
        private INotifierDao notifierDao;

        public deleteAllNotifiersAsyncTask(INotifierDao settingsNotifierDao) {
            this.notifierDao = settingsNotifierDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notifierDao.deleteAll();
            return null;
        }
    }
}
