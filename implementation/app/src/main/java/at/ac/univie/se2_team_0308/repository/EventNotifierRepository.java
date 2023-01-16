package at.ac.univie.se2_team_0308.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.utils.notifications.EventNotifier;

public class EventNotifierRepository implements IEventNotifierRepository {
    private static final String TAG = "NOTIFIER_REPOSITORY";

    private static IEventNotifierDao eventNotifierDao;
    private LiveData<List<EventNotifier>> eventNotifiers;

    public EventNotifierRepository(Application application) {
        try {
            AppDatabase database = AppDatabase.getDatabase(application);
            eventNotifierDao = database.eventNotifierDao();
        } catch (SingletonDbDoubleInitException e) {
            Log.d(TAG, e.toString());
        }

        this.eventNotifiers = eventNotifierDao.getAllEventNotifiers();
    }

    @Override
    public void insert(EventNotifier eventNotifier) {
        AppDatabase.databaseWriteExecutor.execute(() -> eventNotifierDao.insert(eventNotifier));
        Log.d(TAG, "inserted notifier into db");
    }

    @Override
    public void update(EventNotifier eventNotifier) {
        AppDatabase.databaseWriteExecutor.execute(() -> eventNotifierDao.update(eventNotifier));
        Log.d(TAG, "updated notifier to " + eventNotifier + " in db");
    }

    @Override
    public void delete(EventNotifier eventNotifier) {
        AppDatabase.databaseWriteExecutor.execute(() -> eventNotifierDao.delete(eventNotifier));
        Log.d(TAG, "deleted notifier in db");
    }

    @Override
    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> eventNotifierDao.deleteAll());
        Log.d(TAG, "deleted all notification settings in db");
    }

    @Override
    public LiveData<List<EventNotifier>> getAllEventNotifiers() {
        return eventNotifiers;
    }

}
