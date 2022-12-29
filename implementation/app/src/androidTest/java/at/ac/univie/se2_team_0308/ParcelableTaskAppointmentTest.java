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
    private TaskAppointment taskAppointment;
    private TaskAppointment parcelableTaskAppointment;
    //https://stackoverflow.com/questions/12829700/android-unit-testing-bundle-parcelable
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

        // Obtain a Parcel object and write the parcelable object to it:
        Parcel parcel = Parcel.obtain();
        test.writeToParcel(parcel, 0);

        // After you're done with writing, you need to reset the parcel for reading:
        parcel.setDataPosition(0);

        // Reconstruct object from parcel and asserts:
        TaskAppointment createdFromParcel = TaskAppointment.CREATOR.createFromParcel(parcel);
        assertEquals(test, createdFromParcel);
    }
}
