package at.ac.univie.se2_team_0308.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

@Dao
public interface TaskChecklistDao {
    @Insert
    long insert(TaskChecklist task);

    @Insert
    void insertAll(TaskChecklist... tasks);

    @Update
    void update(TaskChecklist task);

    @Query("UPDATE task_checklists SET priority = :priorityEnum WHERE id in (:idList)")
    void updateTaskPriority(List<Integer> idList, EPriority priorityEnum);

    @Query("UPDATE task_checklists SET isHidden = :isHidden WHERE id in (:idList)")
    void updateHiddenStatus(List<Integer> idList, boolean isHidden);

    @Query("UPDATE task_checklists SET taskColor = :color WHERE id in (:idList)")
    void updateTaskColor(List<Integer> idList, String color);

    @Delete
    void delete(TaskChecklist task);

    @Query("DELETE FROM task_checklists WHERE id in (:idList)")
    void deleteTasksById(List<Integer> idList);

    @Query("SELECT id, taskName, description, priority, status, category, isSelected, isHidden, creationDate, subtasks, attachments, sketchData FROM task_checklists")
    LiveData<List<TaskChecklist>> getAllTasks();

    @Query("SELECT * FROM task_checklists")
    List<TaskChecklist> getAllTasksList();
}
