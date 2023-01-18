package at.ac.univie.se2_team_0308.utils.notifications;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import at.ac.univie.se2_team_0308.utils.typeConverters.ENotificationEventTypeConverter;
import at.ac.univie.se2_team_0308.utils.typeConverters.INotifierTypeConverter;

/**
 * The settings chosen by the user in the settings fragment are saved in room database.
 * Each row has a ENotificationEvent and a INotifier that tells us what type of notification
 * the user wants to receive for the corresponding event (CREATE, UPDATE, DELETE, APPOINTMENT).
 *
 * @param event a ENotificationEvent must  always be provided since it is the
 *              primary key which also means that for each event only one notification setting is possible
 *
 * @author Jin-Jin Lee
 */
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
