package at.ac.univie.se2_team_0308.models;
import androidx.room.PrimaryKey;
import java.util.Calendar;
import java.util.Date;

public abstract class ATask {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String taskName;
    private String description;
//    private PriorityEnum priority;
//    private StatusEnum status;
    private boolean isSelected = false;
    //private CategoryEnum categoryEnum;

  //  @TypeConverters(DateConverter.class)
    private Date creationDate;

    public static final String TAG ="Task";

    //StatusEnum status, CategoryEnum categoryEnum, PriorityEnum priority
    public ATask(String taskName, String description) {
        this.taskName = taskName;
        this.description = description;
//        this.priority = priority;
//        this.status = status;
//        this.categoryEnum = categoryEnum;
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

//    public PriorityEnum getPriority() {
//        return priority;
//    }
//
//    public void setPriority(PriorityEnum priority) {
//        this.priority = priority;
//    }

//    public StatusEnum getStatus() {
//        return status;
//    }
//
//    public void setStatus(StatusEnum status) {
//        this.status = status;
//    }
//
//    public CategoryEnum getCategoryEnum() {
//        return categoryEnum;
//    }
//
//    public void setCategoryEnum(CategoryEnum categoryEnum) {
//        this.categoryEnum = categoryEnum;
//    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }



}
