package at.ac.univie.se2_team_0308.views;

import static org.junit.Assert.assertNotNull;

import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import at.ac.univie.se2_team_0308.R;

@RunWith(RobolectricTestRunner.class)
public class PressSelectButtonTest {

    @Test
    public void ClickOnBtnSelect_BtnUpdateAppears() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup(); // Moves Activity to RESUMED state
            MainActivity activity = controller.get();

            activity.findViewById(R.id.btnSelect).performClick();
            assertNotNull((TextView) activity.findViewById(R.id.btnUpdateCommonProperties));
            //assertEquals(((TextView) activity.findViewById(R.id.btnUpdate)).getText(), "Update");
        }
    }

    @Test
    public void ClickOnBtnSelect_BtnDeleteAppears() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup(); // Moves Activity to RESUMED state
            MainActivity activity = controller.get();

            activity.findViewById(R.id.btnSelect).performClick();
            assertNotNull((TextView) activity.findViewById(R.id.btnDelete));
            //assertEquals(((TextView) activity.findViewById(R.id.btnUpdate)).getText(), "Update");
        }
    }

    @Test
    public void ClickOnBtnSelect_BtnHide() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup(); // Moves Activity to RESUMED state
            MainActivity activity = controller.get();

            activity.findViewById(R.id.btnSelect).performClick();
            assertNotNull((TextView) activity.findViewById(R.id.btnHide));
            //assertEquals(((TextView) activity.findViewById(R.id.btnUpdate)).getText(), "Update");
        }
    }
}
