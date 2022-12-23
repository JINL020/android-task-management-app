package at.ac.univie.se2_team_0308.viewmodels;

import android.app.Application;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.repository.TaskRepository;


public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> allTasks;

    private List<Integer> selectedTasksAppointment = new ArrayList<>();
    private List<Integer> selectedTasksChecklist = new ArrayList<>();

    public static final String TAG = "TaskViewModel";

    public TaskViewModel(Application application){
        super(application);
        this.repository = new TaskRepository(application);
        this.allTasks = repository.getAllTasks();
    }

    public void init(Application application){
        this.repository = new TaskRepository(application);
        this.allTasks = repository.getAllTasks();
    }

    public void insertAppointment(TaskAppointment task){
        repository.insertTaskAppointment(task);
    }

    public void updateAppointment(TaskAppointment task) {
        repository.updateTaskAppointment(task);
    }

    public void selectTaskAppointment(ATask taskModel) {
        selectedTasksAppointment.add(taskModel.getId());
    }

    public void insertChecklist(TaskChecklist task) {
        repository.insertTaskChecklist(task);
    }

    public void updateChecklist(TaskChecklist task) {
        repository.updateTaskChecklist(task);
    }

    public void selectTaskChecklist(ATask taskModel) {
        selectedTasksChecklist.add(taskModel.getId());
    }

    public List<Integer> getSelectedTaskAppointmentIds() {
        return selectedTasksAppointment;
    }

    public List<Integer> getSelectedTaskChecklistIds() {
        return selectedTasksChecklist;
    }

    public LiveData<List<TaskAppointment>> getSelectedTaskAppointment(){
        return repository.getSelectedTaskAppointment(selectedTasksAppointment);
    }

    public LiveData<List<TaskChecklist>> getSelectedTaskChecklist(){
        return repository.getSelectedTaskChecklist(selectedTasksChecklist);
    }

    public void deleteAllSelectedTasks(List<Integer> selectedItemsAppointment, List<Integer> selectedItemsChecklist) {
        repository.deleteSelectedTasks(selectedItemsAppointment, selectedItemsChecklist);
    }

    public void updateAllSelectedTasksPriorities(List<Integer> selectedItemsAppointment, List<Integer> selectedItemsChecklist, EPriority priorityEnum) {
        repository.updateSelectedTasksPriority(selectedItemsAppointment, selectedItemsChecklist, priorityEnum);
    }

    public LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> getAllLiveTasks() {
        return allTasks;
    }

    public List<ATask> getAllTasks(){
        List<ATask> tasks = new ArrayList<>();
        tasks.addAll(allTasks.getValue().first);
        tasks.addAll(allTasks.getValue().second);
        Collections.sort(tasks, new Comparator<ATask>() {
            @Override
            public int compare(ATask task1, ATask task2) {
                return (int) (task1.getCreationDate().getTime() - task2.getCreationDate().getTime());
            }
        });
        return tasks;
    }
}
