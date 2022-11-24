package at.ac.univie.se2_team_0308.repository;
import at.ac.univie.se2_team_0308.models.TaskAppointment;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskAppointmentDao {
    @Insert
    void insert(TaskAppointment task);

    @Insert
    void insertAll(TaskAppointment... tasks);

    @Update
    void update(TaskAppointment task);

    @Delete
    void delete(TaskAppointment task);

    @Query("SELECT id, taskName, description, priority, status, categoryEnum, isSelected, deadline, creationDate FROM task_appointments")
    LiveData<List<TaskAppointment>> getAllTasks();
}
