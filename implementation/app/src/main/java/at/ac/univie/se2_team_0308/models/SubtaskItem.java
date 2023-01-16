package at.ac.univie.se2_team_0308.models;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class SubtaskItem extends ASubtask {

    public SubtaskItem(String name) {
        super(name);
    }
    public SubtaskItem(int id, String name, EStatus state){
        super(id, name, state);
    }

    protected SubtaskItem(Parcel in) {
        super(in);
    }

    public static final Creator<SubtaskItem> CREATOR = new Creator<SubtaskItem>() {
        @Override
        public SubtaskItem createFromParcel(Parcel in) {
            return new SubtaskItem(in);
        }

        @Override
        public SubtaskItem[] newArray(int size) {
            return new SubtaskItem[size];
        }
    };

    @Override
    public void setState(EStatus state) {
        this.state = state;
    }

    @Override
    public void removeAllSubtasks() {

    }

    @Override
    public void setSubtasks(List<ASubtask> subtasks) {
    }

    @Override
    public List<ASubtask> getSubtasks() {
        return new ArrayList<>();
    }

    @Override
    public void addSubtask(ASubtask subtask){

    }
    @Override
    public void removeSubtask(ASubtask subtask){

    }

    @Override
    public String toString(){
        return "SubtaskItem{" +
                "id=" + getId() +
                "name='" + getName() + '\'' +
                ", status=" + getState() +
                "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
