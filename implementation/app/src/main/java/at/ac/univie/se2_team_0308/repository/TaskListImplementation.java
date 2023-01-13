package at.ac.univie.se2_team_0308.repository;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.utils.CombinedLiveData;

public class TaskListImplementation implements ITaskList {

    private static ITaskAppointmentDao appointmentDao;
    private static ITaskChecklistDao checklistDao;

    public static final String TAG = "TaskListImpl";

    @Override
    public LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> getAllTasks(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        appointmentDao = database.taskAppointmentDao();
        checklistDao = database.taskChecklistDao();
        return new CombinedLiveData(appointmentDao.getAllTasks(), checklistDao.getAllTasks());
    }

    @Override
    public ITaskAppointmentDao getAppointmentDao() {
        return appointmentDao;
    }

    @Override
    public ITaskChecklistDao getChecklistDao() {
        return checklistDao;
    }


}