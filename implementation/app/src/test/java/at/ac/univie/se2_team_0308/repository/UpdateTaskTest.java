package at.ac.univie.se2_team_0308.repository;

// START: https://stackoverflow.com/questions/12166415/how-do-i-assert-an-iterable-contains-elements-with-a-certain-property
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.iterableWithSize;
// END: https://stackoverflow.com/questions/12166415/how-do-i-assert-an-iterable-contains-elements-with-a-certain-property

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

@RunWith(AndroidJUnit4.class)
public class UpdateTaskTest {
    private AppDatabase database;

    private TaskAppointmentDao taskAppointmentDao;

    private TaskChecklistDao taskChecklistDao;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    @Before
    public void setUpDatabase() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase.class
        ).allowMainThreadQueries().build();
        taskAppointmentDao = database.taskAppointmentDao();
        taskChecklistDao = database.taskChecklistDao();
    }

    @Test
    public void updateTaskAppointment_ChangeInDb() throws ExecutionException, InterruptedException {
        TaskAppointment taskAppointment = new TaskAppointment(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2020, 5, 12)
        );
        taskAppointmentDao.insert(taskAppointment);
        TaskAppointment updatedTaskAppointment = new TaskAppointment(
                "Modified",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2020, 5, 12)
        );
        updatedTaskAppointment.setId(taskAppointmentDao.getAllTasksList().get(0).getId());
        taskAppointmentDao.update(updatedTaskAppointment);
        assertThat(taskAppointmentDao.getAllTasksList(), iterableWithSize(1));
        Assert.assertEquals("Modified", taskAppointmentDao.getAllTasksList().get(0).getTaskName());
        // assertThat(toReturn, containsInAnyOrder(updatedTaskAppointment));
        //assertThat(toReturn, CoreMatchers.not(containsInAnyOrder(taskAppointment)));
    }

    @Test
    public void updateTaskChecklist_ChangeInDb() throws ExecutionException, InterruptedException {
        TaskChecklist taskChecklist = new TaskChecklist(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>()
        );
        taskChecklistDao.insert(taskChecklist);
        TaskChecklist updatedTaskChecklist = new TaskChecklist(
                "Modified",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>()
        );
        updatedTaskChecklist.setId(taskChecklistDao.getAllTasksList().get(0).getId());
        taskChecklistDao.update(updatedTaskChecklist);
        assertThat(taskChecklistDao.getAllTasksList(), iterableWithSize(1));
        Assert.assertEquals("Modified", taskChecklistDao.getAllTasksList().get(0).getTaskName());
        // assertThat(toReturn, containsInAnyOrder(updatedTaskAppointment));
        //assertThat(toReturn, CoreMatchers.not(containsInAnyOrder(taskAppointment)));
        }

    @After
    public void closeDb() {
        database.close();
    }
}
