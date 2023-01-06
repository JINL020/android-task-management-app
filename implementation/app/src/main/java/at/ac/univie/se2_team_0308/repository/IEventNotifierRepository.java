package at.ac.univie.se2_team_0308.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import at.ac.univie.se2_team_0308.utils.notifications.EventNotifier;

public interface IEventNotifierRepository {

    void insert(EventNotifier eventNotifier);

    void update(EventNotifier eventNotifier);

    void delete(EventNotifier eventNotifier);

    void deleteAll();

    LiveData<List<EventNotifier>> getAllEventNotifiers();
}
