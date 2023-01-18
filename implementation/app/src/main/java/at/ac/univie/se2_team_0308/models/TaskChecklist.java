package at.ac.univie.se2_team_0308.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import at.ac.univie.se2_team_0308.utils.typeConverters.SubtasksConverter;

@Entity(tableName = "task_checklists")
public class TaskChecklist extends ATask implements Parcelable {
    @TypeConverters(SubtasksConverter.class)
    List<ASubtask> subtasks;

    public TaskChecklist(String taskName, String description,  EPriority priority, EStatus status, ECategory category, List<ASubtask> subtasks, List<Attachment> attachments, byte[] sketchData, String taskColor){
        super(taskName, description, priority, status, category, attachments, sketchData, taskColor);
        this.subtasks = new ArrayList<>();
        setSubtasks(subtasks);
    }

    protected TaskChecklist(Parcel in) {
        super("","", EPriority.LOW, EStatus.NOT_STARTED, ECategory.CHECKLIST, new ArrayList<>(), new byte[0], "#E1E1E1");
        setId(in.readInt());
        setTaskName(in.readString());
        setDescription(in.readString());
        setPriority(EPriority.valueOf(in.readString()));
        setStatus(EStatus.valueOf(in.readString()));
        setCategory(ECategory.valueOf(in.readString()));
        setCreationDate(new Date(in.readLong()));
        setSubtasks(in.readArrayList(ASubtask.class.getClassLoader()));
        setAttachments(in.readArrayList(Attachment.class.getClassLoader()));
        byte[] arr = new byte[in.readInt()];
        in.readByteArray(arr);
        setSketchData(arr);
        setTaskColor(in.readString());
    }

    public static final Creator<TaskChecklist> CREATOR = new Creator<TaskChecklist>() {
        @Override
        public TaskChecklist createFromParcel(Parcel in) {
            return new TaskChecklist(in);
        }

        @Override
        public TaskChecklist[] newArray(int size) {
            return new TaskChecklist[size];
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
        parcel.writeString(ECategory.CHECKLIST.name());
        parcel.writeLong(getCreationDate().getTime());
        parcel.writeList(subtasks);
        parcel.writeList(getAttachments());
        parcel.writeInt(getSketchData().length);
        parcel.writeByteArray(getSketchData());
        parcel.writeString(getTaskColor());
    }

    @Override
    public String toString() {
        return "TaskChecklist{" +
                super.toString() +
                "subtasks=" + subtasks.toString() +
                '}';
    }

    public List<ASubtask> getSubtasks() {
        return subtasks;
    }
    public void setSubtasks(List<ASubtask> subtasks) {
        if(subtasks != null) {
            this.subtasks = subtasks;
        }
    }

    void removeAllSubtasks(){
        this.subtasks.clear();
    }

    void addSubtask(ASubtask subtask){
        this.subtasks.add(subtask);
    }

    void removeSubtask(ASubtask subtask){
        subtask.removeAllSubtasks();
        this.subtasks.remove(subtask);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskChecklist that = (TaskChecklist) o;
        return Objects.equals(subtasks, that.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtasks);
    }
}
