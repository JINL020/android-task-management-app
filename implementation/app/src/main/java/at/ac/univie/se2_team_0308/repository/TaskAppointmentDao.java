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
    void insert(TaskAppointment task);

    @Insert
    void insertAll(TaskAppointment... tasks);

    @Update
    void update(TaskAppointment task);

    @Query("UPDATE task_appointments SET priority = :priorityEnum WHERE id in (:idList)")
    void updateTaskPriority(List<Integer> idList, EPriority priorityEnum);

    @Delete
    void delete(TaskAppointment task);

    @Query("DELETE FROM task_appointments WHERE id in (:idList)")
    void deleteTasksById(List<Integer> idList);

    @Query("SELECT id, taskName, description, priority, status, category, isSelected, deadline, creationDate FROM task_appointments")
    LiveData<List<TaskAppointment>> getAllTasks();

    @Query("SELECT id, taskName, description, priority, status, category, isSelected, deadline, creationDate FROM task_appointments WHERE id in (:idList)")
    LiveData<List<TaskAppointment>> getTasksById(List<Integer> idList);
}
