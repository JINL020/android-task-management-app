package at.ac.univie.se2_team_0308;

import static org.junit.Assert.assertEquals;

import android.os.Parcel;

import org.junit.Test;

import java.util.ArrayList;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class ParcelableTaskChecklistTest {
    private TaskAppointment taskAppointment;
    private TaskAppointment parcelableTaskAppointment;
    //https://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
    @Test
    public void compareParcelableTaskChecklistToNormalTaskChecklist_getSameValues() {
        TaskChecklist test = new TaskChecklist(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>()
        );

        // Obtain a Parcel object and write the parcelable object to it:
        Parcel parcel = Parcel.obtain();
        test.writeToParcel(parcel, 0);

        // After you're done with writing, you need to reset the parcel for reading:
        parcel.setDataPosition(0);

        // Reconstruct object from parcel and asserts:
        TaskChecklist createdFromParcel = TaskChecklist.CREATOR.createFromParcel(parcel);
        assertEquals(test, createdFromParcel);
    }
}
