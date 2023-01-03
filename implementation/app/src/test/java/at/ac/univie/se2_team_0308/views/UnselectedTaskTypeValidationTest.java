package at.ac.univie.se2_team_0308.views;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.LooperMode;

import at.ac.univie.se2_team_0308.R;

@RunWith(AndroidJUnit4.class)
@LooperMode(LooperMode.Mode.PAUSED)
public class UnselectedTaskTypeValidationTest {

    private Activity context;

    @Before
    public void setUp() {
        context = Robolectric.buildActivity(MainActivity.class).create().start().resume().visible().get();
    }

    // https://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment
    @Test
    public void ClickOnAddTask_DialogFragmentShows() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();

            activity.findViewById(R.id.fabAdd).performClick();
            DialogFragment dialogFragment = new AddTaskFragment();
            dialogFragment.show(activity.getSupportFragmentManager(), AddTaskFragment.TAG);

            getInstrumentation().waitForIdleSync();
            assertTrue(((DialogFragment) dialogFragment).getShowsDialog());
        }
    }

    @Test
    public void ClickOnAddTask_TaskTypeNotSelected_GetValidationTextMessage() {
        // START https://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();
            // END https://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment

            activity.findViewById(R.id.fabAdd).performClick();
            DialogFragment dialogFragment = new AddTaskFragment();
            dialogFragment.show(activity.getSupportFragmentManager(), AddTaskFragment.TAG);

            getInstrumentation().waitForIdleSync();
            assertTrue(((DialogFragment) dialogFragment).getShowsDialog());

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder.setPositiveButton("Add", null);
            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
            assertTrue(alertDialog.isShowing());
            assertNotNull(dialogFragment.getDialog().findViewById(R.id.editTaskRadioGroupTaskType));
            dialogFragment.getDialog().findViewById(R.id.radioButtonAppointment).setSelected(false);
            dialogFragment.getDialog().findViewById(R.id.radioButtonChecklist).setSelected(false);

            assertEquals(View.VISIBLE, alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).getVisibility());
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();

            assertNotNull(dialogFragment.getDialog().findViewById(R.id.taskTypeValidation));
            TextView results = (TextView) dialogFragment.getDialog().findViewById(R.id.taskTypeValidation);
            assertEquals("Please Select Task Type",  results.getText().toString());

        }
    }


}
