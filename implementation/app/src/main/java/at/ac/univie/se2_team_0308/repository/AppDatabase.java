package at.ac.univie.se2_team_0308.repository;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TaskAppointment.class, TaskChecklist.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskAppointmentDao taskAppointmentDao();
    public abstract TaskChecklistDao taskChecklistDao();

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
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
