package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Log;

import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class TaskSynchronizer {
    private static final String TAG = "TaskSynchronizer";
    public void synchronizeTasks(TaskViewModel viewModel, List<TaskAppointment> taskAppointments, List<TaskChecklist> taskChecklists){
        List<ATask> allTasks = viewModel.getAllTasks();
        for(TaskAppointment eachTask: taskAppointments){
            boolean taskExists = false;

            for(ATask existingTask : allTasks){
                if(existingTask.getId() == eachTask.getId()){
                    taskExists = true;
                    break;
                }
            }

            if(taskExists){
                Log.d(TAG, "Task appointment with Id " + eachTask.getId() + "already exists");
                Log.d(TAG, "Update task appointment");
                viewModel.updateAppointment(eachTask);
            }
            else{
                Log.d(TAG, "Insert new task appointment");
                viewModel.insertAppointment(eachTask);
            }
        }

        for(TaskChecklist eachTask: taskChecklists){
            boolean taskExists = false;

            for(ATask existingTask : allTasks){
                if(existingTask.getId() == eachTask.getId()){
                    taskExists = true;
                    break;
                }
            }

            if(taskExists){
                Log.d(TAG, "Task checklist with Id " + eachTask.getId() + "already exists");
                Log.d(TAG, "Update task checklist");
                viewModel.updateChecklist(eachTask);
            }
            else{
                Log.d(TAG, "Insert new task checklist");
                viewModel.insertChecklist(eachTask);
            }
        }
    }
}
