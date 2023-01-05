package at.ac.univie.se2_team_0308.utils.notifications;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.repository.notication.ENotificationEventTypeConverter;
import at.ac.univie.se2_team_0308.repository.notication.IObserverTypeConverter;

@Entity(tableName = "settings_notifier")
public class SettingsNotifier {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(ENotificationEventTypeConverter.class)
    private ENotificationEvent event;
    @TypeConverters(IObserverTypeConverter.class)
    private IObserver notifier;

    public SettingsNotifier(ENotificationEvent event, IObserver notifier) {
        this.event = event;
        this.notifier = notifier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ENotificationEvent getEvent() {
        return event;
    }

    public void setEvent(ENotificationEvent event) {
        this.event = event;
    }

    public IObserver getNotifier() {
        return notifier;
    }

    public void setNotifier(IObserver notifier) {
        this.notifier = notifier;
    }

    @NonNull
    @Override
    public String toString() {
        String ret = event.name() + " " + notifier.getNotifierType().toString() +"\n";
        return ret;
    }
}
