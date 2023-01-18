package at.ac.univie.se2_team_0308.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import at.ac.univie.se2_team_0308.utils.notifications.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.utils.typeConverters.DateConverter;
import at.ac.univie.se2_team_0308.utils.typeConverters.ECategoryTypeConverter;
import at.ac.univie.se2_team_0308.utils.typeConverters.ENotificationEventTypeConverter;
import at.ac.univie.se2_team_0308.utils.typeConverters.EPriorityTypeConverter;
import at.ac.univie.se2_team_0308.utils.typeConverters.EStatusTypeConverter;
import at.ac.univie.se2_team_0308.utils.typeConverters.INotifierTypeConverter;
import at.ac.univie.se2_team_0308.utils.typeConverters.SubtasksConverter;
import at.ac.univie.se2_team_0308.utils.notifications.EventNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.INotifier;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;

@Database(entities = {TaskAppointment.class, TaskChecklist.class, EventNotifier.class}, version = 1, exportSchema = false)
@TypeConverters({EPriorityTypeConverter.class, EStatusTypeConverter.class, ECategoryTypeConverter.class, SubtasksConverter.class, DateConverter.class, ENotificationEventTypeConverter.class, INotifierTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ITaskAppointmentDao taskAppointmentDao();

    public abstract ITaskChecklistDao taskChecklistDao();

    public abstract IEventNotifierDao eventNotifierDao();

    //START https://developer.android.com/codelabs/android-room-with-a-view#7
    private static AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //END https://developer.android.com/codelabs/android-room-with-a-view#7

    public static final String TAG = "";

    static AppDatabase getDatabase(final Context context) throws SingletonDbDoubleInitException {
        if (INSTANCE == null) {
            Log.d(TAG, "getInstance: ");
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "task_management_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
                } else {
                    throw new SingletonDbDoubleInitException();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            AppDatabase.databaseWriteExecutor.execute(() -> populateDb(INSTANCE.eventNotifierDao()));;
        }
    };

    private static void populateDb(IEventNotifierDao eventNotifierDao){
        INotifier defaultNotifier = new LoggerCore();
        eventNotifierDao.insert(new EventNotifier(ENotificationEvent.CREATE, defaultNotifier));
        eventNotifierDao.insert(new EventNotifier(ENotificationEvent.UPDATE, defaultNotifier));
        eventNotifierDao.insert(new EventNotifier(ENotificationEvent.DELETE, defaultNotifier));
        eventNotifierDao.insert(new EventNotifier(ENotificationEvent.APPOINTMENT, defaultNotifier));
    }

}
