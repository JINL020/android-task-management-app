package at.ac.univie.se2_team_0308.viewmodels;

import android.app.Application;
import android.os.Build;

import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.IObserver;
import at.ac.univie.se2_team_0308.models.ISubject;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.repository.TaskRepository;


public class TaskViewModel extends AndroidViewModel implements ISubject {
    private TaskRepository repository;
    private LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> allTasks;

    private List<Integer> selectedTasksAppointment = new ArrayList<>();
    private List<Integer> selectedTasksChecklist = new ArrayList<>();

    public static final String TAG = "TaskViewModel";

    private List<IObserver> taskViewModelObservers = new ArrayList<>();

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
        notifyObservers(ENotificationEvent.CREATE, task);
    }

    public void updateAppointment(TaskAppointment task) {
        repository.updateTaskAppointment(task);
        notifyObservers(ENotificationEvent.UPDATE, task);
    }

    public void selectTaskAppointment(ATask taskModel) {
        selectedTasksAppointment.add(taskModel.getId());
    }

    public void deselectTaskAppointment(ATask taskModel) {
        List<Integer> selectedTasksAppointmentNew = new ArrayList<>();
        for(int taskId: selectedTasksAppointment) {
            if (taskId != taskModel.getId()) {
                selectedTasksAppointmentNew.add(taskId);
            }
        }
        selectedTasksAppointment = new ArrayList<>(selectedTasksAppointmentNew);
    }

    public void insertChecklist(TaskChecklist task) {
        repository.insertTaskChecklist(task);
        notifyObservers(ENotificationEvent.CREATE, task);
    }

    public void updateChecklist(TaskChecklist task) {
        repository.updateTaskChecklist(task);
        notifyObservers(ENotificationEvent.UPDATE, task);
    }

    public void selectTaskChecklist(ATask taskModel) {
        selectedTasksChecklist.add(taskModel.getId());
    }

    public List<Integer> getSelectedTaskAppointmentIds() {
        return selectedTasksAppointment;
    }

    public void deselectTaskChecklist(ATask taskModel) {
        List<Integer> selectedTasksChecklistNew = new ArrayList<>();
        for(int taskId: selectedTasksChecklist) {
            if (taskId != taskModel.getId()) {
                selectedTasksChecklistNew.add(taskId);
            }
        }
        selectedTasksChecklist = new ArrayList<>(selectedTasksChecklistNew);
    }

    public List<Integer> getSelectedTaskChecklistIds() {
        return selectedTasksChecklist;
    }


    public List<TaskChecklist> getSelectedTaskChecklist(List<Integer> selectedItemsChecklist){
        List<TaskChecklist> taskChecklist = new ArrayList<>();
        for(TaskChecklist eachTaskChecklist: Objects.requireNonNull(allTasks.getValue()).second){
            if(selectedItemsChecklist.contains(eachTaskChecklist.getId())){
                taskChecklist.add(eachTaskChecklist);
            }
        }
        return taskChecklist;
    }

    public List<TaskAppointment> getSelectedTaskAppointment(List<Integer> selectedItemsAppointment){
        List<TaskAppointment> taskAppointment = new ArrayList<>();
        for(TaskAppointment eachTaskAppointment: Objects.requireNonNull(allTasks.getValue()).first){
            if(selectedItemsAppointment.contains(eachTaskAppointment.getId())){
                taskAppointment.add(eachTaskAppointment);
            }
        }
        return taskAppointment;
    }

    public void deleteAllSelectedTasks(List<Integer> selectedItemsAppointment, List<Integer> selectedItemsChecklist) {
        repository.deleteSelectedTasks(selectedItemsAppointment, selectedItemsChecklist);

        List<TaskAppointment> appointments = getSelectedTaskAppointment(selectedItemsAppointment);
        List<TaskChecklist> checklists = getSelectedTaskChecklist(selectedItemsChecklist);

        if(!appointments.isEmpty()) {
            notifyObservers(ENotificationEvent.DELETE, appointments.toArray(new TaskAppointment[appointments.size()]));
        }
        if(!checklists.isEmpty()) {
            notifyObservers(ENotificationEvent.DELETE, checklists.toArray(new TaskChecklist[checklists.size()]));
        }
    }

    public void updateAllSelectedTasksPriorities(List<Integer> selectedItemsAppointment, List<Integer> selectedItemsChecklist, EPriority priorityEnum) {
        repository.updateSelectedTasksPriority(selectedItemsAppointment, selectedItemsChecklist, priorityEnum);

        List<TaskAppointment> appointments = getSelectedTaskAppointment(selectedItemsAppointment);
        List<TaskChecklist> checklists = getSelectedTaskChecklist(selectedItemsChecklist);

        if(!appointments.isEmpty()) {
            notifyObservers(ENotificationEvent.UPDATE, appointments.toArray(new TaskAppointment[appointments.size()]));
        }
        if(!checklists.isEmpty()){
            notifyObservers(ENotificationEvent.UPDATE, checklists.toArray(new TaskChecklist[checklists.size()]));
        }
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

    @Override
    public void attachObserver(IObserver observer) {
        if(!taskViewModelObservers.contains(observer)){
            taskViewModelObservers.add(observer);
        }
    }

    @Override
    public void detachObserver(IObserver observer) {
        if(taskViewModelObservers.contains(observer)){
            taskViewModelObservers.remove(observer);
        }
    }

    @Override
    public void notifyObservers(ENotificationEvent event, ATask... tasks) {
        for(IObserver observer : taskViewModelObservers){
            observer.receivedUpdate(event, tasks);
        }
    }
}
