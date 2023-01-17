package at.ac.univie.se2_team_0308.repository;

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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

@RunWith(AndroidJUnit4.class)
public class UpdateCommonPropertyTest {
    private AppDatabase database;

    private ITaskAppointmentDao taskAppointmentDao;

    private ITaskChecklistDao taskChecklistDao;

    private static class Utils {
        public static TaskAppointment taskAppointment1 = new TaskAppointment(
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

        public static TaskAppointment taskAppointment2 = new TaskAppointment(
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

        public static TaskAppointment taskAppointment3 = new TaskAppointment(
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

        public static TaskChecklist taskChecklist1 = new TaskChecklist(
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

        public static TaskChecklist taskChecklist2 = new TaskChecklist(
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

        public static TaskChecklist taskChecklist3 = new TaskChecklist(
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

        public static List<TaskAppointment> appointmentList = new ArrayList<>();
        public static List<TaskChecklist> checklistList = new ArrayList<>();
        public static List<TaskAppointment> selectedAppointmentList = new ArrayList<>();
        public static List<TaskChecklist> selectedChecklistList = new ArrayList<>();

        public static void setAppointmentList() {
            appointmentList.add(taskAppointment1);
            appointmentList.add(taskAppointment2);
            appointmentList.add(taskAppointment3);
            selectedAppointmentList.add(taskAppointment1);
            taskAppointment1.setSelected(true);
            selectedAppointmentList.add(taskAppointment3);
            taskAppointment3.setSelected(true);
        }

        public static void setChecklistList() {
            checklistList.add(taskChecklist1);
            checklistList.add(taskChecklist2);
            checklistList.add(taskChecklist3);
            selectedChecklistList.add(taskChecklist3);
            taskChecklist3.setSelected(true);
            selectedChecklistList.add(taskChecklist2);
            taskChecklist2.setSelected(true);
        }

    }



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
        Utils.setAppointmentList();
        Utils.setChecklistList();
    }

    @Test
    public void UpdateTaskAppointment_ChangeInDb() throws ExecutionException, InterruptedException {

        taskAppointmentDao.insert(Utils.taskAppointment1);
        taskAppointmentDao.insert(Utils.taskAppointment2);
        taskAppointmentDao.insert(Utils.taskAppointment3);

        List<Integer> selectedIds = new ArrayList<>();

        for (TaskAppointment taskAppointment: taskAppointmentDao.getAllTasksList()) {
            if (taskAppointment.isSelected()) {
                selectedIds.add(taskAppointment.getId());
            }
        }

        taskAppointmentDao.updateTaskPriority(selectedIds, EPriority.HIGH);
        Utils.taskAppointment1.setPriority(taskAppointmentDao.getAllTasksList().get(0).getPriority());
        Utils.taskAppointment2.setPriority(taskAppointmentDao.getAllTasksList().get(1).getPriority());
        Utils.taskAppointment3.setPriority(taskAppointmentDao.getAllTasksList().get(2).getPriority());

        Assert.assertEquals(Utils.taskAppointment1.getPriority().toString(), taskAppointmentDao.getAllTasksList().get(0).getPriority().toString());
        Assert.assertEquals(Utils.taskAppointment2.getPriority().toString(), taskAppointmentDao.getAllTasksList().get(1).getPriority().toString());
        Assert.assertEquals(Utils.taskAppointment3.getPriority().toString(), taskAppointmentDao.getAllTasksList().get(2).getPriority().toString());
    }

    @Test
    public void UpdateTaskChecklist_ChangeInDb() throws ExecutionException, InterruptedException {
        taskChecklistDao.insert(Utils.taskChecklist1);
        taskChecklistDao.insert(Utils.taskChecklist2);
        taskChecklistDao.insert(Utils.taskChecklist3);

        List<Integer> selectedIds = new ArrayList<>();

        for (TaskChecklist taskChecklist: taskChecklistDao.getAllTasksList()) {

            if (taskChecklist.isSelected()) {
                selectedIds.add(taskChecklist.getId());
            }
        }

        taskChecklistDao.updateTaskPriority(selectedIds, EPriority.HIGH);
        Utils.taskChecklist1.setPriority(taskChecklistDao.getAllTasksList().get(0).getPriority());
        Utils.taskChecklist2.setPriority(taskChecklistDao.getAllTasksList().get(1).getPriority());
        Utils.taskChecklist3.setPriority(taskChecklistDao.getAllTasksList().get(2).getPriority());

        Assert.assertEquals(Utils.taskChecklist1.getPriority().toString(), taskChecklistDao.getAllTasksList().get(0).getPriority().toString());
        Assert.assertEquals(Utils.taskChecklist2.getPriority().toString(), taskChecklistDao.getAllTasksList().get(1).getPriority().toString());
        Assert.assertEquals(Utils.taskChecklist3.getPriority().toString(), taskChecklistDao.getAllTasksList().get(2).getPriority().toString());

    }

    @After
    public void closeDb() {
        database.close();
    }
}
