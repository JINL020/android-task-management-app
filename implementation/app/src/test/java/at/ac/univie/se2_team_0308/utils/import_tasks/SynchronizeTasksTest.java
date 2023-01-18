package at.ac.univie.se2_team_0308.utils.import_tasks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class SynchronizeTasksTest {
    private TaskViewModel viewModel;
    private TaskSynchronizer taskSynchronizer;
    private List<TaskAppointment> taskAppointments;
    private List<TaskChecklist> taskChecklists;
    private TaskAppointment taskAppointment;
    private TaskChecklist taskChecklist;

    @Before
    public void setUp() {
        viewModel = mock(TaskViewModel.class);
        taskSynchronizer = new TaskSynchronizer();
        taskAppointments = new ArrayList<>();
        taskChecklists = new ArrayList<>();
        taskAppointment = createTaskAppointment(0);
        taskChecklist = createTaskChecklist(0);
    }

    public TaskAppointment createTaskAppointment(int idAppointment){
        TaskAppointment taskAppointment = new TaskAppointment(
                "taskName",
                "description",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2022, 5, 12),
                new ArrayList<>(),
                new byte[0],
                ""
        );
        taskAppointment.setId(idAppointment);
        taskAppointment.setCreationDate(new Date(2020, 5, 12));

        return taskAppointment;
    }

    public TaskChecklist createTaskChecklist(int idChecklist){
        TaskChecklist taskChecklist = new TaskChecklist(
                "taskName",
                "description",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>(),
                new ArrayList<>(),
                new byte[0],
                ""
        );
        taskChecklist.setId(idChecklist);
        taskChecklist.setCreationDate(new Date(2020, 5, 12));

        return taskChecklist;
    }

    @Test
    public void noPreviousTasksSynchronizeNewTaskAppointment_insertAppointmentCalled() {
        // Current taskViewModel does not hold any tasks
        when(viewModel.getAllTasks()).thenReturn(Collections.emptyList());

        // Perform synchronization
        taskAppointments.add(taskAppointment);
        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);

        // Since taskAppointment was not in the viewModel before
        // it should have been inserted during synchronization
        verify(viewModel).insertAppointment(taskAppointment);
    }

    @Test
    public void onePreviousTasksSynchronizeTheSameTaskAppointment_updateExistingAppointment() {
        // Current taskViewModel holds one task
        when(viewModel.getAllTasks()).thenReturn(Collections.singletonList(taskAppointment));

        // Perform synchronization
        taskAppointments.add(taskAppointment);
        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);

        // Since taskAppointment was in the viewModel before
        // it should have been updated during synchronization
        verify(viewModel).updateAppointment(taskAppointment);
    }

    @Test
    public void onePreviousTaskSynchronizeNewTaskAppointment_updateExistingInsertNewTaskAppointment() {
        // Current taskViewModel holds taskAppointment task
        when(viewModel.getAllTasks()).thenReturn(Collections.singletonList(taskAppointment));

        // Create and add to list a second task
        TaskAppointment taskAppointment2 = createTaskAppointment(1);
        taskAppointments.add(taskAppointment2);

        // Perform synchronization
        taskAppointments.add(taskAppointment);
        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);

        // Since taskAppointment2 was not in the viewModel before
        // it should have been inserted during synchronization
        verify(viewModel).insertAppointment(taskAppointment2);

        // Since taskAppointment was  in the viewModel before
        // it should have been updated during synchronization
        verify(viewModel).updateAppointment(taskAppointment);
    }

    @Test
    public void noPreviousTasksSynchronizeNewTaskChecklist_insertChecklistCalled() {
        // Current taskViewModel does not hold any tasks
        when(viewModel.getAllTasks()).thenReturn(Collections.emptyList());

        // Perform synchronization
        taskChecklists.add(taskChecklist);
        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);

        // Since taskChecklist was not in the viewModel before
        // it should have been inserted during synchronization
        verify(viewModel).insertChecklist(taskChecklist);
    }

    @Test
    public void onePreviousTaskSynchronizeTheSameTaskChecklist_updateExistingChecklist() {
        // Current taskViewModel holds one task
        when(viewModel.getAllTasks()).thenReturn(Collections.singletonList(taskChecklist));

        // Perform synchronization
        taskChecklists.add(taskChecklist);
        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);

        // Since taskChecklist was in the viewModel before
        // it should have been updated during synchronization
        verify(viewModel).updateChecklist(taskChecklist);
    }

    @Test
    public void onePreviousTaskChecklistSynchronizeNewTaskAppointment_updateExistingTaskChecklistInsertNewTaskAppointment() {
        // Current taskViewModel holds taskChecklist task
        when(viewModel.getAllTasks()).thenReturn(Collections.singletonList(taskChecklist));

        // Perform synchronization
        taskAppointments.add(taskAppointment);
        taskChecklists.add(taskChecklist);
        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);

        // Since taskAppointment was not in the viewModel before
        // it should have been inserted during synchronization
        verify(viewModel).insertAppointment(taskAppointment);

        // Since taskChecklist was  in the viewModel before
        // it should have been updated during synchronization
        verify(viewModel).updateChecklist(taskChecklist);
    }
}