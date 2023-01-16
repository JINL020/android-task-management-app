package at.ac.univie.se2_team_0308.repository;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class TaskListProxy implements ITaskList {

    private ITaskList taskList;

    /**
     * The first time the app is started, the taskList is null so
     * the real TaskListImplementation is called for initialization.
     * When the taskList is not null, the proxy class is used.
     * @param application
     * @return a LiveData object that contains both types of Tasks as Pairs
     */
    @Override
    public LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> getAllTasks(Application application) {
        if (taskList == null) {
            taskList = new TaskListImplementation();
        }
        return taskList.getAllTasks(application);
    }

    @Override
    public ITaskAppointmentDao getAppointmentDao() {
        return taskList.getAppointmentDao();
    }

    @Override
    public ITaskChecklistDao getChecklistDao() {
        return taskList.getChecklistDao();
    }

}