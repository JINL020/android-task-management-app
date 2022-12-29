package at.ac.univie.se2_team_0308;

import static org.junit.Assert.assertEquals;

import android.os.Parcel;

import org.junit.Test;

import java.util.Date;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;

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
                new Date(2020, 5, 12)
        );

        Parcel parcel = Parcel.obtain();
        test.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        TaskAppointment createdFromParcel = TaskAppointment.CREATOR.createFromParcel(parcel);
        assertEquals(test, createdFromParcel);
    }
    // END https://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
}
