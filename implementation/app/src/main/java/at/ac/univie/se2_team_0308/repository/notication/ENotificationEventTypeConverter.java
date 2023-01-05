package at.ac.univie.se2_team_0308.repository.notication;

import android.util.Log;

import androidx.room.TypeConverter;

import at.ac.univie.se2_team_0308.models.ENotificationEvent;

public class ENotificationEventTypeConverter {
    @TypeConverter
    public String fromENotificationEvent(ENotificationEvent event) {
        //Log.d("settings", event.name().toString());
        return event.name().toString();
    }

    @TypeConverter
    public ENotificationEvent toENotificationEvent(String event) {
        //Log.d("settings", event);
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
