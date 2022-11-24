package at.ac.univie.se2_team_0308.repository;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private static TaskAppointmentDao taskAppointmentDao;
    private static TaskChecklistDao taskChecklistDao;
    private LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> allTasks;

    public static final String TAG = "TaskRepository";

    public TaskRepository(Application application) {
    }

    public LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> getAllTasks() {
        return allTasks;
    }

    public void insertTaskAppointment(TaskAppointment taskAppointment) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskAppointmentDao.insert(taskAppointment));
    }

    public void insertTaskChecklist(TaskChecklist taskChecklist) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskChecklistDao.insert(taskChecklist) );
    }

    public void updateTaskAppointment(TaskAppointment taskAppointment) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskAppointmentDao.update(taskAppointment));
    }

    public void updateTaskChecklist(TaskChecklist taskChecklist) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskChecklistDao.update(taskChecklist) );
    }

    public void deleteTaskAppointment(TaskAppointment taskAppointment) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskAppointmentDao.delete(taskAppointment));
    }

    public void deleteTaskChecklist(TaskChecklist taskChecklist) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskChecklistDao.delete(taskChecklist) );
    }

}
