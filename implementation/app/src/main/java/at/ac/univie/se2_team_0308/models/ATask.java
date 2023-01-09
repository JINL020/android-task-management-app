package at.ac.univie.se2_team_0308.models;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import at.ac.univie.se2_team_0308.utils.AttachmentConverter;

public abstract class ATask {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String taskName;
    private String description;
    private EPriority priority;
    private EStatus status;
    private boolean isSelected = false;
    private ECategory category;
    private boolean isHidden = false;
    private String taskColor = "#E1E1E1"; //default light grey color

    @TypeConverters(AttachmentConverter.class)
    private List<Attachment> attachments;


    // @TypeConverters(DateConverter.class)
    private Date creationDate;

    public static final String TAG ="Task";

    public ATask(String taskName, String description, EPriority priority, EStatus status, ECategory category, List<Attachment> attachments) {
        this.taskName = taskName;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.category = category;
        this.creationDate = Calendar.getInstance().getTime();
        this.attachments = attachments;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        if(attachments == null){
            boolean n = true;
        }
        this.attachments = attachments;
    }

    public void addAttachment(Attachment a){
        this.attachments.add(a);
    }

    public void removeAttachment(Attachment a){
        this.attachments.remove(a);
    }

    public boolean isHidden() { return isHidden; }

    public void setHidden(boolean isHidden) { this.isHidden = isHidden; }

    public String getTaskColor() { return taskColor; }

    public void setTaskColor(String color) { this.taskColor = color; }

    public String toString() {
        return "ATask{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", isSelected=" + isSelected +
                ", isHidden=" + isHidden +
                ", category=" + category +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATask task = (ATask) o;
        return id == task.id
                && isSelected == task.isSelected
                && Objects.equals(taskName, task.taskName)
                && Objects.equals(description, task.description)
                && priority == task.priority && status == task.status && category == task.category
                && Objects.equals(creationDate, task.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, description, priority, status, isSelected, category, creationDate);
    }
}
