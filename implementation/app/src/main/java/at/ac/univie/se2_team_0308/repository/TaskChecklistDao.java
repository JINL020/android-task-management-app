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
    void insert(TaskChecklist task);

    @Insert
    void insertAll(TaskChecklist... tasks);

    @Update
    void update(TaskChecklist task);

    @Query("UPDATE task_checklists SET priority = :priorityEnum WHERE id in (:idList)")
    void updateTaskPriority(List<Integer> idList, EPriority priorityEnum);

    @Delete
    void delete(TaskChecklist task);

    @Query("DELETE FROM task_checklists WHERE id in (:idList)")
    void deleteTasksById(List<Integer> idList);

    @Query("SELECT id, taskName, description, priority, status, category, isSelected, creationDate FROM task_checklists")
    LiveData<List<TaskChecklist>> getAllTasks();
}
