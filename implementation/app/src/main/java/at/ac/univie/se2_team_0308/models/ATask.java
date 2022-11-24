package at.ac.univie.se2_team_0308.models;
import androidx.room.PrimaryKey;
import java.util.Calendar;
import java.util.Date;

public abstract class ATask {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String taskName;
    private String description;
    private EPriority priority;
    private EStatus status;
    private boolean isSelected = false;
    private ECategory category;

   // @TypeConverters(DateConverter.class)
    private Date creationDate;

    public static final String TAG ="Task";

    public ATask(String taskName, String description, EPriority priority, EStatus status, ECategory category) {
        this.taskName = taskName;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.category = category;
        this.creationDate = Calendar.getInstance().getTime();
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



}