package at.ac.univie.se2_team_0308.utils.notifications;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import at.ac.univie.se2_team_0308.utils.typeConverters.ENotificationEventTypeConverter;
import at.ac.univie.se2_team_0308.utils.typeConverters.INotifierTypeConverter;

@Entity(tableName = "event_notifier")
public class EventNotifier {
    @TypeConverters(ENotificationEventTypeConverter.class)
    @PrimaryKey
    @NonNull
    private ENotificationEvent event;
    @TypeConverters(INotifierTypeConverter.class)
    private INotifier notifier;

    public EventNotifier(@NonNull ENotificationEvent event, INotifier notifier) {
        this.event = event;
        this.notifier = notifier;
    }

    @NonNull
    public ENotificationEvent getEvent() {
        return event;
    }

    public INotifier getNotifier() {
        return notifier;
    }

    public boolean isPopup() {
        return notifier.getNotifierType().contains(ENotifier.POPUP);
    }

    public boolean isBasic() {
        return notifier.getNotifierType().contains(ENotifier.BASIC);
    }

    @NonNull
    @Override
    public String toString() {
        return event.name() + " " + notifier.getNotifierType().toString();
    }
}
