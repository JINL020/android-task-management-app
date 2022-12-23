package at.ac.univie.se2_team_0308.repository;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class TaskRepository {
    private static TaskAppointmentDao taskAppointmentDao;
    private static TaskChecklistDao taskChecklistDao;
    private LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> allTasks;

    public static final String TAG = "TaskRepository";

    public TaskRepository(Application application) {
        TaskList taskListProxy = new TaskListProxy();
        this.allTasks = taskListProxy.getAllTasks(application);
        taskAppointmentDao = taskListProxy.getAppointmentDao();
        taskChecklistDao = taskListProxy.getChecklistDao();
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

    public void deleteSelectedTasks(List<Integer> taskIdsAppointment, List<Integer> taskIdsChecklist) {
        deleteTasksByIdAppointment(taskIdsAppointment);
        deleteTasksByIdChecklist(taskIdsChecklist);
    }

    // TODO check if this is correct
    public LiveData<List<TaskAppointment>> getSelectedTaskAppointment(List<Integer> taskIdsAppointment){
        return taskAppointmentDao.getTasksById(taskIdsAppointment);
    }
    // TODO check if this is correct
    public LiveData<List<TaskChecklist>> getSelectedTaskChecklist(List<Integer> taskIdsChecklist){
        return taskChecklistDao.getTasksById(taskIdsChecklist);
    }

    public void deleteTasksByIdAppointment(List<Integer> taskIds) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskAppointmentDao.deleteTasksById(taskIds));
    }

    public void deleteTasksByIdChecklist(List<Integer> taskIds) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskChecklistDao.deleteTasksById(taskIds));
    }

    // Update common property Priority
    public void updateSelectedTasksPriority(List<Integer> taskIdsAppointment, List<Integer> taskIdsChecklist, EPriority priorityEnum) {
        updateAppointmentTasksPriority(taskIdsAppointment, priorityEnum);
        updateChecklistTasksPriority(taskIdsChecklist, priorityEnum);
    }

    public void updateAppointmentTasksPriority(List<Integer> taskIds, EPriority priorityEnum) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskAppointmentDao.updateTaskPriority(taskIds, priorityEnum));
    }

    public void updateChecklistTasksPriority(List<Integer> taskIds, EPriority priorityEnum) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskChecklistDao.updateTaskPriority(taskIds, priorityEnum));
    }

}
