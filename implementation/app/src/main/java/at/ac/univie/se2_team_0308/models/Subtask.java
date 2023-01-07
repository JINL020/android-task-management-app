package at.ac.univie.se2_team_0308.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity(tableName = "checklist_subtask")
public class Subtask  implements Parcelable {
    public static final String TAG = "Subtask";

    @PrimaryKey(autoGenerate = true)
    private int id;
    private EStatus state;
    private String name;
    private List<Subtask> subtasks;

    public Subtask(String name){
        this.name = name;
        this.state = EStatus.NOT_STARTED;
        this.subtasks = new ArrayList<>();
    }

    protected Subtask(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setState(EStatus.valueOf(in.readString()));
        this.subtasks = new ArrayList<>();
        setSubtasks(in.createTypedArrayList(Subtask.CREATOR));
    }

    public static final Creator<Subtask> CREATOR = new Creator<Subtask>() {
        @Override
        public Subtask createFromParcel(Parcel in) {
            return new Subtask(in);
        }

        @Override
        public Subtask[] newArray(int size) {
            return new Subtask[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int subtaskId) {
        this.id = subtaskId;
    }

    public EStatus getState() {
        return state;
    }

    public void setState(EStatus state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSubtask(Subtask subtask){
        this.subtasks.add(subtask);
    }

    public void removeSubtask(Subtask task){
        task.removeAllSubtasks();
        this.subtasks.remove(task);
    }

    public void removeSubtaskById(int id){
        Subtask toRemove = getSubtaskById(id);
        toRemove.removeAllSubtasks();
        this.subtasks.remove(toRemove);
    }

    public void removeAllSubtasks(){
        this.subtasks.clear();
    }

    public Subtask getSubtaskById(int id){
        for(Subtask s : subtasks){
            if(s.getId() == id){
                return s;
            }
        }
        return null; // TODO throw exception: no subtask with given id
    }

    @Override
    public String toString(){
        String resultString = "";
        resultString.concat("Subtask{" +
                "name='" + name + '\'' +
                ", status=" + state +
                ", subtasks=[");
        List<String> subtaskListStr = new ArrayList<String>();
        for(Subtask s: this.subtasks){
            subtaskListStr.add(s.toString());
        }
        resultString.concat(String.join(",", subtaskListStr));
        resultString.concat("]}");
        return resultString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask task = (Subtask) o;
        return id == task.id && Objects.equals(name, task.name) && state == task.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, state);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(String.valueOf(getState()));
        parcel.writeTypedList(getSubtasks());
    }
}
