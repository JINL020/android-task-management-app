package at.ac.univie.se2_team_0308.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import at.ac.univie.se2_team_0308.utils.SubtasksConverter;

@Entity(tableName = "task_checklists")
public class TaskChecklist extends ATask implements Parcelable {
    @TypeConverters(SubtasksConverter.class)
    List<Subtask> subtasks;

    public TaskChecklist(String taskName, String description,  EPriority priority, EStatus status, ECategory category, List<Subtask> subtasks){
        super(taskName, description, priority, status, category);
        this.subtasks = subtasks;
    }

    protected TaskChecklist(Parcel in) {
        super("","", EPriority.LOW, EStatus.NOT_STARTED, ECategory.CHECKLIST);
        setId(in.readInt());
        setTaskName(in.readString());
        setDescription(in.readString());
        setPriority(EPriority.valueOf(in.readString()));
        setStatus(EStatus.valueOf(in.readString()));
        setCategory(ECategory.valueOf(in.readString()));
        setCreationDate(new Date(in.readLong()));
        setSubtasks(in.readArrayList(Subtask.class.getClassLoader()));
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
        parcel.writeList(getSubtasks());
    }

    @Override
    public String toString() {
        return "TaskChecklist{" +
                super.toString() +
                "subtasks=" + subtasks.toString() +
                '}';
    }

    public List<Subtask> getSubtasks() { return subtasks;}
    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    void removeAllSubtasks(){
        this.subtasks.clear();
    }

    void addSubtask(Subtask subtask){
        this.subtasks.add(subtask);
    }

    void removeSubtask(Subtask subtask){
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
