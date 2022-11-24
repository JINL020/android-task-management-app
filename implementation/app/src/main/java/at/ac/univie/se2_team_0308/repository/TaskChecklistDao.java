package at.ac.univie.se2_team_0308.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface TaskChecklistDao {
    @Insert
    void insert(TaskChecklist task);

    @Insert
    void insertAll(TaskChecklist... tasks);

    @Update
    void update(TaskChecklist task);

    @Delete
    void delete(TaskChecklist task);
}
