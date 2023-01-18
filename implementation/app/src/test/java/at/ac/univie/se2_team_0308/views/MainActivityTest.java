package at.ac.univie.se2_team_0308.views;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.utils.notifications.ENotificationEvent;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.viewmodels.EventNotifierViewModel;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

     @Before
     public void setupClass(){
         EventNotifierViewModel eventNotifierViewModel = Mockito.mock(EventNotifierViewModel.class);
         Mockito.when(eventNotifierViewModel.getOnCreateNotifier()).thenReturn(new LoggerCore());
     }

    @Test
    public void receivedUpdate_deadlinePassed_throwException() {

        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                Date deadline = new GregorianCalendar(2018, 2, 11).getTime();
                TaskAppointment appointment = new TaskAppointment("taskName",
                        "description",
                        EPriority.LOW,
                        EStatus.NOT_STARTED,
                        ECategory.APPOINTMENT,
                        deadline,
                        new ArrayList<>(),
                        new byte[0],
                        "");

                try {
                    activity.receivedUpdate(ENotificationEvent.CREATE, appointment);
                    Assert.fail("Should have thrown DeadlinePassedException");
                } catch (DeadlinePassedException e) {
                    System.out.println(e.getErrorMessage());
                }
            });
        }
    }

    @Test
    public void receivedUpdate_deadlineValid_SetAlarm() {

        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                Date deadline = new GregorianCalendar(2030, 2, 11).getTime();
                TaskAppointment appointment = new TaskAppointment("taskName",
                        "description",
                        EPriority.LOW,
                        EStatus.NOT_STARTED,
                        ECategory.APPOINTMENT,
                        deadline,
                        new ArrayList<>(),
                        new byte[0],
                        "");
                try {
                    activity.receivedUpdate(ENotificationEvent.CREATE, appointment);
                } catch (DeadlinePassedException e) {
                    System.out.println(e.getErrorMessage());
                    Assert.fail("Should have set alarm");
                }
            });
        }
    }
}