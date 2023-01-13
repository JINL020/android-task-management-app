package at.ac.univie.se2_team_0308.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.PrimaryKey;

import java.util.List;
import java.util.Objects;

public abstract class ASubtask implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    protected EStatus state;
    private String name;

    public ASubtask(String name){
        this.name = name;
        this.state = EStatus.NOT_STARTED;
    }
    public ASubtask(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.state = EStatus.valueOf(in.readString());
    }
    public ASubtask(int id, String name, EStatus state){
        this.name = name;
        this.id = id;
        setState(state);
    }

    abstract public void addSubtask(ASubtask subtask);
    abstract public void removeSubtask(ASubtask subtask);
    abstract public void setState(EStatus state);
    abstract public void removeAllSubtasks();
    abstract public void setSubtasks(List<ASubtask> subtasks);
    abstract public List<ASubtask> getSubtasks();

    public int getId() {
        return id;
    }

    public void setId(int subtaskId) {
        this.id = subtaskId;
    }

    public EStatus getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ASubtask task = (ASubtask) o;
        return getId() == task.getId() && Objects.equals(getName(), task.getName()) && getState() == task.getState();
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, state);
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(String.valueOf(getState()));
    }

}
