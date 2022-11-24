package at.ac.univie.se2_team_0308.repository;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskListImplementation implements TaskList {

    private static TaskAppointmentDao appointmentDao;
    private static TaskChecklistDao checklistDao;

    public static final String TAG = "TaskListImpl";

    @Override
    public LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> getAllTasks(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        appointmentDao = database.appointmentDao();
        checklistDao = database.checklistDao();
        return new CombinedLiveData(appointmentDao.getAllTasks(), checklistDao.getAllTasks());
    }

    @Override
    public TaskAppointmentDao getAppointmentDao() {
        return appointmentDao;
    }

    @Override
    public TaskChecklistDao getChecklistDao() {
        return checklistDao;
    }


}