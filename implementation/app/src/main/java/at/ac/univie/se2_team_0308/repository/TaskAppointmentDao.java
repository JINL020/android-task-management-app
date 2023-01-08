package at.ac.univie.se2_team_0308.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.TaskAppointment;

@Dao
public interface TaskAppointmentDao {
    @Insert
    long insert(TaskAppointment task);

    @Insert
    void insertAll(TaskAppointment... tasks);

    @Update
    void update(TaskAppointment task);

    @Query("UPDATE task_appointments SET priority = :priorityEnum WHERE id in (:idList)")
    void updateTaskPriority(List<Integer> idList, EPriority priorityEnum);

    @Query("UPDATE task_appointments SET isHidden = :isHidden WHERE id in (:idList)")
    void updateHiddenStatus(List<Integer> idList, boolean isHidden);

    @Query("UPDATE task_appointments SET taskColor = :color WHERE id in (:idList)")
    void updateTaskColor(List<Integer> idList, String color);

    @Delete
    void delete(TaskAppointment task);

    @Query("DELETE FROM task_appointments WHERE id in (:idList)")
    void deleteTasksById(List<Integer> idList);

    @Query("SELECT id, taskName, description, priority, status, category, isSelected, isHidden, deadline, creationDate FROM task_appointments")
    LiveData<List<TaskAppointment>> getAllTasks();

    @Query("SELECT * FROM task_appointments")
    List<TaskAppointment> getAllTasksList();
}
