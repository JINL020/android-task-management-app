package at.ac.univie.se2_team_0308.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class DisplayClass implements Parcelable {
    private int id;
    private String taskName;
    private String description;
    private EPriority priority;
    private EStatus status;
    private boolean isSelected = false;
    private ECategory categoryEnum;
    private Date creationDate;
    private Date deadline;
    private List<String> subtasks;

    public DisplayClass(TaskAppointment appointment) {
        this.id = appointment.getId();
        this.taskName = appointment.getTaskName();
        this.description = appointment.getDescription();
        this.priority = appointment.getPriority();
        this.status = appointment.getStatus();
        this.isSelected = appointment.isSelected();
        this.categoryEnum = appointment.getCategory();
        this.creationDate = appointment.getCreationDate();
        this.deadline = appointment.getDeadline();
        this.subtasks = new ArrayList<>();
    }

    public DisplayClass(TaskChecklist checklist) {
        this.id = checklist.getId();
        this.taskName = checklist.getTaskName();
        this.description = checklist.getDescription();
        this.priority = checklist.getPriority();
        this.status = checklist.getStatus();
        this.isSelected = checklist.isSelected();
        this.categoryEnum = checklist.getCategory();
        this.creationDate = checklist.getCreationDate();
        this.deadline = Calendar.getInstance().getTime();
        this.subtasks = checklist.getSubtasks();
    }

    protected DisplayClass(Parcel in) {
        id = in.readInt();
        taskName = in.readString();
        description = in.readString();
        priority = EPriority.valueOf(in.readString());
        status = EStatus.valueOf(in.readString());
        isSelected = in.readInt() == 1;
        categoryEnum = ECategory.valueOf(in.readString());
        creationDate = new Date(in.readLong());
        deadline = new Date(in.readLong());
        subtasks = in.createStringArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EPriority getPriority() {
        return priority;
    }

    public void setPriority(EPriority priority) {
        this.priority = priority;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ECategory getCategoryEnum() {
        return categoryEnum;
    }

    public void setCategoryEnum(ECategory categoryEnum) {
        this.categoryEnum = categoryEnum;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public List<String> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<String> subtasks) {
        this.subtasks = subtasks;
    }

    public static final Creator<DisplayClass> CREATOR = new Creator<DisplayClass>() {
        @Override
        public DisplayClass createFromParcel(Parcel in) {
            return new DisplayClass(in);
        }

        @Override
        public DisplayClass[] newArray(int size) {
            return new DisplayClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(taskName);
        parcel.writeString(priority.toString());
        parcel.writeString(status.toString());
        parcel.writeInt(isSelected ? 1 : 0);
        parcel.writeString(categoryEnum.toString());
        parcel.writeLong(deadline.getTime());
        parcel.writeLong(creationDate.getTime());
        parcel.writeStringList(subtasks);
    }
}
