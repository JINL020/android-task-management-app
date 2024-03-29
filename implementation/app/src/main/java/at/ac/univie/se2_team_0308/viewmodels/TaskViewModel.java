package at.ac.univie.se2_team_0308.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.utils.notifications.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.utils.notifications.IObserver;
import at.ac.univie.se2_team_0308.utils.notifications.ISubject;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.repository.TaskRepository;
import at.ac.univie.se2_team_0308.views.DeadlinePassedException;


public class TaskViewModel extends AndroidViewModel implements ISubject {
    private TaskRepository repository;
    private LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> allTasks;

    private List<Integer> selectedTasksAppointment = new ArrayList<>();
    private List<Integer> selectedTasksChecklist = new ArrayList<>();

    public static final String TAG = "TaskViewModel";

    private List<IObserver> observers = new ArrayList<>();

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
        long insertedTaskId = repository.insertTaskAppointment(task);
        task.setId((int)insertedTaskId);
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

    public void deselectAllTaskAppointment() {
        selectedTasksAppointment = new ArrayList<>();
    }

    public void insertChecklist(TaskChecklist task) {
        long insertedTaskId = repository.insertTaskChecklist(task);
        task.setId((int)insertedTaskId);
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

    public void deselectAllTaskChecklist() {
        selectedTasksChecklist = new ArrayList<>();
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

    public void hideAllSelectedTasks(List<Integer> selectedItemsAppointment, List<Integer> selectedItemsChecklist, boolean isHidden) {
        repository.updateSelectedTasksHiddenStatus(selectedItemsAppointment, selectedItemsChecklist, isHidden);
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
        if(!observers.contains(observer)){
            observers.add(observer);
        }
    }

    @Override
    public void detachObserver(IObserver observer) {
        if(observers.contains(observer)){
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers(ENotificationEvent event, ATask... tasks) {
        for(IObserver observer : observers){
            try {
                observer.receivedUpdate(event, tasks);
            } catch (DeadlinePassedException e) {
                    Log.d(TAG, e.getErrorMessage());
            }
        }
    }

    //Getting appointment tasks based on deadline for later use in Calendar Fragment
    public List<ATask> getAppointmentTasks(Date deadline){
        List<ATask> tasks = new ArrayList<>();
        for (TaskAppointment task : allTasks.getValue().first) {
            Calendar calTask = Calendar.getInstance();
            Calendar calDeadline = Calendar.getInstance();
            calTask.setTime(task.getDeadline());
            calDeadline.setTime(deadline);
            boolean sameDay = calTask.get(Calendar.YEAR) == calDeadline.get(Calendar.YEAR) &&
                    calTask.get(Calendar.DAY_OF_YEAR) == calDeadline.get(Calendar.DAY_OF_YEAR);
            if(sameDay) {
                tasks.add(task);
                Log.d(TAG, "Dates are similar: " + calTask.toString() + " -- " + calDeadline.toString());
            }
        }
        Collections.sort(tasks, new Comparator<ATask>() {
            @Override
            public int compare(ATask task1, ATask task2) {
                return (int) (task1.getCreationDate().getTime() - task2.getCreationDate().getTime());
            }
        });
        return tasks;
    }
}
