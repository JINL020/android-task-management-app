package at.ac.univie.se2_team_0308.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import at.ac.univie.se2_team_0308.utils.typeConverters.DateConverter;

@Entity(tableName = "task_appointments")
public class TaskAppointment extends ATask implements Parcelable {

    @TypeConverters(DateConverter.class)
    private Date deadline;

    public TaskAppointment(String taskName, String description, EPriority priority, EStatus status, ECategory category, Date deadline, List<Attachment> attachments, byte[] sketchData, String taskColor){
        super(taskName, description, priority, status, category, attachments, sketchData, taskColor);
        this.deadline = deadline;
    }

    protected TaskAppointment(Parcel in) {
        super("","", EPriority.LOW, EStatus.NOT_STARTED, ECategory.APPOINTMENT, new ArrayList<>(), new byte[0], "#E1E1E1");
        setId(in.readInt());
        setTaskName(in.readString());
        setDescription(in.readString());
        setPriority(EPriority.valueOf(in.readString()));
        setStatus(EStatus.valueOf(in.readString()));
        setCategory(ECategory.valueOf(in.readString()));
        deadline = new Date(in.readLong());
        setCreationDate(new Date(in.readLong()));
        setAttachments(in.readArrayList(Attachment.class.getClassLoader()));
        byte[] arr = new byte[in.readInt()];
        in.readByteArray(arr);
        setSketchData(arr);
        setTaskColor(in.readString());
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
        parcel.writeInt(getId());
        parcel.writeString(getTaskName());
        parcel.writeString(getDescription());
        parcel.writeString(String.valueOf(getPriority()));
        parcel.writeString(String.valueOf(getStatus()));
        parcel.writeString(ECategory.APPOINTMENT.name());
        parcel.writeLong(deadline.getTime());
        parcel.writeLong(getCreationDate().getTime());
        parcel.writeList(getAttachments());
        parcel.writeInt(getSketchData().length);
        parcel.writeByteArray(getSketchData());
        parcel.writeString(getTaskColor());
    }

    @Override
    public String toString() {
        return "TaskAppointment{" +
                super.toString() +
                "deadline=" + deadline +
                '}';
    }

    public Date getDeadline(){
        return deadline;
    }

    public void setDeadline(Date deadline){
        this.deadline = deadline;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskAppointment that = (TaskAppointment) o;
        return Objects.equals(deadline, that.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deadline);
    }

}
