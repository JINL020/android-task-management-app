package at.ac.univie.se2_team_0308.utils;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.utils.filter.FilterManager;
import at.ac.univie.se2_team_0308.utils.filter.HiddenTasksFilter;
import at.ac.univie.se2_team_0308.utils.filter.UnhiddenTasksFilter;

@RunWith(AndroidJUnit4.class)
public class FilterManagerTest {

    @Test
    public void ApplyFilterToTheEmptyList_getEmptyList() {
        List<ATask> emptyTaskList = new ArrayList<>();
        List<ATask> filteredTaskList = new FilterManager().applyFilter(emptyTaskList, new HiddenTasksFilter());
        assertEquals(0, filteredTaskList.size());
    }

    @Test
    public void ApplyHiddenFilterToTaskList_getOnlyHiddenTasks() {
        List<ATask> initialTaskList = new ArrayList<>();

        TaskAppointment taskAppointment = new TaskAppointment(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2020, 5, 12),
                new ArrayList<>(),
                new byte[0],
                ""
        );

        TaskChecklist taskChecklist = new TaskChecklist(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>(),
                new ArrayList<>(),
                new byte[0],
                ""
        );

        initialTaskList.add(taskAppointment);
        initialTaskList.add(taskChecklist);

        List<ATask> filteredTaskList = new FilterManager().applyFilter(initialTaskList, new HiddenTasksFilter());
        assertEquals(0, filteredTaskList.size());

        taskAppointment.setHidden(true);
        filteredTaskList = new FilterManager().applyFilter(initialTaskList, new HiddenTasksFilter());
        assertEquals(1, filteredTaskList.size());

        taskChecklist.setHidden(true);
        filteredTaskList = new FilterManager().applyFilter(initialTaskList, new HiddenTasksFilter());
        assertEquals(2, filteredTaskList.size());
    }

    @Test
    public void ApplyUnhiddenFilterToTaskList_getOnlyUnhiddenTasks() {
        List<ATask> initialTaskList = new ArrayList<>();

        TaskAppointment taskAppointment = new TaskAppointment(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2020, 5, 12),
                new ArrayList<>(),
                new byte[0],
                ""
        );

        TaskChecklist taskChecklist = new TaskChecklist(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>(),
                new ArrayList<>(),
                new byte[0],
                ""
        );

        initialTaskList.add(taskAppointment);
        initialTaskList.add(taskChecklist);

        List<ATask> filteredTaskList = new FilterManager().applyFilter(initialTaskList, new UnhiddenTasksFilter());
        assertEquals(2, filteredTaskList.size());

        taskAppointment.setHidden(true);
        filteredTaskList = new FilterManager().applyFilter(initialTaskList, new UnhiddenTasksFilter());
        assertEquals(1, filteredTaskList.size());

        taskChecklist.setHidden(true);
        filteredTaskList = new FilterManager().applyFilter(initialTaskList, new UnhiddenTasksFilter());
        assertEquals(0, filteredTaskList.size());
    }

    @Test
    public void ApplyFilterToNull_getError() {
        List<ATask> initialTaskList = null;

        Exception message = assertThrows(NullPointerException.class, () -> {
            new FilterManager().applyFilter(initialTaskList, new UnhiddenTasksFilter());
        });
    }

}
