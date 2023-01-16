package at.ac.univie.se2_team_0308.models;

import static org.junit.Assert.assertEquals;

import android.os.Parcel;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ParcelableTaskChecklistTest {

    // START https://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
    @Test
    public void CompareParcelableTaskChecklistToNormalTaskChecklist_getSameValues() {
        TaskChecklist test = new TaskChecklist(
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

        Parcel parcel = Parcel.obtain();
        test.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);


        TaskChecklist createdFromParcel = TaskChecklist.CREATOR.createFromParcel(parcel);
        assertEquals(test, createdFromParcel);
    }
    // END https://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
}
