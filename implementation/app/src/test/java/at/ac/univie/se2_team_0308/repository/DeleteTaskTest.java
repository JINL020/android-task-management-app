package at.ac.univie.se2_team_0308.repository;

// START: https://stackoverflow.com/questions/12166415/how-do-i-assert-an-iterable-contains-elements-with-a-certain-property
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.iterableWithSize;
// END: https://stackoverflow.com/questions/12166415/how-do-i-assert-an-iterable-contains-elements-with-a-certain-property

import static org.mockito.Mockito.mock;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

@RunWith(AndroidJUnit4.class)
public class DeleteTaskTest {
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
    public void deleteTaskAppointment_RemoveFromDb() throws ExecutionException, InterruptedException {
        TaskAppointment taskAppointment = new TaskAppointment(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                java.util.Date.from(Instant.now()),
                new ArrayList<>(),
                new byte[0],
                ""
        );
        taskAppointmentDao.insert(taskAppointment);
        assertThat(taskAppointmentDao.getAllTasksList(), iterableWithSize(1));
        taskAppointment.setId(taskAppointmentDao.getAllTasksList().get(0).getId());
        taskAppointmentDao.delete(taskAppointment);
        assertThat(taskAppointmentDao.getAllTasksList(), iterableWithSize(0));
    }

    @Test
    public void deleteTaskChecklist_RemoveFromDb() throws ExecutionException, InterruptedException {
        TaskViewModel viewModel = mock(TaskViewModel.class);
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
        taskChecklistDao.insert(taskChecklist);
        assertThat(taskChecklistDao.getAllTasksList(), iterableWithSize(1));
        taskChecklist.setId(taskChecklistDao.getAllTasksList().get(0).getId());
        taskChecklistDao.delete(taskChecklist);
        assertThat(taskChecklistDao.getAllTasksList(), iterableWithSize(0));
    }

    @After
    public void closeDb() throws IOException {
        database.close();
    }
}
