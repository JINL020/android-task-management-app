package at.ac.univie.se2_team_0308.repository;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.repository.notication.ENotificationEventTypeConverter;
import at.ac.univie.se2_team_0308.repository.notication.IObserverTypeConverter;
import at.ac.univie.se2_team_0308.repository.notication.ISettingsNotifierDao;
import at.ac.univie.se2_team_0308.utils.DateConverter;
import at.ac.univie.se2_team_0308.utils.ECategoryTypeConverter;
import at.ac.univie.se2_team_0308.utils.EPriorityTypeConverter;
import at.ac.univie.se2_team_0308.utils.EStatusTypeConverter;
import at.ac.univie.se2_team_0308.utils.SubtasksConverter;
import at.ac.univie.se2_team_0308.utils.notifications.IObserver;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TaskAppointment.class, TaskChecklist.class, SettingsNotifier.class}, version = 3, exportSchema = false)
@TypeConverters({EPriorityTypeConverter.class, EStatusTypeConverter.class, ECategoryTypeConverter.class, SubtasksConverter.class, DateConverter.class, ENotificationEventTypeConverter.class, IObserverTypeConverter.class} )
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskAppointmentDao taskAppointmentDao();
    public abstract TaskChecklistDao taskChecklistDao();
    public abstract ISettingsNotifierDao iSettingsNotifierDao();

    //START https://developer.android.com/codelabs/android-room-with-a-view#7
    private static AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //END https://developer.android.com/codelabs/android-room-with-a-view#7

    public static final String TAG = "";

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            Log.d(TAG, "getInstance: ");
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "task_management_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ISettingsNotifierDao settingsNotifierDao;

        public populateDbAsyncTask(AppDatabase db) {
            this.settingsNotifierDao = db.iSettingsNotifierDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            IObserver defaultObserver = new LoggerCore();
            settingsNotifierDao.insert(new SettingsNotifier(ENotificationEvent.CREATE, defaultObserver));
            settingsNotifierDao.insert(new SettingsNotifier(ENotificationEvent.UPDATE, defaultObserver));
            settingsNotifierDao.insert(new SettingsNotifier(ENotificationEvent.DELETE, defaultObserver));
            return null;
        }
    }

}
