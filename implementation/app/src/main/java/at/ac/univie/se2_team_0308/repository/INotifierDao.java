package at.ac.univie.se2_team_0308.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;

@Dao
public interface INotifierDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SettingsNotifier settingsNotifier);

    @Update
    void update(SettingsNotifier settingsNotifier);

    @Delete
    void delete(SettingsNotifier settingsNotifier);

    @Query("DELETE FROM settings_notifier")
    void deleteAll();

    @Query("SELECT * FROM settings_notifier")
    LiveData<List<SettingsNotifier>> getAllNotifiers();
}
