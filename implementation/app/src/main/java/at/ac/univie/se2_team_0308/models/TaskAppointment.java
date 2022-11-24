package at.ac.univie.se2_team_0308.models;
import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

@Entity(tableName = "task_appointments")
public class TaskAppointment extends ATask implements Parcelable {
    private Date deadline;

    public TaskAppointment(String taskName, String description, EPriority priority, EStatus status, ECategory category, Date deadline){
        super(taskName, description, priority, status, category);
        this.deadline = deadline;
    }

    protected TaskAppointment(Parcel in) {
        super("","", EPriority.LOW, EStatus.NOT_STARTED, ECategory.APPOINTMENT);
        setId(in.readInt());
        setTaskName(in.readString());
        setDescription(in.readString());
        setPriority(EPriority.valueOf(in.readString()));
        setStatus(EStatus.valueOf(in.readString()));
        setCategory(ECategory.valueOf(in.readString()));
        deadline = new Date(in.readLong());
        setCreationDate(new Date(in.readLong()));
    }

    public static final Creator<TaskAppointment> CREATOR = new Creator<TaskAppointment>() {
        @Override
        public TaskAppointment createFromParcel(Parcel in) {
            return new TaskAppointment(in);
        }

        @Override
        public TaskAppointment[] newArray(int size) {
            return new TaskAppointment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //TODO
        parcel.writeInt(getId());
        parcel.writeString(getTaskName());
        parcel.writeString(getDescription());
        parcel.writeString(String.valueOf(getPriority()));
        parcel.writeString(String.valueOf(getStatus()));
        parcel.writeString(ECategory.APPOINTMENT.name());
        parcel.writeLong(deadline.getTime());
        parcel.writeLong(getCreationDate().getTime());
    }

    public Date getDeadline(){
        return deadline;
    }

    public void setDeadline(Date deadline){
        this.deadline = deadline;
    }
}