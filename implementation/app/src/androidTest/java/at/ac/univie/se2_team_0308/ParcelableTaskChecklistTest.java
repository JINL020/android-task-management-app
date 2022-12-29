package at.ac.univie.se2_team_0308;

import static org.junit.Assert.assertEquals;

import android.os.Parcel;

import org.junit.Test;

import java.util.ArrayList;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class ParcelableTaskChecklistTest {

    // START https://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
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

        Parcel parcel = Parcel.obtain();
        test.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        TaskChecklist createdFromParcel = TaskChecklist.CREATOR.createFromParcel(parcel);
        assertEquals(test, createdFromParcel);
    }
    // END https://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
}
