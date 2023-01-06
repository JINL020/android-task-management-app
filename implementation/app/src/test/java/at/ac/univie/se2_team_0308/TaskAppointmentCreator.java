package at.ac.univie.se2_team_0308;

import android.os.Parcel;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;

public class TaskAppointmentCreator {
    public static List<TaskAppointment> createTaskAppointment(){
        long time1 = 20000000;
        Parcel parcel1 = Parcel.obtain();
        parcel1.writeInt(1);
        parcel1.writeString("taskName1");
        parcel1.writeString("description1");
        parcel1.writeString(String.valueOf(EPriority.LOW));
        parcel1.writeString(String.valueOf(EStatus.NOT_STARTED));
        parcel1.writeString(String.valueOf(ECategory.APPOINTMENT));
        parcel1.writeLong(time1);
        parcel1.writeLong(time1);
        parcel1.setDataPosition(0);
        TaskAppointment taskAppointment1 = new TaskAppointment(parcel1);

        long time2 = 1111111111;
        Parcel parcel2 = Parcel.obtain();
        parcel2.writeInt(2);
        parcel2.writeString("taskName2");
        parcel2.writeString("description2");
        parcel2.writeString(String.valueOf(EPriority.LOW));
        parcel2.writeString(String.valueOf(EStatus.NOT_STARTED));
        parcel2.writeString(String.valueOf(ECategory.APPOINTMENT));
        parcel2.writeLong(time2);
        parcel2.writeLong(time2);
        parcel2.setDataPosition(0);
        TaskAppointment taskAppointment2 = new TaskAppointment(parcel2);

        return  Arrays.asList(taskAppointment1, taskAppointment2);
    }
}
