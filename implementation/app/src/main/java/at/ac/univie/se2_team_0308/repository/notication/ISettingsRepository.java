package at.ac.univie.se2_team_0308.repository.notication;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;

public interface ISettingsRepository {

    void insert(SettingsNotifier settingsNotifier);

    void update(SettingsNotifier settingsNotifier);

    void delete(SettingsNotifier settingsNotifier);

    void deleteAll();

    LiveData<List<SettingsNotifier>> getAllSettingsNotifier();
}
