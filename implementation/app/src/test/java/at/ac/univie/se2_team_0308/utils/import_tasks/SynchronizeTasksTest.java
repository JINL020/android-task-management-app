package at.ac.univie.se2_team_0308.utils.import_tasks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class SynchronizeTasksTest extends TestCase {
    private TaskViewModel viewModel;
    private TaskSynchronizer taskSynchronizer;
    private List<TaskAppointment> taskAppointments;
    private List<TaskChecklist> taskChecklists;

    @Before
    public void setUp() {
        viewModel = mock(TaskViewModel.class);
        taskSynchronizer = new TaskSynchronizer();
        taskAppointments = new ArrayList<>();
        taskChecklists = new ArrayList<>();
    }

//    @Test
//    public void testSynchronizeTasks_insertNewAppointment() {
//        // Arrange
//        // TODO: Fix constructor
//        TaskAppointment taskAppointment = new TaskAppointment();
//        taskAppointments.add(taskAppointment);
//        when(viewModel.getAllTasks()).thenReturn(Collections.emptyList());
//
//        // Act
//        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);
//
//        // Assert
//        verify(viewModel).insertAppointment(taskAppointment);
//    }
//
//    @Test
//    public void testSynchronizeTasks_updateExistingAppointment() {
//        // Arrange
//        TaskAppointment taskAppointment = new TaskAppointment();
//        taskAppointments.add(taskAppointment);
//        when(viewModel.getAllTasks()).thenReturn(Collections.singletonList(taskAppointment));
//
//        // Act
//        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);
//
//        // Assert
//        verify(viewModel).updateAppointment(taskAppointment);
//    }
//
//    @Test
//    public void testSynchronizeTasks_insertNewChecklist() {
//        // Arrange
//        TaskChecklist taskChecklist = new TaskChecklist();
//        taskChecklists.add(taskChecklist);
//        when(viewModel.getAllTasks()).thenReturn(Collections.emptyList());
//
//        // Act
//        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);
//
//        // Assert
//        verify(viewModel).insertChecklist(taskChecklist);
//    }
//
//
//    @Test
//    public void testSynchronizeTasks_updateExistingChecklist() {
//        // Arrange
//        TaskChecklist taskChecklist = new TaskChecklist();
//        taskChecklists.add(taskChecklist);
//        when(viewModel.getAllTasks()).thenReturn(Collections.singletonList(taskChecklist));
//
//        // Act
//        taskSynchronizer.synchronizeTasks(viewModel, taskAppointments, taskChecklists);
//
//        // Assert
//        verify(viewModel).insertChecklist(taskChecklist);
//    }




}