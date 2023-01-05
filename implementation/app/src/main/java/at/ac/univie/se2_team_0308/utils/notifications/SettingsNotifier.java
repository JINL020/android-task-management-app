package at.ac.univie.se2_team_0308.utils.notifications;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.utils.ENotificationEventTypeConverter;
import at.ac.univie.se2_team_0308.utils.IObserverTypeConverter;

@Entity(tableName = "settings_notifier")
public class SettingsNotifier {
    @TypeConverters(ENotificationEventTypeConverter.class)
    @PrimaryKey@NonNull
    private ENotificationEvent event;
    @TypeConverters(IObserverTypeConverter.class)
    private INotifier notifier;

    public SettingsNotifier(ENotificationEvent event, INotifier notifier) {
        this.event = event;
        this.notifier = notifier;
    }

    public ENotificationEvent getEvent() {
        return event;
    }

    public void setEvent(ENotificationEvent event) {
        this.event = event;
    }

    public INotifier getNotifier() {
        return notifier;
    }

    public void setNotifier(INotifier notifier) {
        this.notifier = notifier;
    }

    public boolean isPopup(){
        return notifier.getNotifierType().contains(ENotifier.POPUP);
    }

    public boolean isBasic(){
        return notifier.getNotifierType().contains(ENotifier.BASIC);
    }

    @NonNull
    @Override
    public String toString() {
        String ret = event.name() + " " + notifier.getNotifierType().toString();
        return ret;
    }
}
