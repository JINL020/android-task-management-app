package at.ac.univie.se2_team_0308.models;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class SubtaskList extends ASubtask {

    private List<ASubtask> subtasks;

    public SubtaskList(String name) {
        super(name);
        this.subtasks = new ArrayList<>();
    }
    public SubtaskList(int id, String name, EStatus state){
        super(id, name, state);
    }

    protected SubtaskList(Parcel in) {
        super(in);
        this.subtasks = in.readArrayList(SubtaskList.class.getClassLoader());
    }

    public static final Creator<SubtaskList> CREATOR = new Creator<SubtaskList>() {
        @Override
        public SubtaskList createFromParcel(Parcel in) {
            return new SubtaskList(in);
        }

        @Override
        public SubtaskList[] newArray(int size) {
            return new SubtaskList[size];
        }
    };

    @Override
    public void setState(EStatus state) {
        // set parent state to all children subtasks
        for (ASubtask s: subtasks){
            s.setState(state);
        }
        this.state = state;
    }

    @Override
    public void setSubtasks(List<ASubtask> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public List<ASubtask> getSubtasks() {
        return this.subtasks;
    }

    @Override
    public void addSubtask(ASubtask subtask){
        this.subtasks.add(subtask);
    }
    @Override
    public void removeSubtask(ASubtask subtask){
        // for every task which contains subtasks, remove all children
        if(subtask instanceof SubtaskList){
            ((SubtaskList) subtask).removeAllSubtasks();
        }
        this.subtasks.remove(subtask);
    }

    public void removeAllSubtasks(){
        this.subtasks.clear();
    }

    @Override
    public String toString(){
        String out = "SubtaskList{" +
                "id=" + getId() +
                "name='" + getName() + '\'' +
                ", status=" + getState() +
                "subtasks=[";
        for(ASubtask s: subtasks){
            out.concat(s.toString());
        }
        return out + "]}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeList(getSubtasks());
    }
}
