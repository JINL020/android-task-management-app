package at.ac.univie.se2_team_0308.utils.typeConverters;

import androidx.room.TypeConverter;

import at.ac.univie.se2_team_0308.utils.notifications.ENotificationEvent;

public class ENotificationEventTypeConverter {

    @TypeConverter
    public String fromENotificationEvent(ENotificationEvent event) {
        return event.name().toString();
    }

    @TypeConverter
    public ENotificationEvent toENotificationEvent(String event) {
        switch (event) {
            case "CREATE":
                return ENotificationEvent.CREATE;
            case "UPDATE":
                return ENotificationEvent.UPDATE;
            case "DELETE":
                return ENotificationEvent.DELETE;
            case "APPOINTMENT":
                return ENotificationEvent.APPOINTMENT;
            default:
                return ENotificationEvent.APPOINTMENT;
        }
    }
}
