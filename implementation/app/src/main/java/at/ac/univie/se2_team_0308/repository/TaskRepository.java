package at.ac.univie.se2_team_0308.repository;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

/**
 * The Repository acts like a layer between
 * the real database and the viewmodels.
 */
public class TaskRepository {
    private static ITaskAppointmentDao taskAppointmentDao;
    private static ITaskChecklistDao taskChecklistDao;
    private LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> allTasks;

    public static final String TAG = "TaskRepository";

    public TaskRepository(Application application) {
        ITaskList taskListProxy = new TaskListProxy();
        this.allTasks = taskListProxy.getAllTasks(application);
        taskAppointmentDao = taskListProxy.getAppointmentDao();
        taskChecklistDao = taskListProxy.getChecklistDao();
    }

    public LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> getAllTasks() {
        return allTasks;
    }

    public long insertTaskAppointment(TaskAppointment taskAppointment) {
        Callable<Long> insertCallable = () -> taskAppointmentDao.insert(taskAppointment);
        long rowId = 0;

        Future<Long> future = AppDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rowId;
    }

    public long insertTaskChecklist(TaskChecklist taskChecklist) {
        Callable<Long> insertCallable = () -> taskChecklistDao.insert(taskChecklist);
        long rowId = 0;

        Future<Long> future = AppDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rowId;
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


    public void deleteTasksByIdAppointment(List<Integer> taskIds) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskAppointmentDao.deleteTasksById(taskIds));
    }

    public void deleteTasksByIdChecklist(List<Integer> taskIds) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskChecklistDao.deleteTasksById(taskIds));
    }

    /**
     * Updates priority given in argument
     * on both TaskAppointment and TaskChecklist
     * types of selected tasks
     * @param taskIdsAppointment current selected ids of TaskAppointment type
     * @param taskIdsChecklist current selected ids of TaskChecklist type
     * @param priorityEnum common priority to be updated
     */
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

    /**
     * Updates isHidden property
     * on both TaskAppointment and TaskChecklist
     * types of selected tasks
     * @param taskIdsAppointment current selected ids of TaskAppointment type
     * @param taskIdsChecklist current selected ids of TaskChecklist type
     * @param isHidden new boolean value for isHidden
     */
    public void updateSelectedTasksHiddenStatus(List<Integer> taskIdsAppointment, List<Integer> taskIdsChecklist, boolean isHidden) {
        updateAppointmentTasksHiddenStatus(taskIdsAppointment, isHidden);
        updateChecklistTasksHiddenStatus(taskIdsChecklist, isHidden);
    }

    public void updateAppointmentTasksHiddenStatus(List<Integer> taskIds, boolean isHidden) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskAppointmentDao.updateHiddenStatus(taskIds, isHidden));
    }

    public void updateChecklistTasksHiddenStatus(List<Integer> taskIds, boolean isHidden) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskChecklistDao.updateHiddenStatus(taskIds, isHidden));
    }

    /**
     * Updates task color property
     * on both TaskAppointment and TaskChecklist
     * types of selected tasks
     * @param taskIdsAppointment current selected ids of TaskAppointment type
     * @param taskIdsChecklist current selected ids of TaskChecklist type
     * @param color new string value for task color
     */
    public void updateSelectedTasksColor(List<Integer> taskIdsAppointment, List<Integer> taskIdsChecklist, String color) {
        updateAppointmentTasksColor(taskIdsAppointment, color);
        updateChecklistTasksColor(taskIdsChecklist, color);
    }

    public void updateAppointmentTasksColor(List<Integer> taskIds, String color) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskAppointmentDao.updateTaskColor(taskIds, color));
    }

    public void updateChecklistTasksColor(List<Integer> taskIds, String color) {
        AppDatabase.databaseWriteExecutor.execute( () -> taskChecklistDao.updateTaskColor(taskIds, color));
    }
}
