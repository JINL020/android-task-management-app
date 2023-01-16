package at.ac.univie.se2_team_0308.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import at.ac.univie.se2_team_0308.utils.notifications.EventNotifier;

@Dao
public interface IEventNotifierDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EventNotifier eventNotifier);

    @Update
    void update(EventNotifier eventNotifier);

    @Delete
    void delete(EventNotifier eventNotifier);

    @Query("DELETE FROM event_notifier")
    void deleteAll();

    @Query("SELECT * FROM event_notifier")
    LiveData<List<EventNotifier>> getAllEventNotifiers();
}
