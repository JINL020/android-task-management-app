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

    private TaskAppointmentDao taskAppointmentDao;

    private TaskChecklistDao taskChecklistDao;

    private static class utils {
        public static TaskAppointment taskAppointment1 = new TaskAppointment(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2020, 5, 12),
                new ArrayList<>()
        );

        public static TaskAppointment taskAppointment2 = new TaskAppointment(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2020, 5, 12),
                new ArrayList<>()
        );

        public static TaskAppointment taskAppointment3 = new TaskAppointment(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2020, 5, 12),
                new ArrayList<>()
        );

        public static TaskChecklist taskChecklist1 = new TaskChecklist(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>(),
                new ArrayList<>()
        );

        public static TaskChecklist taskChecklist2 = new TaskChecklist(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>(),
                new ArrayList<>()
        );

        public static TaskChecklist taskChecklist3 = new TaskChecklist(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>(),
                new ArrayList<>()
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
        utils.setAppointmentList();
        utils.setChecklistList();
    }

    @Test
    public void updateTaskAppointment_ChangeInDb() throws ExecutionException, InterruptedException {

        taskAppointmentDao.insert(utils.taskAppointment1);
        taskAppointmentDao.insert(utils.taskAppointment2);
        taskAppointmentDao.insert(utils.taskAppointment3);

        List<Integer> selectedIds = new ArrayList<>();

        for (TaskAppointment taskAppointment: taskAppointmentDao.getAllTasksList()) {
            if (taskAppointment.isSelected()) {
                selectedIds.add(taskAppointment.getId());
            }
        }

        taskAppointmentDao.updateTaskPriority(selectedIds, EPriority.HIGH);
        utils.taskAppointment1.setPriority(taskAppointmentDao.getAllTasksList().get(0).getPriority());
        utils.taskAppointment2.setPriority(taskAppointmentDao.getAllTasksList().get(1).getPriority());
        utils.taskAppointment3.setPriority(taskAppointmentDao.getAllTasksList().get(2).getPriority());

        Assert.assertEquals(utils.taskAppointment1.getPriority().toString(), taskAppointmentDao.getAllTasksList().get(0).getPriority().toString());
        Assert.assertEquals(utils.taskAppointment2.getPriority().toString(), taskAppointmentDao.getAllTasksList().get(1).getPriority().toString());
        Assert.assertEquals(utils.taskAppointment3.getPriority().toString(), taskAppointmentDao.getAllTasksList().get(2).getPriority().toString());
    }

    @Test
    public void updateTaskChecklist_ChangeInDb() throws ExecutionException, InterruptedException {
        taskChecklistDao.insert(utils.taskChecklist1);
        taskChecklistDao.insert(utils.taskChecklist2);
        taskChecklistDao.insert(utils.taskChecklist3);

        List<Integer> selectedIds = new ArrayList<>();

        for (TaskChecklist taskChecklist: taskChecklistDao.getAllTasksList()) {

            if (taskChecklist.isSelected()) {
                selectedIds.add(taskChecklist.getId());
            }
        }

        taskChecklistDao.updateTaskPriority(selectedIds, EPriority.HIGH);
        utils.taskChecklist1.setPriority(taskChecklistDao.getAllTasksList().get(0).getPriority());
        utils.taskChecklist2.setPriority(taskChecklistDao.getAllTasksList().get(1).getPriority());
        utils.taskChecklist3.setPriority(taskChecklistDao.getAllTasksList().get(2).getPriority());

        Assert.assertEquals(utils.taskChecklist1.getPriority().toString(), taskChecklistDao.getAllTasksList().get(0).getPriority().toString());
        Assert.assertEquals(utils.taskChecklist2.getPriority().toString(), taskChecklistDao.getAllTasksList().get(1).getPriority().toString());
        Assert.assertEquals(utils.taskChecklist3.getPriority().toString(), taskChecklistDao.getAllTasksList().get(2).getPriority().toString());

    }

    @After
    public void closeDb() {
        database.close();
    }
}
