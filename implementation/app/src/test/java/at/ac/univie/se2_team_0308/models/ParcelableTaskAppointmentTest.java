package at.ac.univie.se2_team_0308.models;

import static org.junit.Assert.assertEquals;

import android.os.Parcel;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class ParcelableTaskAppointmentTest {

    // START https://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
    @Test
    public void compareParcelableTaskAppointmentToNormalTaskAppointment_getSameValues() {
        TaskAppointment test = new TaskAppointment(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2020, 5, 12),
                new ArrayList<>(),
                new byte[0]
        );

        Parcel parcel = Parcel.obtain();
        test.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        TaskAppointment createdFromParcel = TaskAppointment.CREATOR.createFromParcel(parcel);
        assertEquals(test, createdFromParcel);
    }
    // END https://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
}
